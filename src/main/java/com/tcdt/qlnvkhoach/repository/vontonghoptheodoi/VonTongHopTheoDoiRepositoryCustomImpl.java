package com.tcdt.qlnvkhoach.repository.vontonghoptheodoi;


import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.request.vontonghoptheodoi.VonTongHopTheoDoiSearchRequest;
import com.tcdt.qlnvkhoach.response.vontonghoptheodoi.VonTongHopTheoDoiResponse;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class VonTongHopTheoDoiRepositoryCustomImpl implements VonTongHopTheoDoiRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<VonTongHopTheoDoiResponse> search(VonTongHopTheoDoiSearchRequest req) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT d.id,d.SO_THONG_TRI,d.MA_DVI,dv.TEN_DVI,d.SO_LENH_CHI_TIEN,d.CHUONG,d.LOAI,d.KHOAN, ");
        builder.append("d.LY_DO_CHI,d.SO_TIEN,d.MA_DVI_THU_HUONG,d.TRANG_THAI ");
        builder.append("FROM KH_VON_TT_DY_DTOAN d ");
        builder.append("INNER JOIN KH_DN_CAP_VON_BO_NGANH n ON n.ID = d.SO_DN_CAP_VON ");
        builder.append("INNER JOIN DM_DONVI dv on dv.MA_DVI = d.MA_DVI ");
        setConditionSearch(req, builder);

        Query query = em.createNativeQuery(builder.toString(), Tuple.class);
        setParameterSearch(req, query);
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        //Set pageable
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());

        List<?> data = query.getResultList();
        List<VonTongHopTheoDoiResponse> response = data.stream().map(res -> {
            Tuple item = (Tuple) res;
            VonTongHopTheoDoiResponse kh = new VonTongHopTheoDoiResponse();
            kh.setId(item.get("id", BigDecimal.class).longValue());
            kh.setSoThongTri(item.get("SO_THONG_TRI", String.class));
            kh.setNam(item.get("NAM", BigDecimal.class).intValue());

            Timestamp x = item.get("NGAY_LAP", Timestamp.class);
            kh.setNgayLap(LocalDate.parse(x.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));

            kh.setLyDoChi(item.get("LY_DO_CHI", String.class));
            kh.setSoDnCapVon(item.get("SO_DN_CAP_VON", BigDecimal.class).longValue());
            kh.setTenDvi(item.get("TEN_DVI", String.class));
            kh.setMaDvi(item.get("MA_DVI", String.class));

            kh.setTrangThai(item.get("TRANG_THAI", String.class));
            kh.setTenTrangThai(TrangThaiDungChungEnum.getTenById(kh.getTrangThai()));

            return kh;
        }).collect(Collectors.toList());

        return response;
    }


    private void setConditionSearch(VonTongHopTheoDoiSearchRequest req, StringBuilder builder) {
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

    private void setParameterSearch(VonTongHopTheoDoiSearchRequest req, Query query) {
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
