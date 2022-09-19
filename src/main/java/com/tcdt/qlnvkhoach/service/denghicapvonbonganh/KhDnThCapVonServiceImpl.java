package com.tcdt.qlnvkhoach.service.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganhCt;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnThCapVon;
import com.tcdt.qlnvkhoach.enums.NhapXuatHangTrangThaiEnum;
import com.tcdt.qlnvkhoach.repository.denghicapvonbonganh.KhDnCapVonBoNganhCtRepository;
import com.tcdt.qlnvkhoach.repository.denghicapvonbonganh.KhDnCapVonBoNganhRepository;
import com.tcdt.qlnvkhoach.repository.denghicapvonbonganh.KhDnThCapVonRepository;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnThCapVonCtResponse;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnThCapVonResponse;
import com.tcdt.qlnvkhoach.service.BaseServiceImpl;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
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

    private static final String SHEET_TONG_HOP_DE_NGHI_CAP_VON_DTQG = "Tổng hợp đề nghị cấp vốn DTQG";
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
    public KhDnThCapVonResponse create(KhDnThCapVonRequest req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        KhDnThCapVon item = new KhDnThCapVon();
        BeanUtils.copyProperties(req, item, "id", "maTongHop");
        item.setNgayTao(LocalDate.now());
        item.setNguoiTaoId(userInfo.getId());
        item.setTrangThai(NhapXuatHangTrangThaiEnum.DUTHAO.getId());
        item.setMaDvi(userInfo.getDvql());
        item.setCapDvi(userInfo.getCapDvi());
        item.setNam(LocalDate.now().getYear());
        khDnThCapVonRepository.save(item);

        List<KhDnCapVonBoNganh> khDnCapVonBoNganhList = this.saveListChiTiet(item.getId(), req.getKhDnCapVonIds(), false);
        item.setCts(khDnCapVonBoNganhList);
        return this.buildResponse(item);
    }

    private List<KhDnCapVonBoNganh> saveListChiTiet(Long khThId, List<Long> khDnIds, boolean update) {

        if (update) {
            List<KhDnCapVonBoNganh> olds = khDnCapVonBoNganhRepository.findByKhDnThIdIn(Collections.singletonList(khThId));
            olds.forEach(o -> o.setKhDnThId(null));
            if (!CollectionUtils.isEmpty(olds))
                khDnCapVonBoNganhRepository.saveAll(olds);
        }

        if (!CollectionUtils.isEmpty(khDnIds)) {
            List<KhDnCapVonBoNganh> updates = khDnCapVonBoNganhRepository.findByIdIn(khDnIds);

            updates.forEach(o -> o.setKhDnThId(khThId));
            if (!CollectionUtils.isEmpty(updates))
                khDnCapVonBoNganhRepository.saveAll(updates);
            return updates;
        }

        return Collections.emptyList();
    }

    private List<KhDnThCapVonCtResponse> buildKhDnThCapVonCtResponse(List<KhDnCapVonBoNganh> khDnCapVonBoNganhs) {
        Set<Long> ids = khDnCapVonBoNganhs.stream().map(KhDnCapVonBoNganh::getId).collect(Collectors.toSet());
        List<KhDnCapVonBoNganhCt> chiTietList = khDnCapVonBoNganhCtRepository.findByDeNghiCapVonBoNganhIdIn(ids);
        if (CollectionUtils.isEmpty(chiTietList)) return Collections.emptyList();

        //group chi tiết : key = deNghiCapVonBoNganhId, value = List<KhDnCapVonBoNganhCt>
        Map<Long, List<KhDnCapVonBoNganhCt>> chiTietMap = chiTietList.stream().collect(Collectors.groupingBy(KhDnCapVonBoNganhCt::getDeNghiCapVonBoNganhId));

        List<KhDnThCapVonCtResponse> responses = new ArrayList<>();

        for (KhDnCapVonBoNganh khDnCapVonBoNganh : khDnCapVonBoNganhs) {
            KhDnThCapVonCtResponse response = new KhDnThCapVonCtResponse();
            BeanUtils.copyProperties(khDnCapVonBoNganh, response);

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
        res.setTenTrangThai(NhapXuatHangTrangThaiEnum.getTenById(item.getTrangThai()));
        res.setTrangThaiDuyet(NhapXuatHangTrangThaiEnum.getTrangThaiDuyetById(item.getTrangThai()));

        res.setCts(this.buildKhDnThCapVonCtResponse(item.getCts()));

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
        List<KhDnCapVonBoNganh> khDnCapVonBoNganhList = this.saveListChiTiet(item.getId(), req.getKhDnCapVonIds(), true);
        item.setCts(khDnCapVonBoNganhList);

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
        item.setCts(khDnCapVonBoNganhRepository.findByKhDnThIdIn(Collections.singletonList(item.getId())));
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
        if (NhapXuatHangTrangThaiEnum.BAN_HANH.getId().equals(item.getTrangThai())) {
            throw new Exception("Không thể xóa Tổng hợp đề nghị cấp vốn DTQG đã ban hành");
        }
        List<KhDnCapVonBoNganh> byKhDnThIdIn = khDnCapVonBoNganhRepository.findByKhDnThIdIn(Collections.singletonList(item.getId()));
        byKhDnThIdIn.forEach(i -> {
            i.setKhDnThId(null);
        });
        if (!CollectionUtils.isEmpty(byKhDnThIdIn))
            khDnCapVonBoNganhRepository.saveAll(byKhDnThIdIn);

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
        String trangThai = NhapXuatHangTrangThaiEnum.getTrangThaiDuyetById(stReq.getTrangThai());
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
        List<Long> ids = data.stream().map(KhDnThCapVon::getId).collect(Collectors.toList());

        Map<Long, List<KhDnCapVonBoNganh>> chiTietMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(ids)) {
            //group chi tiết : key = KhDnThId, value = List<KhDnCapVonBoNganh>
            chiTietMap =  khDnCapVonBoNganhRepository.findByKhDnThIdIn(ids)
                    .stream().collect(Collectors.groupingBy(KhDnCapVonBoNganh::getKhDnThId));
        }
        List<KhDnThCapVonResponse> responses = new ArrayList<>();
        for (KhDnThCapVon item : data) {
            KhDnThCapVonResponse response = new KhDnThCapVonResponse();
            BeanUtils.copyProperties(item, response);
            response.setTenTrangThai(NhapXuatHangTrangThaiEnum.getTenById(item.getTrangThai()));
            response.setTrangThaiDuyet(NhapXuatHangTrangThaiEnum.getTrangThaiDuyetById(item.getTrangThai()));

            List<KhDnThCapVonCtResponse> khDnThCapVonCtResponses = this.buildKhDnThCapVonCtResponse(chiTietMap.get(item.getId()));

            BigDecimal tongTien = khDnThCapVonCtResponses.stream()
                    .map(KhDnThCapVonCtResponse::getTongTien)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.setTongTien(tongTien);

            BigDecimal kinhPhiDaCap = khDnThCapVonCtResponses.stream()
                    .map(KhDnThCapVonCtResponse::getKinhPhiDaCap)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.setKinhPhiDaCap(kinhPhiDaCap);

            BigDecimal ycCapThem = khDnThCapVonCtResponses.stream()
                    .map(KhDnThCapVonCtResponse::getYcCapThem)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.setYcCapThem(ycCapThem);
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

        String[] rowsName = new String[] { STT, MA_TONG_HOP, NGAY_TONG_HOP, TONG_TIEN,
                KINH_PHI_DA_CAP, YEU_CAU_CAP_THEM, TRANG_THAI};
        String filename = "Danh_sach_tong_hop_de_nghie_cap_von_DTQG.xlsx";

        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;

        try {
            for (int i = 0; i < list.size(); i++) {
                KhDnThCapVonResponse item = list.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;
                objs[1] = item.getMaTongHop();
                objs[2] = LocalDateTimeUtils.localDateToString(item.getNgayTongHop());
                objs[3] = item.getTongTien();
                objs[4] = item.getKinhPhiDaCap();
                objs[5] = item.getYcCapThem();
                objs[6] = item.getTrangThai();
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
