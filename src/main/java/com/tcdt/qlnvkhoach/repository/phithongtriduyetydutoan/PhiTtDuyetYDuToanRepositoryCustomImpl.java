package com.tcdt.qlnvkhoach.repository.phithongtriduyetydutoan;


import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.request.phithongtriduyetydutoan.PhiTtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.phithongtriduyetydutoan.PhiTtDuyetYDuToanResponse;
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
public class PhiTtDuyetYDuToanRepositoryCustomImpl implements PhiTtDuyetYDuToanRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PhiTtDuyetYDuToanResponse> search(PhiTtDuyetYDuToanSearchRequest req) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT d.id,d.SO_THONG_TRI,d.NAM,d.NGAY_LAP,d.LY_DO_CHI,d.SO_DN_CAP_PHI,d.MA_DVI,dv.TEN_DVI,d.TRANG_THAI ");
        builder.append("FROM KH_PHI_TT_DY_DTOAN d ");
        builder.append("INNER JOIN KH_DN_CAP_PHI_BO_NGHANH n ON n.ID = d.SO_DN_CAP_PHI ");
        builder.append("INNER JOIN DM_DONVI dv on dv.MA_DVI = d.MA_DVI ");
        setConditionSearch(req, builder);

        Query query = em.createNativeQuery(builder.toString(), Tuple.class);
        setParameterSearch(req, query);
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        //Set pageable
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());

        List<?> data = query.getResultList();
        List<PhiTtDuyetYDuToanResponse> response = data.stream().map(res -> {
            Tuple item = (Tuple) res;
            PhiTtDuyetYDuToanResponse kh = new PhiTtDuyetYDuToanResponse();
            kh.setId(item.get("id", BigDecimal.class).longValue());
            kh.setSoThongTri(item.get("SO_THONG_TRI", String.class));
            kh.setNam(item.get("NAM", BigDecimal.class).intValue());

            Timestamp x = item.get("NGAY_LAP", Timestamp.class);
            kh.setNgayLap(LocalDate.parse(x.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));

            kh.setLyDoChi(item.get("LY_DO_CHI", String.class));
            kh.setSoDnCapPhi(item.get("SO_DN_CAP_PHI", BigDecimal.class).longValue());
            kh.setTenDvi(item.get("TEN_DVI", String.class));
            kh.setMaDvi(item.get("MA_DVI", String.class));

            kh.setTrangThai(item.get("TRANG_THAI", String.class));
            kh.setTenTrangThai(TrangThaiDungChungEnum.getTenById(kh.getTrangThai()));

            return kh;
        }).collect(Collectors.toList());

        return response;
    }


    private void setConditionSearch(PhiTtDuyetYDuToanSearchRequest req, StringBuilder builder) {
        QueryUtils.buildWhereClause(builder);
        if (!StringUtils.isEmpty(req.getSoThongTri())) {
            builder.append("AND ").append("d.SO_THONG_TRI like :soThongTri ");
        }
        if (!StringUtils.isEmpty(req.getNam())) {
            builder.append("AND ").append("d.NAM = :nam ");
        }
        if (!StringUtils.isEmpty(req.getTuNgay())) {
            builder.append("AND ").append("d.NGAY_LAP >= :tuNgay ");
        }
        if (!StringUtils.isEmpty(req.getDenNgay())) {
            builder.append("AND ").append("d.NGAY_LAP <= :denNgay ");
        }
        if (!StringUtils.isEmpty(req.getLyDoChi())) {
            builder.append("AND ").append("d.LY_DO_CHI like :lydoChi ");
        }
    }

    private void setParameterSearch(PhiTtDuyetYDuToanSearchRequest req, Query query) {
        if (!StringUtils.isEmpty(req.getSoThongTri())) {
            query.setParameter("soThongTri", "%" + req.getSoThongTri() + "%");
        }
        if (!StringUtils.isEmpty(req.getNam())) {
            query.setParameter("nam", req.getNam());
        }
        if (!StringUtils.isEmpty(req.getTuNgay())) {
            query.setParameter("tuNgay", req.getTuNgay());
        }
        if (!StringUtils.isEmpty(req.getDenNgay())) {
            query.setParameter("denNgay", req.getDenNgay());
        }
        if (!StringUtils.isEmpty(req.getLyDoChi())) {
            query.setParameter("lydoChi", "%" + req.getLyDoChi() + "%");
        }
    }

}
