package com.tcdt.qlnvkhoach.service.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.QlnvDanhMuc;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganhCt;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnThCapVon;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnThCapVonCt1;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPhuongAnGia;
import com.tcdt.qlnvkhoach.enums.TrangThaiAllEnum;
import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.repository.DanhMucRepository;
import com.tcdt.qlnvkhoach.repository.denghicapvonbonganh.KhDnCapVonBoNganhCtRepository;
import com.tcdt.qlnvkhoach.repository.denghicapvonbonganh.KhDnCapVonBoNganhRepository;
import com.tcdt.qlnvkhoach.repository.denghicapvonbonganh.KhDnThCapVonCt1Repository;
import com.tcdt.qlnvkhoach.repository.denghicapvonbonganh.KhDnThCapVonRepository;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonCt1Request;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnThCapVonCtResponse;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnThCapVonResponse;
import com.tcdt.qlnvkhoach.service.BaseServiceImpl;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
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
public class KhDnThCapVonServiceImpl extends BaseServiceImpl implements KhDnThCapVonService {
    private final KhDnThCapVonRepository khDnThCapVonRepository;
    private final KhDnCapVonBoNganhRepository khDnCapVonBoNganhRepository;
    private final KhDnCapVonBoNganhCtRepository khDnCapVonBoNganhCtRepository;
    private final KhDnThCapVonCt1Repository khDnThCapVonCt1Repository;
    private final FileDinhKemService fileDinhKemService;
    private final DanhMucRepository danhMucRepository;

    private static final String SHEET_TONG_HOP_DE_NGHI_CAP_VON_DTQG = "Tổng hợp đề nghị cấp vốn DTQG";
    private static final String STT = "STT";
    private static final String MA_TONG_HOP = "Mã Tổng Hợp";
    private static final String NAM = "Năm";
    private static final String NGAY_TONG_HOP = "Ngày Tổng Hợp";
    private static final String TONG_TIEN = "Tổng Tiền";
    private static final String KINH_PHI_DA_CAP = "Kinh Phí Đã Cấp";
    private static final String TONG_CUC_CAP_THEM = "Tổng Cục Cấp Thêm";
    private static final String TRANG_THAI = "Trạng Thái";

    @Override
    @Transactional(rollbackOn = Exception.class)
    public KhDnThCapVonResponse create(KhDnThCapVonRequest req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        KhDnThCapVon item = new KhDnThCapVon();
        BeanUtils.copyProperties(req, item, "id", "maTongHop");
        item.setNgayTao(LocalDate.now());
        item.setNguoiTaoId(userInfo.getId());
        item.setTrangThai(TrangThaiDungChungEnum.DUTHAO.getId());
        item.setMaDvi(userInfo.getDvql());
        item.setCapDvi(userInfo.getCapDvi());
        item.setNam(LocalDate.now().getYear());

        khDnThCapVonRepository.save(item);
        item.setMaTongHop(String.valueOf(item.getId()));
        item.setCts(this.saveCts(item.getId(), req.getKhDnCapVonIds(), false));
        item.setCt1s(this.saveCt1s(item, req.getCt1s(), false));

        khDnThCapVonRepository.save(item);

        List<FileDinhKemChung> fileDinhKemChungs = fileDinhKemService.saveListFileDinhKem(Collections.singletonList(req.getFileDinhKem()), item.getId(), KhDnThCapVon.TABLE_NAME);
        item.setFileDinhKem(fileDinhKemChungs.stream().findFirst().orElse(null));
        return this.buildResponse(item);
    }

    private List<KhDnCapVonBoNganh> saveCts(Long khThId, List<Long> khDnIds, boolean update) {

        if (update) {
            List<KhDnCapVonBoNganh> olds = khDnCapVonBoNganhRepository.findByKhDnThIdIn(Collections.singletonList(khThId));
            olds.forEach(o -> o.setKhDnThId(null));
            if (!CollectionUtils.isEmpty(olds))
                khDnCapVonBoNganhRepository.saveAll(olds);
        }

        if (!CollectionUtils.isEmpty(khDnIds)) {
            List<KhDnCapVonBoNganh> updates = khDnCapVonBoNganhRepository.findByIdIn(khDnIds);

            updates.forEach(o -> o.setKhDnThId(khThId));
            updates.forEach(o -> o.setTrangThaiTh(TrangThaiAllEnum.DA_TONG_HOP.getId()));
            if (!CollectionUtils.isEmpty(updates))
                khDnCapVonBoNganhRepository.saveAll(updates);
            return updates;
        }

        return Collections.emptyList();
    }

    private List<KhDnThCapVonCt1> saveCt1s(KhDnThCapVon item, List<KhDnThCapVonCt1Request> ct1Requests, boolean update) {
        if (update) {
            List<KhDnThCapVonCt1> olds = khDnThCapVonCt1Repository.findByKhDnThIdIn(Collections.singletonList(item.getId()));
            if (!CollectionUtils.isEmpty(olds))
                khDnThCapVonCt1Repository.deleteAll(olds);
        }
        if (!CollectionUtils.isEmpty(ct1Requests)) {
            List<KhDnCapVonBoNganh> khDnCapVons = khDnCapVonBoNganhRepository.findByIdIn(ct1Requests.stream().map(KhDnThCapVonCt1Request::getKhDnCapVonId).collect(Collectors.toList()));
            List<KhDnThCapVonCtResponse> cts = this.buildKhDnThCapVonCtResponse(khDnCapVons);
            item.setTongTien(cts.stream().map(KhDnThCapVonCtResponse::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add));
            item.setKinhPhiDaCap(cts.stream().map(KhDnThCapVonCtResponse::getKinhPhiDaCap).reduce(BigDecimal.ZERO, BigDecimal::add));
            item.setYcCapThem(cts.stream().map(KhDnThCapVonCtResponse::getYcCapThem).reduce(BigDecimal.ZERO, BigDecimal::add));
            BigDecimal tcCapThem = BigDecimal.ZERO;
            List<KhDnThCapVonCt1> ct1s = new ArrayList<>();
            for (KhDnThCapVonCt1Request ct1Request : ct1Requests) {
                KhDnThCapVonCt1 ct1 = new KhDnThCapVonCt1();
                ct1.setKhDnThId(item.getId());
                ct1.setKhDnCapVonId(ct1Request.getKhDnCapVonId());
                ct1.setTcCapThem(ct1Request.getTcCapThem());
                ct1.setKinhPhiDaCap(ct1Request.getKinhPhiDaCap());
                ct1.setLoaiHang(ct1Request.getLoaiHang());
                ct1.setTongTien(ct1Request.getTongTien());
                ct1.setYcCapThem(ct1Request.getYcCapThem());
                ct1.setTenBoNganh(ct1Request.getTenBoNganh());
                ct1.setLoaiBn(ct1Request.getLoaiBn());
                ct1.setMaBn(ct1Request.getMaBn());
                ct1.setSoDeNghi(ct1Request.getSoDeNghi());
                ct1.setNam(ct1Request.getNam());
                ct1.setNgayDeNghi(LocalDate.parse(ct1Request.getNgayDeNghi()));
                ct1s.add(ct1);
                if (ct1.getTcCapThem() != null) {
                    tcCapThem = tcCapThem.add(ct1Request.getTcCapThem());
                }
            }
            if (!CollectionUtils.isEmpty(ct1s)) {
                khDnThCapVonCt1Repository.saveAll(ct1s);
                item.setTcCapThem(tcCapThem);
            }
            return ct1s;
        }
        return Collections.emptyList();
    }

    private List<KhDnThCapVonCtResponse> buildKhDnThCapVonCtResponse(List<KhDnCapVonBoNganh> khDnCapVonBoNganhs) {
        Set<Long> ids = khDnCapVonBoNganhs.stream().map(KhDnCapVonBoNganh::getId).collect(Collectors.toSet());
        List<KhDnCapVonBoNganhCt> chiTietList = khDnCapVonBoNganhCtRepository.findByDeNghiCapVonBoNganhIdIn(ids);

        //group chi tiết : key = deNghiCapVonBoNganhId, value = List<KhDnCapVonBoNganhCt>
        Map<Long, List<KhDnCapVonBoNganhCt>> chiTietMap = chiTietList.stream().collect(Collectors.groupingBy(KhDnCapVonBoNganhCt::getDeNghiCapVonBoNganhId));

        List<KhDnThCapVonCtResponse> responses = new ArrayList<>();

        //Bộ ngành
        List<QlnvDanhMuc> danhMucs = danhMucRepository.findByMaIn(khDnCapVonBoNganhs.stream().map(KhDnCapVonBoNganh::getMaBoNganh).filter(Objects::nonNull).collect(Collectors.toList()));

        for (KhDnCapVonBoNganh khDnCapVonBoNganh : khDnCapVonBoNganhs) {
            KhDnThCapVonCtResponse response = new KhDnThCapVonCtResponse();
            BeanUtils.copyProperties(khDnCapVonBoNganh, response);
            danhMucs.stream().filter(d -> d.getMa().equals(khDnCapVonBoNganh.getMaBoNganh())).findFirst()
                    .ifPresent(d -> response.setTenBoNganh(d.getGiaTri()));

            List<KhDnCapVonBoNganhCt> ctList = chiTietMap.get(khDnCapVonBoNganh.getId());


            BigDecimal tongTien = ctList.stream()
                    .map(KhDnCapVonBoNganhCt::getThanhTien)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.setTongTien(tongTien);

            BigDecimal kinhPhiDaCap = ctList.stream()
                    .map(KhDnCapVonBoNganhCt::getKinhPhiDaCap)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.setKinhPhiDaCap(kinhPhiDaCap);

            BigDecimal ycCapThem = ctList.stream()
                    .map(KhDnCapVonBoNganhCt::getYcCapThem)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.setYcCapThem(ycCapThem);

            responses.add(response);
        }
        return responses;
    }

    private KhDnThCapVonResponse buildResponse(KhDnThCapVon item) throws Exception {
        KhDnThCapVonResponse res = new KhDnThCapVonResponse();
        BeanUtils.copyProperties(item, res);
        res.setTenTrangThai(TrangThaiDungChungEnum.getTenById(item.getTrangThai()));
        res.setTrangThaiDuyet(TrangThaiDungChungEnum.getTrangThaiDuyetById(item.getTrangThai()));
        List<KhDnThCapVonCt1> ctBtcs = item.getCt1s().stream().filter(it -> it.getMaBn().equals("BTC")).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(ctBtcs)) {
            KhDnThCapVonCt1 ct1Res = new KhDnThCapVonCt1();
            ct1Res.setTenBoNganh("Tổng cục Dự Trữ");
            BigDecimal tongTien = BigDecimal.ZERO;
            BigDecimal kpDaCap = BigDecimal.ZERO;
            BigDecimal ycCapThem = BigDecimal.ZERO;
            for (KhDnThCapVonCt1 ct1 : ctBtcs) {
                tongTien = tongTien.add(ct1.getTongTien());
                kpDaCap = kpDaCap.add(ct1.getKinhPhiDaCap());
                ycCapThem = ycCapThem.add(ct1.getYcCapThem());
                ct1.setIsSum(Boolean.FALSE);
                ct1.setParentName("Tổng cục Dự Trữ");
            }
            ct1Res.setTongTien(tongTien);
            ct1Res.setYcCapThem(ycCapThem);
            ct1Res.setKinhPhiDaCap(kpDaCap);
            ct1Res.setIsSum(Boolean.TRUE);
            item.getCt1s().add(0, ct1Res);
        }
//        res.setCts(this.buildKhDnThCapVonCtResponse(item.getCts()));

//        List<Long> khDnCapVonIds = item.getCt1s().stream().map(KhDnThCapVonCt1::getKhDnCapVonId).collect(Collectors.toList());
//
//        List<KhDnCapVonBoNganh> khDnCapVons = khDnCapVonBoNganhRepository.findByIdIn(khDnCapVonIds);
//        List<KhDnThCapVonCtResponse> ct1s = this.buildKhDnThCapVonCtResponse(khDnCapVons);
//        ct1s.forEach(c -> {
//            item.getCt1s().stream()
//                    .filter(t -> c.getId().equals(t.getKhDnCapVonId())).findFirst()
//                    .ifPresent(t -> c.setTcCapThem(t.getTcCapThem()));
//        });
//        res.setCt1s(ct1s);
        this.setThongTinDonVi(res, item.getMaDvi());
        return res;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public KhDnThCapVonResponse update(KhDnThCapVonRequest req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        Optional<KhDnThCapVon> optional = khDnThCapVonRepository.findById(req.getId());
        if (!optional.isPresent())
            throw new Exception("Tổng hợp đề nghị cấp vốn DTQG không tồn tại.");

        KhDnThCapVon item = optional.get();
        BeanUtils.copyProperties(req, item, "id");
        item.setNgaySua(LocalDate.now());
        item.setNguoiSuaId(userInfo.getId());

        khDnThCapVonRepository.save(item);
        item.setCts(this.saveCts(item.getId(), req.getKhDnCapVonIds(), true));
        item.setCt1s(this.saveCt1s(item, req.getCt1s(), true));

        khDnThCapVonRepository.save(item);
        List<FileDinhKemChung> fileDinhKemChungs = fileDinhKemService.saveListFileDinhKem(Collections.singletonList(req.getFileDinhKem()), item.getId(), KhDnThCapVon.TABLE_NAME);
        item.setFileDinhKem(fileDinhKemChungs.stream().findFirst().orElse(null));
        return this.buildResponse(item);
    }

    @Override
    public KhDnThCapVonResponse detail(Long id) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<KhDnThCapVon> optional = khDnThCapVonRepository.findById(id);
        if (!optional.isPresent())
            throw new Exception("Tổng hợp đề nghị cấp vốn DTQG không tồn tại.");

        KhDnThCapVon item = optional.get();
//        item.setCts(khDnCapVonBoNganhRepository.findByKhDnThIdIn(Collections.singletonList(item.getId())));
        item.setCt1s(khDnThCapVonCt1Repository.findByKhDnThIdIn(Collections.singletonList(item.getId())));

        List<FileDinhKemChung> fileDinhKemChungs = fileDinhKemService.search(item.getId(), Collections.singletonList(KhDnThCapVon.TABLE_NAME));
        item.setFileDinhKem(fileDinhKemChungs.stream().findFirst().orElse(null));
        return this.buildResponse(item);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(Long id) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<KhDnThCapVon> optional = khDnThCapVonRepository.findById(id);
        if (!optional.isPresent())
            throw new Exception("Tổng hợp đề nghị cấp vốn DTQG không tồn tại.");

        KhDnThCapVon item = optional.get();
        if (TrangThaiDungChungEnum.BAN_HANH.getId().equals(item.getTrangThai())) {
            throw new Exception("Không thể xóa Tổng hợp đề nghị cấp vốn DTQG đã ban hành");
        }
        List<KhDnCapVonBoNganh> byKhDnThIdIn = khDnCapVonBoNganhRepository.findByKhDnThIdIn(Collections.singletonList(item.getId()));
        byKhDnThIdIn.forEach(i -> {
            i.setKhDnThId(null);
        });
        if (!CollectionUtils.isEmpty(byKhDnThIdIn))
            khDnCapVonBoNganhRepository.saveAll(byKhDnThIdIn);

        khDnThCapVonCt1Repository.deleteByKhDnThIdIn(Collections.singletonList(item.getId()));
        fileDinhKemService.delete(id, Collections.singleton(KhDnThCapVon.TABLE_NAME));
        khDnThCapVonRepository.delete(item);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean updateStatusQd(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<KhDnThCapVon> optional = khDnThCapVonRepository.findById(stReq.getId());
        if (!optional.isPresent())
            throw new Exception("Tổng hợp đề nghị cấp vốn DTQG không tồn tại.");

        KhDnThCapVon khDnThCapVon = optional.get();
        //validate Trạng Thái
        String trangThai = TrangThaiDungChungEnum.getTrangThaiDuyetById(stReq.getTrangThai());
        if (StringUtils.isEmpty(trangThai)) throw new Exception("Trạng thái không tồn tại");
        khDnThCapVon.setTrangThai(stReq.getTrangThai());
        khDnThCapVonRepository.save(khDnThCapVon);
        return true;
    }

    @Override
    public Page<KhDnThCapVonResponse> search(KhDnThCapVonSearchRequest req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        this.prepareSearchReq(req, userInfo, req.getCapDvis(), req.getTrangThais());
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        List<KhDnThCapVon> data = khDnThCapVonRepository.search(req, pageable);
        List<Long> idsThs = data.stream().map(KhDnThCapVon::getId).collect(Collectors.toList());
        List<KhDnThCapVonCt1> listCt1 = khDnThCapVonCt1Repository.findByKhDnThIdIn(idsThs);
        Map<Long, List<KhDnThCapVonCt1>> mapListCt1 = listCt1.stream().collect(Collectors.groupingBy(o -> o.getKhDnThId()));
        List<KhDnThCapVonResponse> responses = new ArrayList<>();
        for (KhDnThCapVon item : data) {
            KhDnThCapVonResponse response = new KhDnThCapVonResponse();
            BeanUtils.copyProperties(item, response);
            response.setTenTrangThai(TrangThaiDungChungEnum.getTenById(item.getTrangThai()));
            response.setTrangThaiDuyet(TrangThaiDungChungEnum.getTrangThaiDuyetById(item.getTrangThai()));
            List<KhDnThCapVonCt1> lCtiet = mapListCt1.get(item.getId());
            if (!CollectionUtils.isEmpty(lCtiet)) {
                response.setKinhPhiDaCap(lCtiet.stream().map(KhDnThCapVonCt1::getKinhPhiDaCap).reduce(BigDecimal.ZERO, BigDecimal::add));
                response.setTongTien(lCtiet.stream().map(KhDnThCapVonCt1::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add));
                response.setYcCapThem(lCtiet.stream().map(KhDnThCapVonCt1::getYcCapThem).reduce(BigDecimal.ZERO, BigDecimal::add));
            }
            responses.add(response);
        }
        return new PageImpl<>(responses, pageable, khDnThCapVonRepository.count(req));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean deleteMultiple(DeleteReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        List<KhDnCapVonBoNganh> byKhDnThIdIn = khDnCapVonBoNganhRepository.findByKhDnThIdIn(req.getIds());
        byKhDnThIdIn.forEach(i -> {
            i.setKhDnThId(null);
        });
        if (!CollectionUtils.isEmpty(byKhDnThIdIn))
            khDnCapVonBoNganhRepository.saveAll(byKhDnThIdIn);

        khDnThCapVonCt1Repository.deleteByKhDnThIdIn(req.getIds());
        fileDinhKemService.deleteMultiple(req.getIds(), Collections.singleton(KhDnThCapVon.TABLE_NAME));
        khDnThCapVonRepository.deleteByIdIn(req.getIds());
        return true;
    }

    @Override
    public boolean exportToExcel(KhDnThCapVonSearchRequest objReq, HttpServletResponse response) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        this.prepareSearchReq(objReq, userInfo, objReq.getCapDvis(), objReq.getTrangThais());
        objReq.setPaggingReq(new PaggingReq(Integer.MAX_VALUE, 0));
        List<KhDnThCapVonResponse> list = this.search(objReq).get().collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list))
            return true;

        String[] rowsName = new String[]{STT, MA_TONG_HOP, NAM, NGAY_TONG_HOP, TONG_TIEN,
                KINH_PHI_DA_CAP, TONG_CUC_CAP_THEM, TRANG_THAI};
        String filename = "Danh_sach_tong_hop_de_nghie_cap_von_DTQG.xlsx";

        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;

        try {
            for (int i = 0; i < list.size(); i++) {
                KhDnThCapVonResponse item = list.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;
                objs[1] = item.getMaTongHop();
                objs[2] = item.getNam();
                objs[3] = LocalDateTimeUtils.localDateToString(item.getNgayTongHop());
                objs[4] = item.getTongTien();
                objs[5] = item.getKinhPhiDaCap();
                objs[6] = item.getTcCapThem();
                objs[7] = item.getTrangThai();
                dataList.add(objs);
            }

            ExportExcel ex = new ExportExcel(SHEET_TONG_HOP_DE_NGHI_CAP_VON_DTQG, filename, rowsName, dataList, response);
            ex.export();
        } catch (Exception e) {
            log.error("Error export", e);
            return false;
        }

        return true;
    }
}
