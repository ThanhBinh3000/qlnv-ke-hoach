package com.tcdt.qlnvkhoach.service.phitonghoptheodoi;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.phitonghoptheodoi.PhiTongHopTheoDoi;
import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.repository.DanhMucRepository;
import com.tcdt.qlnvkhoach.repository.phitonghoptheodoi.PhiTongHopTheoDoiRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phitonghoptheodoi.PhiTongHopTheoDoiRequest;
import com.tcdt.qlnvkhoach.request.phitonghoptheodoi.PhiTongHopTheoDoiSearchRequest;
import com.tcdt.qlnvkhoach.response.phitonghoptheodoi.PhiTongHopTheoDoiResponse;
import com.tcdt.qlnvkhoach.service.BaseServiceImpl;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.util.ExcelHeaderConst;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import lombok.extern.log4j.Log4j2;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PhiTongHopTheoDoiServiceImpl extends BaseServiceImpl implements PhiTongHopTheoDoiService {

    private final String MA_DS = "/TCDT";
    @Autowired
    FileDinhKemService fileDinhKemService;
    @Autowired
    PhiTongHopTheoDoiRepository repository;
    @Autowired
    DanhMucRepository danhMucRepository;
    private static final String SHEET_NAME = "Tổng hợp theo dõi cấp phí";
    private static final String FILE_NAME = "tong_hop_theo_doi_cap_Phi.xlsx";

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PhiTongHopTheoDoiResponse create(PhiTongHopTheoDoiRequest req) throws Exception {
        if (req == null) return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        PhiTongHopTheoDoi item = new PhiTongHopTheoDoi();

        BeanUtils.copyProperties(req, item, "id");
        Long count = repository.getMaxId();
        if (count == null) count = 1L;
        item.setSoThongTri(count.intValue() + 1 + "/" + LocalDate.now().getYear() + MA_DS);

        item.setTrangThai(TrangThaiDungChungEnum.DUTHAO.getId());
        item.setNgayTao(LocalDate.now());
        item.setNguoiTaoId(userInfo.getId());
        repository.save(item);

        PhiTongHopTheoDoiResponse result = new PhiTongHopTheoDoiResponse();
        BeanUtils.copyProperties(item, result, "id");

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKems(), item.getId(), PhiTongHopTheoDoi.TABLE_NAME);
        result.setFileDinhKems(fileDinhKems);
        return result;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PhiTongHopTheoDoiResponse update(PhiTongHopTheoDoiRequest req) throws Exception {
        if (req == null) return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        Optional<PhiTongHopTheoDoi> optional = repository.findById(req.getId());
        if (!optional.isPresent())
            throw new Exception("Đề nghị cấp phí bộ ngành không tồn tại");
        PhiTongHopTheoDoi item = optional.get();
        BeanUtils.copyProperties(req, optional.get(), "id", "soThongTri", "trangThai");
        item.setNgaySua(LocalDate.now());
        item.setNguoiSuaId(userInfo.getId());
        repository.save(item);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKems(), item.getId(), PhiTongHopTheoDoi.TABLE_NAME);
        item.setFileDinhKems(fileDinhKems);
        PhiTongHopTheoDoiResponse res = new PhiTongHopTheoDoiResponse();
        BeanUtils.copyProperties(item, res);

        return res;
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
        List<PhiTongHopTheoDoi> lstPhiTongHopTheoDois = repository.findByIdIn(ids);
        if (CollectionUtils.isEmpty(lstPhiTongHopTheoDois)) {
            throw new Exception("Đề nghị cấp phí bộ ngành không tồn tại");
        }

        if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");

        fileDinhKemService.deleteMultiple(ids, Collections.singleton(PhiTongHopTheoDoi.TABLE_NAME));

        repository.deleteAll(lstPhiTongHopTheoDois);
        return true;
    }

    @Override
    public Page<PhiTongHopTheoDoiResponse> search(PhiTongHopTheoDoiSearchRequest req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        List<PhiTongHopTheoDoiResponse> list = repository.search(req);
        list.forEach(item -> {
            item.setFileDinhKems(fileDinhKemService.search(item.getId(), Collections.singleton(PhiTongHopTheoDoi.TABLE_NAME)));
        });
        Page<PhiTongHopTheoDoiResponse> page = new PageImpl<>(list, pageable, list.size());

        return page;
    }

    @Override
    public PhiTongHopTheoDoiResponse detail(Long id) throws Exception {
        Optional<PhiTongHopTheoDoi> ttDuyetYDuToan = repository.findById(id);

        if (!ttDuyetYDuToan.isPresent())
            throw new Exception("Không tìm thấy dữ liệu.");

        PhiTongHopTheoDoiResponse item = new PhiTongHopTheoDoiResponse();
        BeanUtils.copyProperties(ttDuyetYDuToan.get(), item);
        item.setTenTrangThai(TrangThaiDungChungEnum.getTenById(item.getTrangThai()));
        item.setTenDviDuocDuyet(danhMucRepository.findByMa(item.getMaDviDuocDuyet()).getGiaTri());

        // đính kèm
        item.setFileDinhKems(fileDinhKemService.search(id, Collections.singleton(PhiTongHopTheoDoi.TABLE_NAME)));

        return item;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean updateStatus(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        Optional<PhiTongHopTheoDoi> optional = repository.findById(stReq.getId());
        if (!optional.isPresent())
            throw new Exception("Thông tri duyệt y dự toán không tồn tại.");

        PhiTongHopTheoDoi item = optional.get();
        String trangThai = item.getTrangThai();
        if (TrangThaiDungChungEnum.BAN_HANH.getId().equals(stReq.getTrangThai())) {
            if (!TrangThaiDungChungEnum.DUTHAO.getId().equals(trangThai))
                return false;

            item.setTrangThai(TrangThaiDungChungEnum.BAN_HANH.getId());
            item.setNguoiPduyetId(userInfo.getId());
            item.setNgayPduyet(LocalDate.now());

        } else {
            throw new Exception("Bad request.");
        }

        return true;
    }

    @Override
    public boolean exportToExcel(PhiTongHopTheoDoiSearchRequest req, HttpServletResponse response) throws Exception {
        req.setPaggingReq(new PaggingReq(Integer.MAX_VALUE, 0));
        List<PhiTongHopTheoDoiResponse> list = this.search(req).get().collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list))
            return true;

        String[] rowsName = new String[]{ExcelHeaderConst.STT,
                ExcelHeaderConst.SO_THONG_TRI,
                ExcelHeaderConst.DON_VI_DUOC_DUYET,
                ExcelHeaderConst.SO_LENH_CHI_TIEN,
                ExcelHeaderConst.CHUONG,
                ExcelHeaderConst.LOAI,
                ExcelHeaderConst.KHOAN,
                ExcelHeaderConst.LY_DO_CHI,
                ExcelHeaderConst.SO_TIEN,
                ExcelHeaderConst.DON_VI_THU_HUONG,
                ExcelHeaderConst.TRANG_THAI
        };

        List<Object[]> dataList = new ArrayList<>();
        Object[] objs;

        try {
            for (int i = 0; i < list.size(); i++) {
                PhiTongHopTheoDoiResponse item = list.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;
                objs[1] = item.getSoThongTri();
                objs[2] = danhMucRepository.findByMa(item.getMaDviDuocDuyet()).getGiaTri();
                objs[3] = item.getSoLenhChiTien();
                objs[4] = item.getChuong();
                objs[5] = item.getLoai();
                objs[6] = item.getKhoan();
                objs[7] = item.getLyDoChi();
                objs[8] = item.getSoTien();
                objs[9] = item.getDviThuHuong();
                objs[10] = item.getTenTrangThai();
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
