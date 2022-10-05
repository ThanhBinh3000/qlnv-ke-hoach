package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;


import com.tcdt.qlnvkhoach.entities.QlnvDanhMuc;
import com.tcdt.qlnvkhoach.entities.QlnvDanhMuc_;
import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganh;
import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganhCt1;
import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganhCt2;
import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganh_;
import com.tcdt.qlnvkhoach.enums.Operator;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhSearchResponse;
import com.tcdt.qlnvkhoach.util.QueryUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class KhDnCapPhiBoNganhRepositoryCustomImpl implements KhDnCapPhiBoNganhRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private KhDnCapPhiBoNganhCt1Repository ct1Repository;

	@Autowired
	private KhDnCapPhiBoNganhCt2Repository ct2Repository;

	@Override
	public Page<KhDnCapPhiBoNganhSearchResponse> search(KhDnCapPhiBoNganhSearchRequest req, Pageable pageable) {
		StringBuilder builder = new StringBuilder();
		QueryUtils khDnCapPhiBoNganh = QueryUtils.builder().clazz(KhDnCapPhiBoNganh.class).alias("khDnCapPhiBoNganh").build();
		QueryUtils dmDungChung = QueryUtils.builder().clazz(QlnvDanhMuc.class).alias("dmDungChung").build();


		log.debug("Build select query");
		builder.append(QueryUtils.SELECT);
		QueryUtils.selectFields(builder, khDnCapPhiBoNganh, KhDnCapPhiBoNganh_.ID);
		QueryUtils.selectFields(builder, khDnCapPhiBoNganh, KhDnCapPhiBoNganh_.SO_DE_NGHI);
		QueryUtils.selectFields(builder, dmDungChung, QlnvDanhMuc_.GIA_TRI);
		QueryUtils.selectFields(builder, khDnCapPhiBoNganh, KhDnCapPhiBoNganh_.NGAY_DE_NGHI);
		QueryUtils.selectFields(builder, khDnCapPhiBoNganh, KhDnCapPhiBoNganh_.NAM);
		QueryUtils.selectFields(builder, khDnCapPhiBoNganh, KhDnCapPhiBoNganh_.TRANG_THAI);

		builder.append(QueryUtils.FROM)
				.append(khDnCapPhiBoNganh.buildAliasName())
				.append(QueryUtils.buildInnerJoin(khDnCapPhiBoNganh, dmDungChung, KhDnCapPhiBoNganh_.MA_BO_NGANH, QlnvDanhMuc_.MA));

		log.debug("Set Condition search");
		this.setConditionSearch(req, builder, khDnCapPhiBoNganh, dmDungChung);

		log.debug("Set sort");
		QueryUtils.buildSort(pageable, builder);

		log.debug("Create query");
		TypedQuery<Object[]> query = em.createQuery(QueryUtils.buildQuery(builder), Object[].class);

		log.debug("Set params");
		this.setParameterSearch(req, query, khDnCapPhiBoNganh, dmDungChung);
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());

		log.info("Build response");
		List<Object[]> result = query.getResultList();
		List<KhDnCapPhiBoNganhSearchResponse> responses = result.stream()
				.map(KhDnCapPhiBoNganhSearchResponse::new).collect(Collectors.toList());
		//Build thông tin tổng tiền, kinh phí đã cấp, yêu cầu cấp thêm
		this.buildSearchResponse(responses);

		return new PageImpl<>(responses, pageable, this.count(req, khDnCapPhiBoNganh, dmDungChung));
	}


	private void setConditionSearch(KhDnCapPhiBoNganhSearchRequest req, StringBuilder builder, QueryUtils khDnCapPhiBoNganh, QueryUtils dmDungChung) {
		QueryUtils.buildWhereClause(builder);
		khDnCapPhiBoNganh.eq(Operator.AND, KhDnCapPhiBoNganh_.SO_DE_NGHI, req.getSoDeNghi(), builder);
		khDnCapPhiBoNganh.eq(Operator.AND, KhDnCapPhiBoNganh_.MA_BO_NGANH, req.getMaBoNganh(), builder);
		khDnCapPhiBoNganh.eq(Operator.AND, KhDnCapPhiBoNganh_.NAM, req.getNam(), builder);
		khDnCapPhiBoNganh.start(Operator.AND, KhDnCapPhiBoNganh_.NGAY_DE_NGHI, req.getNgayDeNghiTuNgay(), builder);
		khDnCapPhiBoNganh.end(Operator.AND, KhDnCapPhiBoNganh_.NGAY_DE_NGHI, req.getNgayDeNghiDenNgay(), builder);
		dmDungChung.eq(Operator.AND, QlnvDanhMuc_.LOAI, "BO_NGANH", builder);

	}

	private int count(KhDnCapPhiBoNganhSearchRequest req, QueryUtils khDnCapPhiBoNganh, QueryUtils dmDungChung) {
		log.debug("Build count query");
		StringBuilder builder = khDnCapPhiBoNganh.countBy(KhDnCapPhiBoNganh_.ID);

		builder.append(QueryUtils.buildInnerJoin(khDnCapPhiBoNganh, dmDungChung, KhDnCapPhiBoNganh_.MA_BO_NGANH, QlnvDanhMuc_.MA));

		log.debug("Set condition search");
		this.setConditionSearch(req, builder, khDnCapPhiBoNganh, dmDungChung);

		log.debug("Create query");
		TypedQuery<Long> query = em.createQuery(builder.toString(), Long.class);

		log.debug("Set parameter");
		this.setParameterSearch(req, query, khDnCapPhiBoNganh, dmDungChung);

		return query.getSingleResult().intValue();
	}

	private void setParameterSearch(KhDnCapPhiBoNganhSearchRequest req, Query query, QueryUtils khDnCapPhiBoNganh, QueryUtils dmDungChung) {
		khDnCapPhiBoNganh.setParam(query, KhDnCapPhiBoNganh_.SO_DE_NGHI, req.getSoDeNghi());
		khDnCapPhiBoNganh.setParam(query, KhDnCapPhiBoNganh_.MA_BO_NGANH, req.getMaBoNganh());
		khDnCapPhiBoNganh.setParam(query, KhDnCapPhiBoNganh_.NAM, req.getNam());
		khDnCapPhiBoNganh.setParamStart(query, KhDnCapPhiBoNganh_.NGAY_DE_NGHI, req.getNgayDeNghiTuNgay());
		khDnCapPhiBoNganh.setParamEnd(query, KhDnCapPhiBoNganh_.NGAY_DE_NGHI, req.getNgayDeNghiDenNgay());
		dmDungChung.setParam(query, QlnvDanhMuc_.LOAI, "BO_NGANH");

	}

	private void buildSearchResponse (List<KhDnCapPhiBoNganhSearchResponse> responses) {
		Set<Long> ids = responses.stream().map(KhDnCapPhiBoNganhSearchResponse::getId).collect(Collectors.toSet());
		List<KhDnCapPhiBoNganhCt1> ct1List = ct1Repository.findByDnCapPhiIdIn(ids);
		if (CollectionUtils.isEmpty(ct1List)) return;

		Set<Long> ct1Ids = ct1List.stream().map(KhDnCapPhiBoNganhCt1::getId).collect(Collectors.toSet());
		List<KhDnCapPhiBoNganhCt2> ct2List = ct2Repository.findByCapPhiBoNghanhCt1IdIn(ct1Ids);

		Map<Long, List<KhDnCapPhiBoNganhCt2>> chiTiet2Map = ct2List.stream().collect(Collectors.groupingBy(KhDnCapPhiBoNganhCt2::getCapPhiBoNghanhCt1Id));



		//group chi tiết : key = deNghiCapVonBoNganhId, value = List<KhDnCapPhiBoNganhCt>
		Map<Long, List<KhDnCapPhiBoNganhCt1>> chiTietMap = ct1List.stream().collect(Collectors.groupingBy(KhDnCapPhiBoNganhCt1::getDnCapPhiId));


		responses.forEach(item -> {
			List<KhDnCapPhiBoNganhCt1> ctList = chiTietMap.get(item.getId());
			if (CollectionUtils.isEmpty(ctList)) return;
			BigDecimal tongTien = ctList.stream()
					.map(ct1 -> chiTiet2Map.get(ct1.getId()).stream().map(KhDnCapPhiBoNganhCt2::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			item.setTongTien(tongTien);

			BigDecimal kinhPhiDaCap = ctList.stream()
					.map(ct1 -> chiTiet2Map.get(ct1.getId()).stream().map(KhDnCapPhiBoNganhCt2::getKinhPhiDaCap).reduce(BigDecimal.ZERO, BigDecimal::add))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			item.setKinhPhiDaCap(kinhPhiDaCap);

			BigDecimal ycCapThem = ctList.stream()
					.map(ct1 -> chiTiet2Map.get(ct1.getId()).stream().map(KhDnCapPhiBoNganhCt2::getYeuCauCapThem).reduce(BigDecimal.ZERO, BigDecimal::add))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			item.setYcCapThem(ycCapThem);
		});

	}
}
