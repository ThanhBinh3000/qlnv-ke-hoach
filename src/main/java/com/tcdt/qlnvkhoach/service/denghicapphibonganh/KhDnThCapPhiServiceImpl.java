package com.tcdt.qlnvkhoach.service.denghicapphibonganh;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.QlnvDanhMuc;
import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.*;
import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.repository.DanhMucRepository;
import com.tcdt.qlnvkhoach.repository.denghicapphibonganh.*;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiCt1Request;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiCt2Request;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiRequest;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhCt2Response;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnThCapPhiCt1Response;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnThCapPhiResponse;
import com.tcdt.qlnvkhoach.service.BaseServiceImpl;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmVattu;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import com.tcdt.qlnvkhoach.util.LocalDateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class KhDnThCapPhiServiceImpl extends BaseServiceImpl implements KhDnThCapPhiService {

    private final KhDnThCapPhiRepository khDnThCapPhiRepository;
    private final KhDnThCapPhiCt1Repository khDnThCapPhiCt1Repository;
    private final KhDnThCapPhiCt2Repository khDnThCapPhiCt2Repository;
    private final FileDinhKemService fileDinhKemService;
    private final QlnvDmService qlnvDmService;
    private final DanhMucRepository danhMucRepository;

    private static final String LOAI_DE_XUAT = "00";
    private static final String LOAI_PHUONG_AN = "01";

    private static final String SHEET_TONG_HOP_DE_NGHI_CAP_PHI_DTQG = "Tổng hợp đề nghị cấp phí DTQG";
    private static final String STT = "STT";
    private static final String MA_TONG_HOP = "Mã Tổng Hợp";
    private static final String NAM = "Năm";
    private static final String NGAY_TONG_HOP = "Ngày Tổng Hợp";
    private static final String TONG_TIEN = "Tổng Tiền";
    private static final String KINH_PHI_DA_CAP = "Kinh Phí Đã Cấp";
    private static final String YEU_CAU_CAP_THEM = "Yêu Cầu Cấp Thêm";
    private static final String TRANG_THAI = "Trạng Thái";

    @Override
    @Transactional(rollbackOn = Exception.class)
    public KhDnThCapPhiResponse create(KhDnThCapPhiRequest req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        KhDnThCapPhi item = new KhDnThCapPhi();
        BeanUtils.copyProperties(req, item, "id", "maTongHop");
        item.setNgayTao(LocalDate.now());
        item.setNguoiTaoId(userInfo.getId());
        item.setTrangThai(TrangThaiDungChungEnum.DUTHAO.getId());
        item.setMaDvi(userInfo.getDvql());
        item.setCapDvi(userInfo.getCapDvi());
        item.setNam(LocalDate.now().getYear());

        khDnThCapPhiRepository.save(item);
        item.setMaTongHop(String.valueOf(item.getId()));
        item.setCts(this.saveCt1s(item, req.getCts(), LOAI_DE_XUAT, false));
        item.setCt1s(this.saveCt1s(item, req.getCt1s(), LOAI_PHUONG_AN, false));

        khDnThCapPhiRepository.save(item);

        List<FileDinhKemChung> fileDinhKemChungs = fileDinhKemService.saveListFileDinhKem(Collections.singletonList(req.getFileDinhKem()), item.getId(), KhDnThCapPhi.TABLE_NAME);
        item.setFileDinhKem(fileDinhKemChungs.stream().findFirst().orElse(null));
        return this.buildResponse(item);
    }

//    private List<KhDnCapPhiBoNganh> saveCts(Long khThId, List<Long> khDnIds, boolean update) {
//
//        if (update) {
//            List<KhDnCapPhiBoNganh> olds = khDnCapPhiBoNganhRepository.findByKhDnThCapPhiIdIn(Collections.singletonList(khThId));
//            olds.forEach(o -> o.setKhDnThCapPhiId(null));
//            if (!CollectionUtils.isEmpty(olds))
//                khDnCapPhiBoNganhRepository.saveAll(olds);
//        }
//
//        if (!CollectionUtils.isEmpty(khDnIds)) {
//            List<KhDnCapPhiBoNganh> updates = khDnCapPhiBoNganhRepository.findByIdIn(khDnIds);
//
//            updates.forEach(o -> o.setKhDnThCapPhiId(khThId));
//            if (!CollectionUtils.isEmpty(updates))
//                khDnCapPhiBoNganhRepository.saveAll(updates);
//            return updates;
//        }
//
//        return Collections.emptyList();
//    }

    private List<KhDnThCapPhiCt1> saveCt1s(KhDnThCapPhi item, List<KhDnThCapPhiCt1Request> ct1Requests, String loai,  boolean update) {

        if (update) {
            List<KhDnThCapPhiCt1> olds = khDnThCapPhiCt1Repository.findByKhDnThCapPhiIdInAndLoai(Collections.singletonList(item.getId()), loai);
            if (!CollectionUtils.isEmpty(olds)) {
                khDnThCapPhiCt2Repository.deleteAllByKhDnThCapPhiCt1IdIn(olds.stream().map(KhDnThCapPhiCt1::getId).collect(Collectors.toList()));
                khDnThCapPhiCt1Repository.deleteAll(olds);
            }
        }

        if (!CollectionUtils.isEmpty(ct1Requests)) {
            List<KhDnThCapPhiCt1> ct1s = new ArrayList<>();
            for (KhDnThCapPhiCt1Request ct1Request : ct1Requests) {
                KhDnThCapPhiCt1 ct1 = new KhDnThCapPhiCt1();
                BeanUtils.copyProperties(ct1Request, ct1, "id", "ct2s");
                ct1.setLoai(loai);
                ct1.setKhDnThCapPhiId(item.getId());
                khDnThCapPhiCt1Repository.save(ct1);

                BigDecimal ycCapThemPhi = BigDecimal.ZERO;
                BigDecimal tongTien = BigDecimal.ZERO;
                BigDecimal kinhPhiDaCap = BigDecimal.ZERO;
                List<KhDnThCapPhiCt2Request> ct2Requests = ct1Request.getCt2s();
                List<KhDnThCapPhiCt2> ct2s = new ArrayList<>();
                for (KhDnThCapPhiCt2Request ct2Request : ct2Requests) {
                    KhDnThCapPhiCt2 ct2 = new KhDnThCapPhiCt2();
                    BeanUtils.copyProperties(ct2Request, ct2, "id");
                    ct2s.add(ct2);
                    if (ct2.getYeuCauCapThem() != null)
                        ycCapThemPhi = ycCapThemPhi.add(ct2.getYeuCauCapThem());

                    if (ct2.getTongTien() != null)
                        tongTien = tongTien.add(ct2.getTongTien());

                    if (ct2.getKinhPhiDaCap() != null)
                        kinhPhiDaCap = kinhPhiDaCap.add(ct2.getKinhPhiDaCap());
                }
                if (!CollectionUtils.isEmpty(ct2s)) {
                    khDnThCapPhiCt2Repository.saveAll(ct2s);
                    ct1.setYcCapThemPhi(ycCapThemPhi);
                    ct1.setTongTien(tongTien);
                    ct1.setKinhPhiDaCap(kinhPhiDaCap);
                    khDnThCapPhiCt1Repository.save(ct1);
                }
                ct1s.add(ct1);

                item.setYcCapThemPhi(ct1s.stream().map(KhDnThCapPhiCt1::getYcCapThemPhi).reduce(BigDecimal.ZERO, BigDecimal::add));
                item.setKinhPhiDaCap(ct1s.stream().map(KhDnThCapPhiCt1::getKinhPhiDaCap).reduce(BigDecimal.ZERO, BigDecimal::add));
                item.setTongTien(ct1s.stream().map(KhDnThCapPhiCt1::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add));
                khDnThCapPhiRepository.save(item);
            }
            return ct1s;
        }

        return Collections.emptyList();
    }

//    private List<KhDnCapPhiBoNganhCt1Response> buildKhDnThCapPhiCtResponse(List<KhDnCapPhiBoNganh> khDnCapPhiBoNganhs) {
//        Set<Long> ids = khDnCapPhiBoNganhs.stream().map(KhDnCapPhiBoNganh::getId).collect(Collectors.toSet());
//        List<KhDnCapPhiBoNganhCt1> chiTietList = khDnCapPhiBoNganhCt1Repository.findByDnCapPhiIdIn(ids);
//        if (CollectionUtils.isEmpty(chiTietList)) return Collections.emptyList();
//
//        //group chi tiết : key = deNghiCapVonBoNganhId, value = List<KhDnCapPhiBoNganhCt1>
//        Map<Long, List<KhDnCapPhiBoNganhCt1>> ct1s = chiTietList.stream().collect(Collectors.groupingBy(KhDnCapPhiBoNganhCt1::getDnCapPhiId));
//
//        //Chi tiết 2
//        List<KhDnCapPhiBoNganhCt2> ct2s = khDnCapPhiBoNganhCt2Repository.findByCapPhiBoNghanhCt1IdIn(ct1s.stream().map(KhDnCapPhiBoNganhCt1::getId).collect(Collectors.toSet()));
//        //Map ct2 key = ct1Id, value = List<KhDnCapPhiBoNganhCt2>
//        Map<Long, List<KhDnCapPhiBoNganhCt2>> ct2Map = ct2s.stream().collect(Collectors.groupingBy(KhDnCapPhiBoNganhCt2::getCapPhiBoNghanhCt1Id));
//
//        List<KhDnCapPhiBoNganhCt1Response> responses = new ArrayList<>();
//
//        for (KhDnCapPhiBoNganh khDnCapPhiBoNganh : khDnCapPhiBoNganhs) {
//            KhDnCapPhiBoNganhCt1Response response = new KhDnCapPhiBoNganhCt1Response();
//            BeanUtils.copyProperties(khDnCapPhiBoNganh, response);
//
//            List<KhDnCapPhiBoNganhCt1> ctList = ct1s.get(khDnCapPhiBoNganh.getId());
//            BigDecimal ycCapThemPhi = ctList.stream()
//                    .map(KhDnCapPhiBoNganhCt1::getYcCapThemPhi)
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//            response.setYcCapThemPhi(ycCapThemPhi);
//            responses.add(response);
//        }
//        return responses;
//    }

    private List<KhDnThCapPhiCt1Response> buildKhDnThCt1Response(List<KhDnThCapPhiCt1> khDnThCapPhiCt1s) {
        Set<Long> ids = khDnThCapPhiCt1s.stream().map(KhDnThCapPhiCt1::getId).collect(Collectors.toSet());
        List<KhDnThCapPhiCt2> ct2s = khDnThCapPhiCt2Repository.findByKhDnThCapPhiCt1IdIn(ids);

        //group chi tiết : key = getKhDnThCapPhiCt1Id, value = List<KhDnThCapPhiCt2>
        Map<Long, List<KhDnThCapPhiCt2>> chiTietMap = ct2s.stream().collect(Collectors.groupingBy(KhDnThCapPhiCt2::getKhDnThCapPhiCt1Id));

        List<KhDnThCapPhiCt1Response> responses = new ArrayList<>();

        //Get mã vật tư
        Set<String> maVatTuList = new HashSet<>();
        ct2s.forEach(entry -> {
            maVatTuList.add(entry.getMaVatTu());
            maVatTuList.add(entry.getMaVatTuCha());
        });

        Map<String, QlnvDmVattu> vatTuMap = qlnvDmService.getMapVatTu(maVatTuList);

        //Bộ ngành
        List<QlnvDanhMuc> danhMucs = danhMucRepository.findByMaIn(khDnThCapPhiCt1s.stream().map(KhDnThCapPhiCt1::getMaBoNganh).filter(Objects::nonNull).collect(Collectors.toList()));

        for (KhDnThCapPhiCt1 khDnThCapPhiCt1 : khDnThCapPhiCt1s) {
            KhDnThCapPhiCt1Response response = new KhDnThCapPhiCt1Response();
            BeanUtils.copyProperties(khDnThCapPhiCt1, response);
            danhMucs.stream().filter(d -> d.getMa().equals(khDnThCapPhiCt1.getMaBoNganh())).findFirst()
                    .ifPresent(d -> response.setTenBoNganh(d.getGiaTri()));

            List<KhDnThCapPhiCt2> ctList = chiTietMap.get(khDnThCapPhiCt1.getId());
            List<KhDnCapPhiBoNganhCt2Response> ct2Responses = ctList.stream().map(c -> {
                KhDnCapPhiBoNganhCt2Response ct2Response = new KhDnCapPhiBoNganhCt2Response();
                BeanUtils.copyProperties(c, ct2Response);

//                ct2Response.setTenVatTu(Optional.ofNullable(vatTuMap.get(c.getMaVatTu())).map(QlnvDmVattu::getTen).orElse(null));
//                ct2Response.setTenVatTuCha(Optional.ofNullable(vatTuMap.get(c.getMaVatTuCha())).map(QlnvDmVattu::getTen).orElse(null));
                return ct2Response;
            }).collect(Collectors.toList());

            response.setCt2List(ct2Responses);
            responses.add(response);
        }
        return responses;
    }

    private KhDnThCapPhiResponse buildResponse(KhDnThCapPhi item) throws Exception {
        KhDnThCapPhiResponse res = new KhDnThCapPhiResponse();
        BeanUtils.copyProperties(item, res);
        res.setTenTrangThai(TrangThaiDungChungEnum.getTenById(item.getTrangThai()));
        res.setTrangThaiDuyet(TrangThaiDungChungEnum.getTrangThaiDuyetById(item.getTrangThai()));

        res.setCts(this.buildKhDnThCt1Response(item.getCts()));
        res.setCts(this.buildKhDnThCt1Response(item.getCt1s()));
        this.setThongTinDonVi(res, item.getMaDvi());


        return res;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public KhDnThCapPhiResponse update(KhDnThCapPhiRequest req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        Optional<KhDnThCapPhi> optional = khDnThCapPhiRepository.findById(req.getId());
        if (!optional.isPresent())
            throw new Exception("Tổng hợp đề nghị cấp phí DTQG không tồn tại.");

        KhDnThCapPhi item = optional.get();
        BeanUtils.copyProperties(req, item, "id", "maTongHop");
        item.setNgaySua(LocalDate.now());
        item.setNguoiSuaId(userInfo.getId());

        khDnThCapPhiRepository.save(item);
        item.setCts(this.saveCt1s(item, req.getCts(), LOAI_DE_XUAT, true));
        item.setCt1s(this.saveCt1s(item, req.getCt1s(), LOAI_PHUONG_AN, true));

        khDnThCapPhiRepository.save(item);
        List<FileDinhKemChung> fileDinhKemChungs = fileDinhKemService.saveListFileDinhKem(Collections.singletonList(req.getFileDinhKem()), item.getId(), KhDnThCapPhi.TABLE_NAME);
        item.setFileDinhKem(fileDinhKemChungs.stream().findFirst().orElse(null));
        return this.buildResponse(item);
    }

    @Override
    public KhDnThCapPhiResponse detail(Long id) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<KhDnThCapPhi> optional = khDnThCapPhiRepository.findById(id);
        if (!optional.isPresent())
            throw new Exception("Tổng hợp đề nghị cấp phí DTQG không tồn tại.");

        KhDnThCapPhi item = optional.get();
        List<KhDnThCapPhiCt1> ct1s = khDnThCapPhiCt1Repository.findByKhDnThCapPhiIdIn(Collections.singletonList(item.getId()));
        item.setCts(ct1s.stream().filter(c -> LOAI_DE_XUAT.equals(c.getLoai())).collect(Collectors.toList()));
        item.setCt1s(ct1s.stream().filter(c -> LOAI_PHUONG_AN.equals(c.getLoai())).collect(Collectors.toList()));

        List<FileDinhKemChung> fileDinhKemChungs = fileDinhKemService.search(item.getId(), Collections.singletonList(KhDnThCapPhi.TABLE_NAME));
        item.setFileDinhKem(fileDinhKemChungs.stream().findFirst().orElse(null));
        return this.buildResponse(item);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(Long id) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<KhDnThCapPhi> optional = khDnThCapPhiRepository.findById(id);
        if (!optional.isPresent())
            throw new Exception("Tổng hợp đề nghị cấp phí DTQG không tồn tại.");

        KhDnThCapPhi item = optional.get();
        if (TrangThaiDungChungEnum.BAN_HANH.getId().equals(item.getTrangThai())) {
            throw new Exception("Không thể xóa Tổng hợp đề nghị cấp phí DTQG đã ban hành");
        }

        this.deleteKhDnThCapPhiCt(Collections.singletonList(item.getId()));
        fileDinhKemService.delete(id, Collections.singleton(KhDnThCapPhi.TABLE_NAME));
        khDnThCapPhiRepository.delete(item);
        return true;
    }

    private void deleteKhDnThCapPhiCt(Collection<Long> khDnThCapPhiIds) {
        List<KhDnThCapPhiCt1> ct1s = khDnThCapPhiCt1Repository.findByKhDnThCapPhiIdIn(khDnThCapPhiIds);
        if (!CollectionUtils.isEmpty(ct1s)) {
            khDnThCapPhiCt2Repository.deleteAllByKhDnThCapPhiCt1IdIn(ct1s.stream().map(KhDnThCapPhiCt1::getId).collect(Collectors.toList()));
        }

        khDnThCapPhiCt1Repository.deleteAll(ct1s);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean updateStatusQd(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<KhDnThCapPhi> optional = khDnThCapPhiRepository.findById(stReq.getId());
        if (!optional.isPresent())
            throw new Exception("Tổng hợp đề nghị cấp phí DTQG không tồn tại.");

        KhDnThCapPhi khDnThCapVon = optional.get();
        //validate Trạng Thái
        String trangThai = TrangThaiDungChungEnum.getTrangThaiDuyetById(stReq.getTrangThai());
        if (StringUtils.isEmpty(trangThai)) throw new Exception("Trạng thái không tồn tại");
        khDnThCapVon.setTrangThai(stReq.getTrangThai());
        khDnThCapPhiRepository.save(khDnThCapVon);
        return true;
    }

    @Override
    public Page<KhDnThCapPhiResponse> search(KhDnThCapPhiSearchRequest req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        this.prepareSearchReq(req, userInfo, req.getCapDvis(), req.getTrangThais());
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        List<KhDnThCapPhi> data = khDnThCapPhiRepository.search(req, pageable);

        List<KhDnThCapPhiResponse> responses = new ArrayList<>();
        for (KhDnThCapPhi item : data) {
            KhDnThCapPhiResponse response = new KhDnThCapPhiResponse();
            BeanUtils.copyProperties(item, response);
            response.setTenTrangThai(TrangThaiDungChungEnum.getTenById(item.getTrangThai()));
            response.setTrangThaiDuyet(TrangThaiDungChungEnum.getTrangThaiDuyetById(item.getTrangThai()));
            responses.add(response);
        }

        return new PageImpl<>(responses, pageable, khDnThCapPhiRepository.count(req));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean deleteMultiple(DeleteReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        this.deleteKhDnThCapPhiCt(req.getIds());
        fileDinhKemService.deleteMultiple(req.getIds(), Collections.singleton(KhDnThCapPhi.TABLE_NAME));
        khDnThCapPhiRepository.deleteAllByIdIn(req.getIds());
        return true;
    }

    @Override
    public boolean exportToExcel(KhDnThCapPhiSearchRequest objReq, HttpServletResponse response) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        this.prepareSearchReq(objReq, userInfo, objReq.getCapDvis(), objReq.getTrangThais());
        objReq.setPaggingReq(new PaggingReq(Integer.MAX_VALUE, 0));
        List<KhDnThCapPhiResponse> list = this.search(objReq).get().collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list))
            return true;

        String[] rowsName = new String[] { STT, MA_TONG_HOP, NAM, NGAY_TONG_HOP, TONG_TIEN,
                KINH_PHI_DA_CAP, YEU_CAU_CAP_THEM, TRANG_THAI};
        String filename = "Danh_sach_tong_hop_de_nghie_cap_phi_DTQG.xlsx";

        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;

        try {
            for (int i = 0; i < list.size(); i++) {
                KhDnThCapPhiResponse item = list.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;
                objs[1] = item.getMaTongHop();
                objs[2] = item.getNam();
                objs[3] = LocalDateTimeUtils.localDateToString(item.getNgayTongHop());
                objs[4] = item.getTongTien();
                objs[5] = item.getKinhPhiDaCap();
                objs[6] = item.getYcCapThemPhi();
                objs[7] = item.getTrangThai();
                dataList.add(objs);
            }

            ExportExcel ex = new ExportExcel(SHEET_TONG_HOP_DE_NGHI_CAP_PHI_DTQG, filename, rowsName, dataList, response);
            ex.export();
        } catch (Exception e) {
            log.error("Error export", e);
            return false;
        }

        return true;
    }
}
