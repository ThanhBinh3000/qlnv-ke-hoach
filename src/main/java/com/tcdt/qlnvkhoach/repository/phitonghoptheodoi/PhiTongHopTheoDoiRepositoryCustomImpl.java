package com.tcdt.qlnvkhoach.repository.phitonghoptheodoi;


import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.request.phitonghoptheodoi.PhiTongHopTheoDoiSearchRequest;
import com.tcdt.qlnvkhoach.response.phitonghoptheodoi.PhiTongHopTheoDoiResponse;
import com.tcdt.qlnvkhoach.util.QueryUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class PhiTongHopTheoDoiRepositoryCustomImpl implements PhiTongHopTheoDoiRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PhiTongHopTheoDoiResponse> search(PhiTongHopTheoDoiSearchRequest req) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT d.id,d.SO_THONG_TRI,d.MA_DVI_DUOC_DUYET,dv.TEN_DVI as TEN_DVI_DUOC_DUYET,d.SO_LENH_CHI_TIEN,d.CHUONG,d.LOAI,d.KHOAN, ");
        builder.append("d.LY_DO_CHI,d.SO_TIEN,d.DVI_THU_HUONG,d.TRANG_THAI ");
        builder.append("FROM KH_Phi_TH_TDOI d ");

        builder.append("INNER JOIN DM_DONVI dv on dv.MA_DVI = d.MA_DVI_DUOC_DUYET ");
        setConditionSearch(req, builder);

        Query query = em.createNativeQuery(builder.toString(), Tuple.class);
        setParameterSearch(req, query);
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        //Set pageable
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());

        List<?> data = query.getResultList();
        List<PhiTongHopTheoDoiResponse> response = data.stream().map(res -> {
            Tuple item = (Tuple) res;
            PhiTongHopTheoDoiResponse kh = new PhiTongHopTheoDoiResponse();
            kh.setId(item.get("id", BigDecimal.class).longValue());
            kh.setSoThongTri(item.get("SO_THONG_TRI", String.class));
            kh.setMaDviDuocDuyet(item.get("MA_DVI_DUOC_DUYET", String.class));
            kh.setTenDviDuocDuyet(item.get("MA_DVI_DUOC_DUYET", String.class));
            kh.setSoLenhChiTien(item.get("SO_LENH_CHI_TIEN", BigDecimal.class).longValue());
            kh.setChuong(item.get("CHUONG", String.class));
            kh.setLoai(item.get("LOAI", String.class));
            kh.setKhoan(item.get("KHOAN", String.class));
            kh.setLyDoChi(item.get("LY_DO_CHI", String.class));
            kh.setDviThuHuong(item.get("DVI_THU_HUONG", String.class));
            kh.setSoTien(item.get("SO_TIEN", BigDecimal.class));

            kh.setTrangThai(item.get("TRANG_THAI", String.class));
            kh.setTenTrangThai(TrangThaiDungChungEnum.getTenById(kh.getTrangThai()));

            return kh;
        }).collect(Collectors.toList());

        return response;
    }

    private void setConditionSearch(PhiTongHopTheoDoiSearchRequest req, StringBuilder builder) {
        QueryUtils.buildWhereClause(builder);
        if (!StringUtils.isEmpty(req.getSoThongTri())) {
            builder.append("AND ").append("d.SO_THONG_TRI like :soThongTri ");
        }
        if (!StringUtils.isEmpty(req.getMaDviDuocDuyet())) {
            builder.append("AND ").append("d.MA_DVI_DUOC_DUYET = :maDviDuocDuyet ");
        }
        if (!StringUtils.isEmpty(req.getSoLenhChiTien())) {
            builder.append("AND ").append("d.SO_LENH_CHI_TIEN = :soLenhChiTien ");
        }
        if (!StringUtils.isEmpty(req.getChuong())) {
            builder.append("AND ").append("d.CHUONG like :chuong ");
        }
        if (!StringUtils.isEmpty(req.getLoai())) {
            builder.append("AND ").append("d.LOAI like :loai ");
        }
        if (!StringUtils.isEmpty(req.getKhoan())) {
            builder.append("AND ").append("d.KHOAN like :khoan ");
        }
    }

    private void setParameterSearch(PhiTongHopTheoDoiSearchRequest req, Query query) {
        if (!StringUtils.isEmpty(req.getSoThongTri())) {
            query.setParameter("soThongTri", "%" + req.getSoThongTri() + "%");
        }
        if (!StringUtils.isEmpty(req.getMaDviDuocDuyet())) {
            query.setParameter("maDviDuocDuyet", req.getMaDviDuocDuyet());
        }
        if (!StringUtils.isEmpty(req.getSoLenhChiTien())) {
            query.setParameter("soLenhChiTien", req.getSoLenhChiTien());
        }
        if (!StringUtils.isEmpty(req.getChuong())) {
            query.setParameter("chuong", "%" + req.getChuong() + "%");
        }
        if (!StringUtils.isEmpty(req.getLoai())) {
            query.setParameter("loai", "%" + req.getLoai() + "%");
        }
        if (!StringUtils.isEmpty(req.getKhoan())) {
            query.setParameter("khoan", "%" + req.getKhoan() + "%");
        }
    }

}
