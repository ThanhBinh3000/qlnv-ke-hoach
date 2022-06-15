package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.query.dto.DxDcQueryDto;
import com.tcdt.qlnvkhoach.request.search.catalog.dexuatdieuchinhkehoachnam.SearchDxDcKeHoachNamReq;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class DxDcKeHoachNamRepositoryCustomImpl implements DxDcKeHoachNamRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DxDcQueryDto> search(SearchDxDcKeHoachNamReq req, Collection<String> trangThais) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.tcdt.qlnvkhoach.query.dto.DxDcQueryDto(dx, qd.soQuyetDinh) FROM DxDcKeHoachNam dx ");
        builder.append("INNER JOIN ChiTieuKeHoachNam qd ON dx.keHoachNamId = qd.id ");
        setConditionFilter(req, trangThais, builder);
        builder.append("ORDER BY qd.namKeHoach DESC");

        TypedQuery<DxDcQueryDto> query = em.createQuery(builder.toString(), DxDcQueryDto.class);
        //Set params
        this.setParameterFilter(req, trangThais, query);
        //Set pageable
        query.setFirstResult(req.getPaggingReq().getPage() * req.getPaggingReq().getLimit()).setMaxResults(req.getPaggingReq().getLimit());
        return query.getResultList();
    }

    private void setConditionFilter(SearchDxDcKeHoachNamReq req, Collection<String> trangThais, StringBuilder builder) {
        builder.append("WHERE 1 = 1 ");

        if (req.getNamKeHoach() != null) {
            builder.append("AND ").append("dx.namKeHoach = :namKeHoach ");
        }

        if (StringUtils.hasText(req.getSoVanBan())) {
            builder.append("AND ").append("LOWER(dx.soVanBan) LIKE :soVanBan ");
        }
        if (StringUtils.hasText(req.getSoQuyetDinh())) {
            builder.append("AND ").append("LOWER(qd.soQuyetDinh) LIKE :soQuyetDinh ");
        }

        if (StringUtils.hasText(req.getTrichYeuDx())) {
            builder.append("AND ").append("LOWER(dx.trichYeu) LIKE :trichYeuDx ");
        }
        if (StringUtils.hasText(req.getTrichYeuQd())) {
            builder.append("AND ").append("LOWER(qd.trichYeu) LIKE :trichYeuQd ");
        }

        if (req.getNgayKyTuNgayDx() != null) {
            builder.append("AND ").append("dx.ngayKy >= :ngayKyTuNgayDx ");
        }
        if (req.getNgayKyDenNgayDx() != null) {
            builder.append("AND ").append("dx.ngayKy <= :ngayKyDenNgayDx ");
        }
        if (req.getNgayKyTuNgayQd() != null) {
            builder.append("AND ").append("qd.ngayKy >= :ngayKyTuNgayQd ");
        }
        if (req.getNgayKyDenNgayQd() != null) {
            builder.append("AND ").append("qd.ngayKy <= :ngayKyDenNgayQd ");
        }

        if (StringUtils.hasText(req.getMaDonVi())) {
            builder.append("AND ").append("dx.maDvi = :maDonVi ");
        }

        if (!CollectionUtils.isEmpty(trangThais)) {
            builder.append("AND ").append("dx.trangThai IN :trangThais ");
        }

        if (StringUtils.hasText(req.getLoaiHangHoa())) {
            builder.append("AND ").append("dx.LOAI_HANG_HOA = :loaiHangHoa ");
        }
    }

    @Override
    public Long countDxDcKeHoachNam(SearchDxDcKeHoachNamReq req, Collection<String> trangThais) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(dx.id) FROM DxDcKeHoachNam dx ");
        builder.append("INNER JOIN ChiTieuKeHoachNam qd ON dx.keHoachNamId = qd.id ");

        this.setConditionFilter(req, trangThais, builder);

        TypedQuery<Long> query = em.createQuery(builder.toString(), Long.class);

        this.setParameterFilter(req, trangThais, query);

        return query.getSingleResult();
    }

    private void setParameterFilter(SearchDxDcKeHoachNamReq req, Collection<String> trangThais, Query query) {
        if (req.getMaDonVi() != null) {
            query.setParameter("maDonVi", req.getMaDonVi());
        }

        if (req.getNamKeHoach() != null) {
            query.setParameter("namKeHoach", req.getNamKeHoach());
        }

        if (StringUtils.hasText(req.getSoVanBan())) {
            query.setParameter("soVanBan", "%" + req.getSoVanBan().toLowerCase() + "%");
        }
        if (StringUtils.hasText(req.getSoQuyetDinh())) {
            query.setParameter("soQuyetDinh", "%" + req.getSoQuyetDinh().toLowerCase() + "%");
        }

        if (StringUtils.hasText(req.getTrichYeuDx())) {
            query.setParameter("trichYeuDx", "%" + req.getTrichYeuDx().toLowerCase() + "%");
        }
        if (StringUtils.hasText(req.getTrichYeuQd())) {
            query.setParameter("trichYeuQd", "%" + req.getTrichYeuQd().toLowerCase() + "%");
        }

        if (req.getNgayKyTuNgayDx() != null) {
            query.setParameter("ngayKyTuNgayDx", req.getNgayKyTuNgayDx());
        }
        if (req.getNgayKyDenNgayDx() != null) {
            query.setParameter("ngayKyDenNgayDx", req.getNgayKyDenNgayDx());
        }
        if (req.getNgayKyTuNgayQd() != null) {
            query.setParameter("ngayKyTuNgayQd", req.getNgayKyTuNgayQd());
        }

        if (req.getNgayKyDenNgayQd() != null) {
            query.setParameter("ngayKyDenNgayQd", req.getNgayKyDenNgayQd());
        }

        if (StringUtils.hasText(req.getMaDonVi())) {
            query.setParameter("maDonVi", req.getMaDonVi());
        }

        if (!CollectionUtils.isEmpty(trangThais)) {
            query.setParameter("trangThais", trangThais);
        }

        if (StringUtils.hasText(req.getLoaiHangHoa())) {
            query.setParameter("loaiHangHoa", req.getLoaiHangHoa());
        }
    }
}
