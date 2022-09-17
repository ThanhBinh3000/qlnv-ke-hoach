package com.tcdt.qlnvkhoach.repository.denghicapvonbonganh;


import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh_;
import com.tcdt.qlnvkhoach.enums.Operator;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhSearchResponse;
import com.tcdt.qlnvkhoach.table.khotang.*;
import com.tcdt.qlnvkhoach.util.QueryUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class KhDnCapVonBoNganhRepositoryCustomImpl implements KhDnCapVonBoNganhRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<KhDnCapVonBoNganhSearchResponse> search(KhDnCapVonBoNganhSearchRequest req, Pageable pageable) {
		StringBuilder builder = new StringBuilder();
		QueryUtils khDnCapVonBoNganh = QueryUtils.builder().clazz(KhDnCapVonBoNganh.class).alias("khDnCapVonBoNganh").build();
		QueryUtils diemKho = QueryUtils.builder().clazz(KtDiemKho.class).alias("diemKho").build();
		QueryUtils nhaKho = QueryUtils.builder().clazz(KtNhaKho.class).alias("nhaKho").build();
		QueryUtils nganKho = QueryUtils.builder().clazz(KtNganKho.class).alias("nganKho").build();
		QueryUtils nganLo = QueryUtils.builder().clazz(KtNganLo.class).alias("nganLo").build();


		log.debug("Build select query");
		builder.append(QueryUtils.SELECT);
		QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.ID);
		QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.SO_BIEN_BAN);
		QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.NGAY_LAY_MAU);
		QueryUtils.selectFields(builder, diemKho, KtDiemKho_.MA_DIEMKHO);
		QueryUtils.selectFields(builder, diemKho, KtDiemKho_.TEN_DIEMKHO);
		QueryUtils.selectFields(builder, nhaKho, KtNhaKho_.MA_NHAKHO);
		QueryUtils.selectFields(builder, nhaKho, KtNhaKho_.TEN_NHAKHO);
		QueryUtils.selectFields(builder, nganKho, KtNganKho_.MA_NGANKHO);
		QueryUtils.selectFields(builder, nganKho, KtNganKho_.TEN_NGANKHO);
		QueryUtils.selectFields(builder, nganLo, KtNganLo_.MA_NGANLO);
		QueryUtils.selectFields(builder, nganLo, KtNganLo_.TEN_NGANLO);
		QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.TRANG_THAI);

		builder.append(QueryUtils.FROM)
				.append(khDnCapVonBoNganh.buildAliasName())
				.append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, diemKho, KhDnCapVonBoNganh_.MA_DIEM_KHO, KtDiemKho_.MA_DIEMKHO))
				.append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, nhaKho, KhDnCapVonBoNganh_.MA_NHA_KHO, KtNhaKho_.MA_NHAKHO))
				.append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, nganKho, KhDnCapVonBoNganh_.MA_NGAN_KHO, KtNganKho_.MA_NGANKHO))
				.append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, nganLo, KhDnCapVonBoNganh_.MA_NGAN_LO, KtNganLo_.MA_NGANLO));

		log.debug("Set Condition search");
		this.setConditionSearch(req, builder, khDnCapVonBoNganh);

		log.debug("Set sort");
		QueryUtils.buildSort(pageable, builder);

		log.debug("Create query");
		TypedQuery<Object[]> query = em.createQuery(QueryUtils.buildQuery(builder), Object[].class);

		log.debug("Set params");
		this.setParameterSearch(req, query, khDnCapVonBoNganh);
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());

		log.info("Build response");
		List<Object[]> result = query.getResultList();
		List<KhDnCapVonBoNganhSearchResponse> responses = result.stream()
				.map(KhDnCapVonBoNganhSearchResponse::new).collect(Collectors.toList());

		return new PageImpl<>(responses, pageable, this.count(req, khDnCapVonBoNganh, diemKho, nhaKho, nganKho, nganLo));
	}


	private void setConditionSearch(KhDnCapVonBoNganhSearchRequest req, StringBuilder builder, QueryUtils khDnCapVonBoNganh) {
		QueryUtils.buildWhereClause(builder);
		khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.SO_BIEN_BAN, req.getSoBienBan(), builder);
		khDnCapVonBoNganh.start(Operator.AND, KhDnCapVonBoNganh_.NGAY_LAY_MAU, req.getNgayLayMauTuNgay(), builder);
		khDnCapVonBoNganh.end(Operator.AND, KhDnCapVonBoNganh_.NGAY_LAY_MAU, req.getNgayLayMauDenNgay(), builder);
		khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.MA_DIEM_KHO, req.getMaDiemKho(), builder);
		khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.MA_NHA_KHO, req.getMaNhaKho(), builder);
		khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.MA_NGAN_KHO, req.getMaNganKho(), builder);
		khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.MA_NGAN_LO, req.getMaNganLo(), builder);
	}

	private int count(KhDnCapVonBoNganhSearchRequest req, QueryUtils khDnCapVonBoNganh, QueryUtils diemKho, QueryUtils nhaKho, QueryUtils nganKho, QueryUtils nganLo) {
		log.debug("Build count query");
		StringBuilder builder = khDnCapVonBoNganh.countBy(KhDnCapVonBoNganh_.ID);

		builder.append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, diemKho, KhDnCapVonBoNganh_.MA_DIEM_KHO, KtDiemKho_.MA_DIEMKHO))
				.append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, nhaKho, KhDnCapVonBoNganh_.MA_NHA_KHO, KtNhaKho_.MA_NHAKHO))
				.append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, nganKho, KhDnCapVonBoNganh_.MA_NGAN_KHO, KtNganKho_.MA_NGANKHO))
				.append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, nganLo, KhDnCapVonBoNganh_.MA_NGAN_LO, KtNganLo_.MA_NGANLO));

		log.debug("Set condition search");
		this.setConditionSearch(req, builder, khDnCapVonBoNganh);

		log.debug("Create query");
		TypedQuery<Long> query = em.createQuery(builder.toString(), Long.class);

		log.debug("Set parameter");
		this.setParameterSearch(req, query, khDnCapVonBoNganh);

		return query.getSingleResult().intValue();
	}

	private void setParameterSearch(KhDnCapVonBoNganhSearchRequest req, Query query, QueryUtils khDnCapVonBoNganh) {
		khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.SO_BIEN_BAN, req.getSoBienBan());
		khDnCapVonBoNganh.setParamStart(query, KhDnCapVonBoNganh_.NGAY_LAY_MAU, req.getNgayLayMauTuNgay());
		khDnCapVonBoNganh.setParamEnd(query, KhDnCapVonBoNganh_.NGAY_LAY_MAU, req.getNgayLayMauTuNgay());
		khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.MA_DIEM_KHO, req.getMaDiemKho());
		khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.MA_NHA_KHO, req.getMaNhaKho());
		khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.MA_NGAN_KHO, req.getMaNganKho());
		khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.MA_NGAN_LO, req.getMaNganLo());
	}
}
