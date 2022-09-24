package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;

import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnThCapPhi;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class KhDnThCapPhiRepositoryCustomImpl implements KhDnThCapPhiRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<KhDnThCapPhi> search(KhDnThCapPhiSearchRequest req, Pageable pageable) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT bb FROM KhDnThCapVon bb ");
        setConditionSearchCtkhn(req, builder);

        //Sort
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            builder.append("ORDER BY ").append(sort.get()
                    .map(o -> o.getProperty() + " " + o.getDirection()).collect(Collectors.joining(", ")));
        }

        TypedQuery<KhDnThCapPhi> query = em.createQuery(builder.toString(), KhDnThCapPhi.class);

        //Set params
        this.setParameterSearchCtkhn(req, query);
        //Set pageable
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }


    private void setConditionSearchCtkhn(KhDnThCapPhiSearchRequest req, StringBuilder builder) {
        builder.append("WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(req.getMaTongHop())) {
            builder.append("AND ").append("LOWER(bb.maTongHop) LIKE :maTongHop ");
        }

        if (req.getNgayTongHopTuNgay() != null) {
            builder.append("AND ").append("bb.ngayTongHop >= :ngayTongHopTuNgay ");
        }
        if (req.getNgayTongHopDenNgay() != null) {
            builder.append("AND ").append("bb.ngayTongHop <= :ngayTongHopDenNgay ");
        }

        if (!CollectionUtils.isEmpty(req.getMaDvis())) {
            builder.append("AND ").append("bb.maDvi IN :maDvis ");
        }

        if (!CollectionUtils.isEmpty(req.getTrangThais())) {
            builder.append("AND ").append("bb.trangThai IN :trangThais ");
        }

        if (req.getNam() != null) {
            builder.append("AND ").append("bb.nam = :nam ");
        }
    }

    @Override
    public int count(KhDnThCapPhiSearchRequest req) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(DISTINCT bb.id) FROM KhDnThCapVon bb ");
        this.setConditionSearchCtkhn(req, builder);

        TypedQuery<Long> query = em.createQuery(builder.toString(), Long.class);

        this.setParameterSearchCtkhn(req, query);
        return query.getSingleResult().intValue();
    }

    private void setParameterSearchCtkhn(KhDnThCapPhiSearchRequest req, Query query) {

        if (!StringUtils.isEmpty(req.getMaTongHop())) {
            query.setParameter("maTongHop", "%" + req.getMaTongHop().toLowerCase() + "%");
        }

        if (req.getNgayTongHopTuNgay() != null) {
            query.setParameter("ngayTongHopTuNgay", req.getNgayTongHopTuNgay());
        }
        if (req.getNgayTongHopDenNgay() != null) {
            query.setParameter("ngayTongHopDenNgay", req.getNgayTongHopDenNgay());
        }

        if (!CollectionUtils.isEmpty(req.getMaDvis())) {
            query.setParameter("maDvis", req.getMaDvis());
        }

        if (!CollectionUtils.isEmpty(req.getTrangThais())) {
            query.setParameter("trangThais", req.getTrangThais());
        }

        if (req.getNam() != null) {
            query.setParameter("nam", req.getNam());
        }
    }
}
