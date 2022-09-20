package com.tcdt.qlnvkhoach.service.thongtriduyetydutoan;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganhCt;
import com.tcdt.qlnvkhoach.entities.thongtriduyetydutoan.TtDuyetYDuToan;
import com.tcdt.qlnvkhoach.entities.thongtriduyetydutoan.TtDuyetYDuToanCt;
import com.tcdt.qlnvkhoach.enums.NhapXuatHangTrangThaiEnum;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.repository.thongtriduyetydutoan.TtDuyetYDuToanCtRepository;
import com.tcdt.qlnvkhoach.repository.thongtriduyetydutoan.TtDuyetYDuToanRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.request.thongtriduyetydutoan.TtDuyetYDuToanCtRequest;
import com.tcdt.qlnvkhoach.request.thongtriduyetydutoan.TtDuyetYDuToanRequest;
import com.tcdt.qlnvkhoach.request.thongtriduyetydutoan.TtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhResponse;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhSearchResponse;
import com.tcdt.qlnvkhoach.response.thongtriduyetydutoan.TtDuyetYDuToanCtResponse;
import com.tcdt.qlnvkhoach.response.thongtriduyetydutoan.TtDuyetYDuToanResponse;
import com.tcdt.qlnvkhoach.service.BaseServiceImpl;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.service.thongtriduyetydutoan.TtDuyetYDuToanService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.FileDinhKem;
import com.tcdt.qlnvkhoach.util.ExcelHeaderConst;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import com.tcdt.qlnvkhoach.util.LocalDateTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TtDuyetYDuToanServiceImpl extends BaseServiceImpl implements TtDuyetYDuToanService {

    private static final String SHEET_THONG_TRI_DUYET_Y_DU_TOAN = "Thông tri duyệt y dự toán";
    private final String MA_DS = "/JDF";
    @Autowired
    FileDinhKemService fileDinhKemService;
    @Autowired
    TtDuyetYDuToanRepository ttDuyetYDuToanRepository;
    @Autowired
    TtDuyetYDuToanCtRepository ctRepository;
    @Autowired
    QlnvDmDonviRepository qlnvDmDonviRepository;
    private static final String SHEET_NAME = "Thông tri duệt y dự toán";
    private static final String FILE_NAME = "thong_tri_duyet_y_du_toan.xlsx";

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TtDuyetYDuToanResponse create(TtDuyetYDuToanRequest req) throws Exception {
        if (req == null) return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        TtDuyetYDuToan item = new TtDuyetYDuToan();

        BeanUtils.copyProperties(req, item, "id");
        Long count = ttDuyetYDuToanRepository.getMaxId();
        if (count == null) count = 1L;
        item.setSoThongTri(count.intValue() + 1 + "/" + LocalDate.now().getYear() + MA_DS);

        item.setTrangThai(NhapXuatHangTrangThaiEnum.DUTHAO.getId());
        item.setNgayTao(LocalDate.now());
        item.setNguoiTaoId(userInfo.getId());
        ttDuyetYDuToanRepository.save(item);


        List<TtDuyetYDuToanCt> ds = req.getChiTietList().stream()
                .map(d -> {
                    d.setIdTtdydt(item.getId());
                    return d;
                })
                .map(d -> {
                    TtDuyetYDuToanCt xuatKhoCt = new TtDuyetYDuToanCt();
                    BeanUtils.copyProperties(d, xuatKhoCt, "id");
                    return xuatKhoCt;
                })
                .collect(Collectors.toList());

        ctRepository.saveAll(ds);
        TtDuyetYDuToanResponse result = new TtDuyetYDuToanResponse();
        BeanUtils.copyProperties(item, result, "id");

        List<TtDuyetYDuToanCtResponse> dsRes = ds
                .stream()
                .map(user -> new ModelMapper().map(user, TtDuyetYDuToanCtResponse.class))
                .collect(Collectors.toList());

        result.setChiTietList(dsRes);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKems(), item.getId(), TtDuyetYDuToan.TABLE_NAME);
        item.setFileDinhKems(fileDinhKems);
        return result;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TtDuyetYDuToanResponse update(TtDuyetYDuToanRequest req) throws Exception {
        if (req == null) return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        Optional<TtDuyetYDuToan> optional = ttDuyetYDuToanRepository.findById(req.getId());
        if (!optional.isPresent())
            throw new Exception("Đề nghị cấp vốn bộ ngành không tồn tại");
        TtDuyetYDuToan item = optional.get();
        BeanUtils.copyProperties(req, optional.get(), "id", "trangThai");
        item.setNgaySua(LocalDate.now());
        item.setNguoiSuaId(userInfo.getId());
        ttDuyetYDuToanRepository.save(item);

        Map<Long, TtDuyetYDuToanCt> mapChiTiet = ctRepository.findByIdTtdydtIn(Collections.singleton(item.getId()))
                .stream().collect(Collectors.toMap(TtDuyetYDuToanCt::getId, Function.identity()));

        List<TtDuyetYDuToanCt> chiTiets = this.saveListChiTiet(item.getId(), req.getChiTietList(), mapChiTiet);
        item.setChiTietList(chiTiets);
        if (!CollectionUtils.isEmpty(mapChiTiet.values()))
            ctRepository.deleteAll(mapChiTiet.values());

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKems(), item.getId(), TtDuyetYDuToan.TABLE_NAME);
        item.setFileDinhKems(fileDinhKems);
        TtDuyetYDuToanResponse res = new TtDuyetYDuToanResponse();
        BeanUtils.copyProperties(item, res);

        return res;
    }

    private List<TtDuyetYDuToanCt> saveListChiTiet(Long parentId,
                                                   List<TtDuyetYDuToanCtRequest> chiTietReqs,
                                                   Map<Long, TtDuyetYDuToanCt> mapChiTiet) throws Exception {
        List<TtDuyetYDuToanCt> chiTiets = new ArrayList<>();
        for (TtDuyetYDuToanCtRequest req : chiTietReqs) {
            Long id = req.getId();
            TtDuyetYDuToanCt chiTiet = new TtDuyetYDuToanCt();

            if (id != null && id > 0) {
                chiTiet = mapChiTiet.get(id);
                if (chiTiet == null)
                    throw new Exception("Thông tri duyệt y dự toán chi tiết không tồn tại.");
                mapChiTiet.remove(id);
            }

            BeanUtils.copyProperties(req, chiTiet, "id");
            chiTiet.setIdTtdydt(parentId);
            chiTiets.add(chiTiet);
        }

        if (!CollectionUtils.isEmpty(chiTiets))
            ctRepository.saveAll(chiTiets);

        return chiTiets;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        return this.deleteMultiple(Collections.singletonList(id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean deleteMultiple(List<Long> ids) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        List<TtDuyetYDuToan> lstTtDuyetYDuToans = ttDuyetYDuToanRepository.findByIdIn(ids);
        if (CollectionUtils.isEmpty(lstTtDuyetYDuToans)) {
            throw new Exception("Đề nghị cấp vốn bộ ngành không tồn tại");
        }

        if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");

        fileDinhKemService.deleteMultiple(ids, Collections.singleton(TtDuyetYDuToan.TABLE_NAME));


        this.deleteChiTiets(lstTtDuyetYDuToans.stream().map(TtDuyetYDuToan::getId).collect(Collectors.toSet()));


        ttDuyetYDuToanRepository.deleteAll(lstTtDuyetYDuToans);
        return true;
    }

    private void deleteChiTiets(Set<Long> ids) {
        List<TtDuyetYDuToanCt> ctList = ctRepository.findByIdTtdydtIn(ids);
        if (!CollectionUtils.isEmpty(ctList)) {
            ctRepository.deleteAll(ctList);
        }
    }


    @Override
    public Page<TtDuyetYDuToanResponse> search(TtDuyetYDuToanSearchRequest req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        List<TtDuyetYDuToanResponse> list = ttDuyetYDuToanRepository.search(req);
        list.forEach(item -> {
            item.setFileDinhKems(fileDinhKemService.search(item.getId(), Collections.singleton(TtDuyetYDuToan.TABLE_NAME)));
        });
        Page<TtDuyetYDuToanResponse> page = new PageImpl<>(list, pageable, list.size());

        return page;
    }

    @Override
    public TtDuyetYDuToanResponse detail(Long id) throws Exception {
        Optional<TtDuyetYDuToan> phieuXuatKho = ttDuyetYDuToanRepository.findById(id);

        if (!phieuXuatKho.isPresent())
            throw new Exception("Không tìm thấy dữ liệu.");

        TtDuyetYDuToanResponse item = new TtDuyetYDuToanResponse();
        BeanUtils.copyProperties(phieuXuatKho, item);
        item.setTenTrangThai(NhapXuatHangTrangThaiEnum.getTenById(item.getTrangThai()));
        item.setTenDvi(qlnvDmDonviRepository.findByMaDvi(item.getMaDvi()).getTenDvi());

        // chi tiết
        List<TtDuyetYDuToanCt> lstDs = ctRepository.findByIdTtdydtIn(Collections.singleton(id));
        List<TtDuyetYDuToanCtResponse> dsRes = lstDs.stream()
                .map(user -> new ModelMapper().map(user, TtDuyetYDuToanCtResponse.class))
                .collect(Collectors.toList());
        item.setChiTietList(dsRes);
        // đính kèm
        item.setFileDinhKems(fileDinhKemService.search(id, Collections.singleton(TtDuyetYDuToan.TABLE_NAME)));

        return item;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean updateStatus(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        Optional<TtDuyetYDuToan> optional = ttDuyetYDuToanRepository.findById(stReq.getId());
        if (!optional.isPresent())
            throw new Exception("Thông tri duyệt y dự toán không tồn tại.");

        TtDuyetYDuToan item = optional.get();
        String trangThai = item.getTrangThai();
        if (NhapXuatHangTrangThaiEnum.CHODUYET_LDCC.getId().equals(stReq.getTrangThai())) {
            if (!NhapXuatHangTrangThaiEnum.DUTHAO.getId().equals(trangThai))
                return false;

            item.setTrangThai(NhapXuatHangTrangThaiEnum.CHODUYET_LDCC.getId());
            item.setNguoiGuiDuyetId(userInfo.getId());
            item.setNgayGuiDuyet(LocalDate.now());

        } else if (NhapXuatHangTrangThaiEnum.DADUYET_LDCC.getId().equals(stReq.getTrangThai())) {
            if (!NhapXuatHangTrangThaiEnum.CHODUYET_LDCC.getId().equals(trangThai))
                return false;

            item.setTrangThai(NhapXuatHangTrangThaiEnum.DADUYET_LDCC.getId());
            item.setNguoiPduyetId(userInfo.getId());
            item.setNgayPduyet(LocalDate.now());

        } else if (NhapXuatHangTrangThaiEnum.TUCHOI_LDCC.getId().equals(stReq.getTrangThai())) {
            if (!NhapXuatHangTrangThaiEnum.CHODUYET_LDCC.getId().equals(trangThai))
                return false;

            item.setTrangThai(NhapXuatHangTrangThaiEnum.TUCHOI_LDCC.getId());
            item.setNguoiPduyetId(userInfo.getId());
            item.setNgayPduyet(LocalDate.now());
            item.setLyDoTuChoi(stReq.getLyDoTuChoi());

        } else {
            throw new Exception("Bad request.");
        }

        return true;
    }

    @Override
    public boolean exportToExcel(TtDuyetYDuToanSearchRequest req, HttpServletResponse response) throws Exception {
        req.setPaggingReq(new PaggingReq(Integer.MAX_VALUE, 0));
        List<TtDuyetYDuToanResponse> list = this.search(req).get().collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list))
            return true;

        String[] rowsName = new String[]{ExcelHeaderConst.STT,
                ExcelHeaderConst.SO_THONG_TRI,
                ExcelHeaderConst.NAM,
                ExcelHeaderConst.NGAY_LAP,
                ExcelHeaderConst.LY_DO_CHI,
                ExcelHeaderConst.SO_DE_NGHI_CAP_VON,
                ExcelHeaderConst.DON_VI,
                ExcelHeaderConst.TRANG_THAI
        };

        List<Object[]> dataList = new ArrayList<>();
        Object[] objs;

        try {
            for (int i = 0; i < list.size(); i++) {
                TtDuyetYDuToanResponse item = list.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;
                objs[1] = item.getSoThongTri();
                objs[2] = item.getNam();
                objs[3] = LocalDateTimeUtils.localDateToString(item.getNgayLap());
                objs[4] = item.getLyDoChi();
                objs[5] = item.getSoDnCapVon();
                objs[6] = item.getTenDvi();
                objs[7] = item.getTenTrangThai();
                objs[8] = NhapXuatHangTrangThaiEnum.getTenById(item.getTrangThai());
                dataList.add(objs);
            }

            ExportExcel ex = new ExportExcel(SHEET_NAME, FILE_NAME, rowsName, dataList, response);
            ex.export();
        } catch (Exception e) {
            log.error("Error export", e);
            return false;
        }

        return true;
    }
}
