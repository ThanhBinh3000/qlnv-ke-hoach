package com.tcdt.qlnvkhoach.service.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagTongHop;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum;
import com.tcdt.qlnvkhoach.enums.TrangThaiEnum;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagTongHopRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPhuongAnGiaRepository;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagTongHopFilterReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagTongHopReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.util.Contains;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KhLtTongHopPagService extends BaseService {
    @Autowired
    private KhLtPagTongHopRepository khLtPagTongHopRepository;

    @Autowired
    private KhLtPhuongAnGiaRepository khLtPhuongAnGiaRepository;

    @Autowired
    private QlnvDmDonviRepository qlnvDmDonviRepository;
    @Autowired
    private ModelMapper mapper;

    public Page<KhLtPagTongHop> searchPage(KhLtPagTongHopSearchReq objReq) throws Exception {
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhLtPagTongHop> data = khLtPagTongHopRepository.selectPage(
                objReq.getNamKh(),
                objReq.getLoaiHh(),
                Contains.convertDateToString(objReq.getNgayKyTu()),
                Contains.convertDateToString(objReq.getNgayKyDen()),
                objReq.getNoiDung(),
                pageable);
        return data;
    }

    public KhLtPagTongHop tongHopData(KhLtPagTongHopFilterReq objReq, HttpServletRequest req) throws Exception {
        List<KhLtPhuongAnGia> listPagTH = khLtPhuongAnGiaRepository.listTongHop(objReq.getLoaiVthh(), objReq.getChungloaiVthh(), objReq.getNamKhoach(), objReq.getLoaiGia(), objReq.getNgayDxuatTu(), objReq.getNgayDxuatDen());
        if (listPagTH.isEmpty()) {
            throw new Exception("Không tìm thấy data tổng hợp");
        }
        List<Long> khLtPagIds = listPagTH.stream().map(KhLtPhuongAnGia::getId).collect(Collectors.toList());
        if (khLtPagIds.isEmpty()) {
            throw new Exception("Không tìm thấy data tổng hợp");
        }
        KhLtPagTongHop pagTH = new KhLtPagTongHop();
        pagTH.setNamTongHop(Long.valueOf(objReq.getNamKhoach()));
        pagTH.setChungLoaiHh(objReq.getChungloaiVthh());
        pagTH.setLoaiGia(objReq.getLoaiGia());
        pagTH.setLoaiHangHoa(objReq.getLoaiVthh());
        try {
            Map<Long, List<Object[]>> khLtMinMaxKQThamDinhs = khLtPhuongAnGiaRepository.listPagWithDonGia(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), khLtPagIds)
                    .stream().collect(Collectors.groupingBy(o -> (Long) o[0]));
            Map<Long, List<Object[]>> khLtMinMaxKQKhaoSats = khLtPhuongAnGiaRepository.listPagWithDonGia(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), khLtPagIds)
                    .stream().collect(Collectors.groupingBy(o -> (Long) o[0]));
            for (KhLtPhuongAnGia pag : listPagTH) {
                List<Object[]> ketquaTDs = khLtMinMaxKQThamDinhs.get(pag.getId());
                List<Object[]> ketquaKSs = khLtMinMaxKQKhaoSats.get(pag.getId());
//            set min max gia khao sat
                if (StringUtils.isEmpty(pagTH.getGiaKsTtTu()) || (ketquaKSs != null && !ketquaKSs.isEmpty() && pagTH.getGiaKsTtTu().compareTo((BigDecimal) ketquaKSs.get(0)[1]) > 0)) {
                    pagTH.setGiaKsTtTu((ketquaKSs != null && !ketquaKSs.isEmpty()) ? (BigDecimal) ketquaKSs.get(0)[1] : null);
                }
                if (StringUtils.isEmpty(pagTH.getGiaKsTtDen()) || (ketquaKSs != null && !ketquaKSs.isEmpty() && pagTH.getGiaKsTtDen().compareTo((BigDecimal) ketquaKSs.get(0)[2]) > 0)) {
                    pagTH.setGiaKsTtDen((ketquaKSs != null && !ketquaKSs.isEmpty()) ? (BigDecimal) ketquaKSs.get(0)[2] : null);
                }
                if (StringUtils.isEmpty(pagTH.getGiaKsTtVatTu()) || (ketquaKSs != null && !ketquaKSs.isEmpty() && pagTH.getGiaKsTtVatTu().compareTo((BigDecimal) ketquaKSs.get(0)[3]) > 0)) {
                    pagTH.setGiaKsTtVatTu((ketquaKSs != null && !ketquaKSs.isEmpty()) ? (BigDecimal) ketquaKSs.get(0)[3] : null);
                }
                if (StringUtils.isEmpty(pagTH.getGiaKsTtVatDen()) || (ketquaKSs != null && !ketquaKSs.isEmpty() && pagTH.getGiaKsTtVatDen().compareTo((BigDecimal) ketquaKSs.get(0)[4]) > 0)) {
                    pagTH.setGiaKsTtVatDen((ketquaKSs != null && !ketquaKSs.isEmpty()) ? (BigDecimal) ketquaKSs.get(0)[4] : null);
                }
//            set min max gia tham dinh
                if (StringUtils.isEmpty(pagTH.getGiaTdTu()) || (ketquaTDs != null && !ketquaTDs.isEmpty() && pagTH.getGiaTdTu().compareTo((BigDecimal) ketquaTDs.get(0)[1]) > 0)) {
                    pagTH.setGiaTdTu((ketquaTDs != null && !ketquaTDs.isEmpty()) ? (BigDecimal) ketquaTDs.get(0)[1] : null);
                }
                if (StringUtils.isEmpty(pagTH.getGiaTdDen()) || (ketquaTDs != null && !ketquaTDs.isEmpty() && pagTH.getGiaTdDen().compareTo((BigDecimal) ketquaTDs.get(0)[2]) > 0)) {
                    pagTH.setGiaTdDen((ketquaTDs != null && !ketquaTDs.isEmpty()) ? (BigDecimal) ketquaTDs.get(0)[2] : null);
                }
                if (StringUtils.isEmpty(pagTH.getGiaTdVatTu()) || (ketquaTDs != null && !ketquaTDs.isEmpty() && pagTH.getGiaTdVatTu().compareTo((BigDecimal) ketquaTDs.get(0)[3]) > 0)) {
                    pagTH.setGiaTdVatTu((ketquaTDs != null && !ketquaTDs.isEmpty()) ? (BigDecimal) ketquaTDs.get(0)[3] : null);
                }
                if (StringUtils.isEmpty(pagTH.getGiaTdVatDen()) || (ketquaTDs != null && !ketquaTDs.isEmpty() && pagTH.getGiaTdVatDen().compareTo((BigDecimal) ketquaTDs.get(0)[4]) > 0)) {
                    pagTH.setGiaTdVatTu((ketquaTDs != null && !ketquaTDs.isEmpty()) ? (BigDecimal) ketquaTDs.get(0)[4] : null);
                }
                //set min max gia de nghi
                if (StringUtils.isEmpty(pagTH.getGiaDnTu()) || pagTH.getGiaDnTu().compareTo(pag.getGiaDeNghi()) > 0) {
                    pagTH.setGiaDnTu(pag.getGiaDeNghi());
                }
                if (StringUtils.isEmpty(pagTH.getGiaDnDen()) || pagTH.getGiaDnDen().compareTo(pag.getGiaDeNghi()) < 0) {
                    pagTH.setGiaDnDen(pag.getGiaDeNghi());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> maDvis = listPagTH.stream().map(KhLtPhuongAnGia::getMaDonVi).collect(Collectors.toList());
        List<QlnvDmDonvi> dvis = qlnvDmDonviRepository.findByMaDviIn(maDvis);
        Map<String, QlnvDmDonvi> listDvi = dvis.stream()
                .collect(Collectors.toMap(QlnvDmDonvi::getMaDvi, Function.identity()));
        listPagTH.forEach(f -> {
            f.setTenDvi(listDvi.get(f.getMaDonVi()).getTenDvi());
        });
        pagTH.setPhuongAnGias(listPagTH);
        return pagTH;
    }

    @Transactional(rollbackFor = Exception.class)
    public KhLtPagTongHop create(KhLtPagTongHopReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        KhLtPagTongHop pagTH = mapper.map(req, KhLtPagTongHop.class);
        pagTH.setTrangThai(TrangThaiEnum.DU_THAO.getId());
        pagTH.setNguoiTaoId(userInfo.getId());
        pagTH.setNgayTao(LocalDate.now());
        pagTH.setNgayTongHop(LocalDate.now());
        pagTH.setTrangThaiTH(TrangThaiEnum.DU_THAO.getId());
        return khLtPagTongHopRepository.save(pagTH);
    }


}
