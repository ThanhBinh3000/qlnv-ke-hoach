package com.tcdt.qlnvkhoachphi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoachphi.enums.ChiTieuKeHoachNamStatus;
import com.tcdt.qlnvkhoachphi.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.util.DataUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

//Ctkhn: Chỉ tiêu kế hoạch năm
public class ChiTieuKeHoachNamRepositoryCustomImpl implements ChiTieuKeHoachNamRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Page<ChiTieuKeHoachNamRes> search(SearchChiTieuKeHoachNamReq req, Pageable pageable) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ct.SO_QUYET_DINH as soQD, ");
		builder.append("ct.NGAY_KY as ngayKy, ");
		builder.append("ct.NAM_KE_HOACH as namKeHoach, ");
		builder.append("ct.TRICH_YEU as trichYeu, ");
		builder.append("ct.ID as id, ");
		builder.append("ct.TRANG_THAI as trangThai ");

		builder.append("FROM CHI_TIEU_KE_HOACH_NAM ct ");
		builder.append("INNER JOIN QLNV_DM_DONVI dv ON dv.ID = ct.DON_VI_ID ");
		builder.append("LEFT JOIN KE_HOACH_LUONG_THUC_MUOI khltm ON khltm.CTKHN_ID = ct.ID ");
		builder.append("LEFT JOIN KE_HOACH_VAT_TU khvt ON khvt.CTKHN_ID = ct.ID ");
		setConditionSearchCtkhn(req, builder);

		builder.append("GROUP BY ct.SO_QUYET_DINH, ct.NGAY_KY, ct.NAM_KE_HOACH, ct.TRICH_YEU, ct.ID, ct.TRANG_THAI ");
		//Sort
		Sort sort = pageable.getSort();
		if (sort.isSorted()) {
			builder.append("ORDER BY ").append(sort.get()
					.map(o -> o.getProperty() + " " + o.getDirection()).collect(Collectors.joining(", ")));
		}

		Query query = em.createNativeQuery(builder.toString(), Tuple.class);

		//Set params
		this.setParameterSearchCtkhn(req, query);

		//Set pageable
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());

		List<?> data = query.getResultList();

		List<ChiTieuKeHoachNamRes> response = data
				.stream()
				.map(res -> {
					Tuple item = (Tuple) res;
					return ChiTieuKeHoachNamRes.builder()
							.id(item.get("id", BigDecimal.class).longValue())
							.soQuyetDinh(item.get("soQD", String.class))
							.ngayKy(DataUtils.convertToLocalDate(item.get("ngayKy", Timestamp.class)))
							.namKeHoach(item.get("namKeHoach", BigDecimal.class).intValue())
							.trichYeu(item.get("trichYeu", String.class))
							.trangThai(item.get("trangThai", String.class))
							.tenTrangThai(ChiTieuKeHoachNamStatus.getTenById(item.get("trangThai", String.class)))
							.build();
				}).collect(Collectors.toList());


		return new PageImpl<>(response, pageable, this.countCtkhn(req));
	}


	private void setConditionSearchCtkhn(SearchChiTieuKeHoachNamReq req, StringBuilder builder) {
		builder.append("WHERE ct.LASTEST = 1 ");

		if (!StringUtils.isEmpty(req.getSoQD())) {
			builder.append("AND ").append("LOWER(ct.SO_QUYET_DINH) LIKE :soQD ");
		}
		if (req.getNgayKyTuNgay() != null) {
			builder.append("AND ").append("ct.NGAY_KY >= :ngayKyTuNgay ");
		}
		if (req.getNgayKyDenNgay() != null) {
			builder.append("AND ").append("ct.NGAY_KY <= :ngayKyDenNgay ");
		}
		if (!StringUtils.isEmpty(req.getTrichYeu())) {
			builder.append("AND ").append("LOWER(ct.TRICH_YEU) LIKE :trichYeu ");
		}
		if (req.getDonViId() != null) {
			builder.append("AND ").append("(khltm.DON_VI_ID = :donViId OR khvt.DON_VI_ID = :donViId) ");
		}

		if (req.getDvql() != null) {
			builder.append("AND ").append("dv.ID = :dvql ");
		}

		if (!StringUtils.isEmpty(req.getLoaiQuyetDinh())) {
			builder.append("AND ").append("ct.LOAI_QUYET_DINH = :loaiQuyetDinh ");
		}

		if (!StringUtils.isEmpty(req.getTrangThai())) {
			builder.append("AND ").append("ct.TRANG_THAI = :trangThai ");
		}

		if (req.getNamKeHoach() != null) {
			builder.append("AND ").append("ct.NAM_KE_HOACH = :namKeHoach ");
		}
	}

	private int countCtkhn(SearchChiTieuKeHoachNamReq req) {
		int total = 0;
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(1) AS totalRecord FROM CHI_TIEU_KE_HOACH_NAM ct ");
		builder.append("INNER JOIN QLNV_DM_DONVI dv on dv.ID = ct.DON_VI_ID ");
		builder.append("LEFT JOIN KE_HOACH_LUONG_THUC_MUOI khltm ON khltm.CTKHN_ID = ct.ID ");
		builder.append("LEFT JOIN KE_HOACH_VAT_TU khvt ON khvt.CTKHN_ID = ct.ID ");

		this.setConditionSearchCtkhn(req, builder);

		Query query = em.createNativeQuery(builder.toString(), Tuple.class);

		this.setParameterSearchCtkhn(req, query);

		List<?> dataCount = query.getResultList();

		if (!CollectionUtils.isEmpty(dataCount)) {
			return total;
		}
		Tuple result = (Tuple) dataCount.get(0);
		return result.get("totalRecord", BigInteger.class).intValue();
	}

	private void setParameterSearchCtkhn(SearchChiTieuKeHoachNamReq req, Query query) {
		if (!StringUtils.isEmpty(req.getSoQD())) {
			query.setParameter("soQD", "%" + req.getSoQD().toLowerCase() + "%");
		}
		if (req.getNgayKyTuNgay() != null) {
			query.setParameter("ngayKyTuNgay", req.getNgayKyTuNgay());
		}
		if (req.getNgayKyDenNgay() != null) {
			query.setParameter("ngayKyDenNgay", req.getNgayKyDenNgay());
		}
		if (!StringUtils.isEmpty(req.getTrichYeu())) {
			query.setParameter("trichYeu", "%" + req.getTrichYeu().toLowerCase() + "%");
		}
		if (req.getDonViId() != null) {
			query.setParameter("donViId", req.getDonViId());
		}

		if (!StringUtils.isEmpty(req.getLoaiQuyetDinh())) {
			query.setParameter("loaiQuyetDinh", req.getLoaiQuyetDinh());
		}

		if (req.getDvql() != null) {
			query.setParameter("dvql", req.getDvql());
		}

		if (!StringUtils.isEmpty(req.getTrangThai())) {
			query.setParameter("trangThai", req.getTrangThai());
		}

		if (req.getNamKeHoach() != null) {
			query.setParameter("namKeHoach", req.getNamKeHoach());
		}
	}
}
