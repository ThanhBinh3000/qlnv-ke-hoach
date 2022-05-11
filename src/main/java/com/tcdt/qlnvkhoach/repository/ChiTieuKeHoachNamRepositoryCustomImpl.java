package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachNamStatus;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoach.util.DataUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Ctkhn: Chỉ tiêu kế hoạch năm
public class ChiTieuKeHoachNamRepositoryCustomImpl implements ChiTieuKeHoachNamRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<ChiTieuKeHoachNamRes> search(SearchChiTieuKeHoachNamReq req) {
		String loaiQd = req.getLoaiQuyetDinh();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ct.SO_QUYET_DINH as soQD, ");
		builder.append("ct.NGAY_KY as ngayKy, ");
		builder.append("ct.NAM_KE_HOACH as namKeHoach, ");
		builder.append("ct.TRICH_YEU as trichYeu, ");
		builder.append("ct.ID as id, ");
		builder.append("ct.TRANG_THAI as trangThai, ");
		builder.append("ct.LY_DO_TU_CHOI as lyDoTuChoi ");

		if (ChiTieuKeHoachEnum.QD_DC.getValue().equals(loaiQd)) {
			builder.append(", qdGoc.ID as qdGocId, ");
			builder.append("qdGoc.SO_QUYET_DINH as soQDGoc ");
		}

		builder.append("FROM CHI_TIEU_KE_HOACH_NAM ct ");
		builder.append("INNER JOIN DM_DONVI dv ON dv.ID = ct.DON_VI_ID ");

		if (ChiTieuKeHoachEnum.QD_DC.getValue().equals(loaiQd)) {
			builder.append("INNER JOIN CHI_TIEU_KE_HOACH_NAM qdGoc ON ct.QD_GOC_ID = qdGoc.ID ");
		}
		builder.append("LEFT JOIN KE_HOACH_LUONG_THUC_MUOI khltm ON khltm.CTKHN_ID = ct.ID ");
		builder.append("LEFT JOIN KE_HOACH_VAT_TU khvt ON khvt.CTKHN_ID = ct.ID ");
		setConditionSearchCtkhn(req, builder);

		builder.append("GROUP BY ct.SO_QUYET_DINH, ct.NGAY_KY, ct.NAM_KE_HOACH, ct.TRICH_YEU, ct.ID, ct.TRANG_THAI, ct.LY_DO_TU_CHOI ");
		if (ChiTieuKeHoachEnum.QD_DC.getValue().equals(loaiQd)) {
			builder.append(", qdGoc.ID, qdGoc.SO_QUYET_DINH ");
		}
		builder.append("ORDER BY ct.NGAY_KY DESC");

		Query query = em.createNativeQuery(builder.toString(), Tuple.class);

		//Set params
		this.setParameterSearchCtkhn(req, query);

		//Set pageable
		query.setFirstResult(req.getPaggingReq().getPage() * req.getPaggingReq().getLimit()).setMaxResults(req.getPaggingReq().getLimit());

		List<?> data = query.getResultList();

		List<ChiTieuKeHoachNamRes> response = data
				.stream()
				.map(res -> {
					Tuple item = (Tuple) res;
					ChiTieuKeHoachNamRes chiTieuKeHoachNamRes = ChiTieuKeHoachNamRes.builder()
							.id(item.get("id", BigDecimal.class).longValue())
							.soQuyetDinh(item.get("soQD", String.class))
							.ngayKy(DataUtils.convertToLocalDate(item.get("ngayKy", Timestamp.class)))
							.namKeHoach(item.get("namKeHoach", BigDecimal.class).intValue())
							.trichYeu(item.get("trichYeu", String.class))
							.trangThai(item.get("trangThai", String.class))
							.tenTrangThai(ChiTieuKeHoachNamStatus.getTenById(item.get("trangThai", String.class)))
							.lyDoTuChoi(item.get("lyDoTuChoi", String.class))
							.build();

					if (ChiTieuKeHoachEnum.QD_DC.getValue().equals(loaiQd)) {
						chiTieuKeHoachNamRes.setQdGocId(item.get("qdGocId", BigDecimal.class).longValue());
						chiTieuKeHoachNamRes.setSoQdGoc(item.get("soQDGoc", String.class));
					}
					return chiTieuKeHoachNamRes;
				}).collect(Collectors.toList());

		int page = Optional.ofNullable(req.getPaggingReq()).map(PaggingReq::getPage).orElse(BaseRequest.DEFAULT_PAGE);
		int limit = Optional.ofNullable(req.getPaggingReq()).map(PaggingReq::getLimit).orElse(BaseRequest.DEFAULT_LIMIT);
		return new PageImpl<>(response, PageRequest.of(page, limit), this.countCtkhn(req));
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

		if (!StringUtils.isEmpty(req.getDvql())) {
			builder.append("AND ").append("dv.MA_DVI = :dvql ");
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

		if (!StringUtils.isEmpty(req.getCapDvi())) {
			builder.append("AND ").append("dv.CAP_DVI = :capDvi ");
		}
	}

	private int countCtkhn(SearchChiTieuKeHoachNamReq req) {
		int total = 0;
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT ct.ID FROM CHI_TIEU_KE_HOACH_NAM ct ");
		builder.append("INNER JOIN DM_DONVI dv on dv.ID = ct.DON_VI_ID ");
		builder.append("LEFT JOIN KE_HOACH_LUONG_THUC_MUOI khltm ON khltm.CTKHN_ID = ct.ID ");
		builder.append("LEFT JOIN KE_HOACH_VAT_TU khvt ON khvt.CTKHN_ID = ct.ID ");

		this.setConditionSearchCtkhn(req, builder);

		Query query = em.createNativeQuery(builder.toString(), Tuple.class);

		this.setParameterSearchCtkhn(req, query);

		List<?> dataCount = query.getResultList();

		if (CollectionUtils.isEmpty(dataCount)) {
			return total;
		}
		return dataCount.size();
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

		if (!StringUtils.isEmpty(req.getDvql())) {
			query.setParameter("dvql", req.getDvql());
		}

		if (!StringUtils.isEmpty(req.getTrangThai())) {
			query.setParameter("trangThai", req.getTrangThai());
		}

		if (req.getNamKeHoach() != null) {
			query.setParameter("namKeHoach", req.getNamKeHoach());
		}

		if (!StringUtils.isEmpty(req.getCapDvi())) {
			query.setParameter("capDvi", req.getCapDvi());
		}
	}
}
