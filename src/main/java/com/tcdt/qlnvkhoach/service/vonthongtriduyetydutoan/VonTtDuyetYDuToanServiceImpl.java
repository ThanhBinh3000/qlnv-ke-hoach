package com.tcdt.qlnvkhoach.service.vonthongtriduyetydutoan;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.vonthongtriduyetydutoan.VonTtDuyetYDuToan;
import com.tcdt.qlnvkhoach.entities.vonthongtriduyetydutoan.VonTtDuyetYDuToanCt;
import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.repository.vonthongtriduyetydutoan.VonTtDuyetYDuToanCtRepository;
import com.tcdt.qlnvkhoach.repository.vonthongtriduyetydutoan.VonTtDuyetYDuToanRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.vonthongtriduyetydutoan.VonTtDuyetYDuToanCtRequest;
import com.tcdt.qlnvkhoach.request.vonthongtriduyetydutoan.VonTtDuyetYDuToanRequest;
import com.tcdt.qlnvkhoach.request.vonthongtriduyetydutoan.VonTtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.vonthongtriduyetydutoan.VonTtDuyetYDuToanCtResponse;
import com.tcdt.qlnvkhoach.response.vonthongtriduyetydutoan.VonTtDuyetYDuToanResponse;
import com.tcdt.qlnvkhoach.service.BaseServiceImpl;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
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

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
public class VonTtDuyetYDuToanServiceImpl extends BaseServiceImpl implements VonTtDuyetYDuToanService {

    private static final String SHEET_THONG_TRI_DUYET_Y_DU_TOAN = "Thông tri duyệt y dự toán";
    private final String MA_DS = "/JDF";
    @Autowired
    FileDinhKemService fileDinhKemService;
    @Autowired
    VonTtDuyetYDuToanRepository vonTtDuyetYDuToanRepository;
    @Autowired
    VonTtDuyetYDuToanCtRepository ctRepository;
    @Autowired
    QlnvDmDonviRepository qlnvDmDonviRepository;
    private static final String SHEET_NAME = "Thông tri duyệt y dự toán";
    private static final String FILE_NAME = "thong_tri_duyet_y_du_toan.xlsx";

    @Override
    @Transactional(rollbackOn = Exception.class)
    public VonTtDuyetYDuToanResponse create(VonTtDuyetYDuToanRequest req) throws Exception {
        if (req == null) return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        VonTtDuyetYDuToan item = new VonTtDuyetYDuToan();

        BeanUtils.copyProperties(req, item, "id");
        Long count = vonTtDuyetYDuToanRepository.getMaxId();
        if (count == null) count = 1L;
        item.setSoThongTri(count.intValue() + 1 + "/" + LocalDate.now().getYear() + MA_DS);

        item.setTrangThai(TrangThaiDungChungEnum.DUTHAO.getId());
        item.setNgayTao(LocalDate.now());
        item.setNguoiTaoId(userInfo.getId());
        vonTtDuyetYDuToanRepository.save(item);


        List<VonTtDuyetYDuToanCt> ds = req.getChiTietList().stream()
                .map(d -> {
                    d.setIdTtdydt(item.getId());
                    return d;
                })
                .map(d -> {
                    VonTtDuyetYDuToanCt ct = new VonTtDuyetYDuToanCt();
                    BeanUtils.copyProperties(d, ct, "id");
                    return ct;
                })
                .collect(Collectors.toList());

        ctRepository.saveAll(ds);
        VonTtDuyetYDuToanResponse result = new VonTtDuyetYDuToanResponse();
        BeanUtils.copyProperties(item, result, "id");

        List<VonTtDuyetYDuToanCtResponse> dsRes = ds
                .stream()
                .map(user -> new ModelMapper().map(user, VonTtDuyetYDuToanCtResponse.class))
                .collect(Collectors.toList());

        result.setChiTietList(dsRes);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKems(), item.getId(), VonTtDuyetYDuToan.TABLE_NAME);
        item.setFileDinhKems(fileDinhKems);
        return result;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public VonTtDuyetYDuToanResponse update(VonTtDuyetYDuToanRequest req) throws Exception {
        if (req == null) return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        Optional<VonTtDuyetYDuToan> optional = vonTtDuyetYDuToanRepository.findById(req.getId());
        if (!optional.isPresent())
            throw new Exception("Đề nghị cấp vốn bộ ngành không tồn tại");
        VonTtDuyetYDuToan item = optional.get();
        BeanUtils.copyProperties(req, optional.get(), "id", "soThongTri", "trangThai");
        item.setNgaySua(LocalDate.now());
        item.setNguoiSuaId(userInfo.getId());
        vonTtDuyetYDuToanRepository.save(item);

        Map<Long, VonTtDuyetYDuToanCt> mapChiTiet = ctRepository.findByIdTtdydtIn(Collections.singleton(item.getId()))
                .stream().collect(Collectors.toMap(VonTtDuyetYDuToanCt::getId, Function.identity()));

        List<VonTtDuyetYDuToanCt> chiTiets = this.saveListChiTiet(item.getId(), req.getChiTietList(), mapChiTiet);
        item.setChiTietList(chiTiets);
        if (!CollectionUtils.isEmpty(mapChiTiet.values()))
            ctRepository.deleteAll(mapChiTiet.values());

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKems(), item.getId(), VonTtDuyetYDuToan.TABLE_NAME);
        item.setFileDinhKems(fileDinhKems);
        VonTtDuyetYDuToanResponse res = new VonTtDuyetYDuToanResponse();
        BeanUtils.copyProperties(item, res);

        return res;
    }

    private List<VonTtDuyetYDuToanCt> saveListChiTiet(Long parentId,
                                                      List<VonTtDuyetYDuToanCtRequest> chiTietReqs,
                                                      Map<Long, VonTtDuyetYDuToanCt> mapChiTiet) throws Exception {
        List<VonTtDuyetYDuToanCt> chiTiets = new ArrayList<>();
        for (VonTtDuyetYDuToanCtRequest req : chiTietReqs) {
            Long id = req.getId();
            VonTtDuyetYDuToanCt chiTiet = new VonTtDuyetYDuToanCt();

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
        List<VonTtDuyetYDuToan> lstVonTtDuyetYDuToans = vonTtDuyetYDuToanRepository.findByIdIn(ids);
        if (CollectionUtils.isEmpty(lstVonTtDuyetYDuToans)) {
            throw new Exception("Đề nghị cấp vốn bộ ngành không tồn tại");
        }

        if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");

        fileDinhKemService.deleteMultiple(ids, Collections.singleton(VonTtDuyetYDuToan.TABLE_NAME));


        this.deleteChiTiets(lstVonTtDuyetYDuToans.stream().map(VonTtDuyetYDuToan::getId).collect(Collectors.toSet()));


        vonTtDuyetYDuToanRepository.deleteAll(lstVonTtDuyetYDuToans);
        return true;
    }

    private void deleteChiTiets(Set<Long> ids) {
        List<VonTtDuyetYDuToanCt> ctList = ctRepository.findByIdTtdydtIn(ids);
        if (!CollectionUtils.isEmpty(ctList)) {
            ctRepository.deleteAll(ctList);
        }
    }


    @Override
    public Page<VonTtDuyetYDuToanResponse> search(VonTtDuyetYDuToanSearchRequest req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        List<VonTtDuyetYDuToanResponse> list = vonTtDuyetYDuToanRepository.search(req);
        list.forEach(item -> {
            item.setFileDinhKems(fileDinhKemService.search(item.getId(), Collections.singleton(VonTtDuyetYDuToan.TABLE_NAME)));
        });
        Page<VonTtDuyetYDuToanResponse> page = new PageImpl<>(list, pageable, list.size());

        return page;
    }

    @Override
    public VonTtDuyetYDuToanResponse detail(Long id) throws Exception {
        Optional<VonTtDuyetYDuToan> ttDuyetYDuToan = vonTtDuyetYDuToanRepository.findById(id);

        if (!ttDuyetYDuToan.isPresent())
            throw new Exception("Không tìm thấy dữ liệu.");

        VonTtDuyetYDuToanResponse item = new VonTtDuyetYDuToanResponse();
        BeanUtils.copyProperties(ttDuyetYDuToan.get(), item);
        item.setTenTrangThai(TrangThaiDungChungEnum.getTenById(item.getTrangThai()));
        item.setTenDvi(qlnvDmDonviRepository.findByMaDvi(item.getMaDvi()).getTenDvi());

        // chi tiết
        List<VonTtDuyetYDuToanCt> lstDs = ctRepository.findByIdTtdydtIn(Collections.singleton(id));
        List<VonTtDuyetYDuToanCtResponse> dsRes = lstDs.stream()
                .map(user -> new ModelMapper().map(user, VonTtDuyetYDuToanCtResponse.class))
                .collect(Collectors.toList());
        item.setChiTietList(dsRes);
        // đính kèm
        item.setFileDinhKems(fileDinhKemService.search(id, Collections.singleton(VonTtDuyetYDuToan.TABLE_NAME)));

        return item;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean updateStatus(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        Optional<VonTtDuyetYDuToan> optional = vonTtDuyetYDuToanRepository.findById(stReq.getId());
        if (!optional.isPresent())
            throw new Exception("Thông tri duyệt y dự toán không tồn tại.");

        VonTtDuyetYDuToan item = optional.get();
        String trangThai = item.getTrangThai();
        if (TrangThaiDungChungEnum.CHODUYET_LDV.getId().equals(stReq.getTrangThai())) {
            if (!TrangThaiDungChungEnum.DUTHAO.getId().equals(trangThai))
                return false;

            item.setTrangThai(TrangThaiDungChungEnum.CHODUYET_LDV.getId());
            item.setNguoiGuiDuyetId(userInfo.getId());
            item.setNgayGuiDuyet(LocalDate.now());

        } else if (TrangThaiDungChungEnum.DADUYET_LDV.getId().equals(stReq.getTrangThai())) {
            if (!TrangThaiDungChungEnum.CHODUYET_LDV.getId().equals(trangThai))
                return false;

            item.setTrangThai(TrangThaiDungChungEnum.DADUYET_LDV.getId());
            item.setNguoiPduyetId(userInfo.getId());
            item.setNgayPduyet(LocalDate.now());

        } else if (TrangThaiDungChungEnum.TUCHOI_LDV.getId().equals(stReq.getTrangThai())) {
            if (!TrangThaiDungChungEnum.CHODUYET_LDV.getId().equals(trangThai))
                return false;

            item.setTrangThai(TrangThaiDungChungEnum.TUCHOI_LDV.getId());
            item.setNguoiPduyetId(userInfo.getId());
            item.setNgayPduyet(LocalDate.now());
            item.setLyDoTuChoi(stReq.getLyDoTuChoi());

        } else {
            throw new Exception("Bad request.");
        }

        return true;
    }

    @Override
    public boolean exportToExcel(VonTtDuyetYDuToanSearchRequest req, HttpServletResponse response) throws Exception {
        req.setPaggingReq(new PaggingReq(Integer.MAX_VALUE, 0));
        List<VonTtDuyetYDuToanResponse> list = this.search(req).get().collect(Collectors.toList());

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
                VonTtDuyetYDuToanResponse item = list.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;
                objs[1] = item.getSoThongTri();
                objs[2] = item.getNam();
                objs[3] = LocalDateTimeUtils.localDateToString(item.getNgayLap());
                objs[4] = item.getLyDoChi();
                objs[5] = item.getSoDnCapVon();
                objs[6] = item.getTenDvi();
                objs[7] = item.getTenTrangThai();
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
