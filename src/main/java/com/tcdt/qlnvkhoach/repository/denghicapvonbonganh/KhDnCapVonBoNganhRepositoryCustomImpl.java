package com.tcdt.qlnvkhoach.repository.denghicapvonbonganh;


import com.tcdt.qlnvkhoach.entities.QlnvDanhMuc;
import com.tcdt.qlnvkhoach.entities.QlnvDanhMuc_;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganhCt;
import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh_;
import com.tcdt.qlnvkhoach.enums.Operator;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhSearchResponse;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi_;
import com.tcdt.qlnvkhoach.util.QueryUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class KhDnCapVonBoNganhRepositoryCustomImpl implements KhDnCapVonBoNganhRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private KhDnCapVonBoNganhCtRepository chiTietRepository;

    @Override
    public Page<KhDnCapVonBoNganhSearchResponse> search(KhDnCapVonBoNganhSearchRequest req, Pageable pageable) {
        StringBuilder builder = new StringBuilder();
        QueryUtils khDnCapVonBoNganh = QueryUtils.builder().clazz(KhDnCapVonBoNganh.class).alias("khDnCapVonBoNganh").build();
        QueryUtils dmDonVi = QueryUtils.builder().clazz(QlnvDmDonvi.class).alias("dmDonVi").build();


        log.debug("Build select query");
        builder.append(QueryUtils.SELECT);
        QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.ID);
        QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.SO_DE_NGHI);
        QueryUtils.selectFields(builder, dmDonVi, QlnvDmDonvi_.TEN_DVI);
        QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.NGAY_DE_NGHI);
        QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.NAM);
        QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.TRANG_THAI);
        QueryUtils.selectFields(builder, khDnCapVonBoNganh, KhDnCapVonBoNganh_.NGAY_SUA);

        builder.append(QueryUtils.FROM)
                .append(khDnCapVonBoNganh.buildAliasName())
                .append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, dmDonVi, KhDnCapVonBoNganh_.MA_BO_NGANH, QlnvDmDonvi_.CODE));

        log.debug("Set Condition search");
        this.setConditionSearch(req, builder, khDnCapVonBoNganh, dmDonVi);

        log.debug("Set sort");
//		QueryUtils.buildSort(pageable, builder);
        builder.append("ORDER BY ").append("khDnCapVonBoNganh." + KhDnCapVonBoNganh_.NGAY_SUA + " desc");

        log.debug("Create query");
        TypedQuery<Object[]> query = em.createQuery(QueryUtils.buildQuery(builder), Object[].class);

        log.debug("Set params");
        this.setParameterSearch(req, query, khDnCapVonBoNganh, dmDonVi);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());

        log.info("Build response");
        List<Object[]> result = query.getResultList();
        List<KhDnCapVonBoNganhSearchResponse> responses = result.stream()
                .map(KhDnCapVonBoNganhSearchResponse::new).collect(Collectors.toList());
        //Build thông tin tổng tiền, kinh phí đã cấp, yêu cầu cấp thêm
        this.buildSearchResponse(responses);
        //Build thêm data của TCDT NN khi tổng hợp
        if (!StringUtils.isEmpty(req.getType()) && req.getType().equals("TH") && req.getLoaiTh().equals("ALL")) {
            List<Object[]> abcd = chiTietRepository.getDnCapVonDonViByNam(req.getNam());
            this.addResponseDataBtc(responses, abcd, req.getNam());
        }

        return new PageImpl<>(responses, pageable, this.count(req, khDnCapVonBoNganh, dmDonVi));
    }


    @Override
    public List<KhDnCapVonBoNganhSearchResponse> loadDataThTCDT(KhDnThCapVonSearchRequest req) {
        List<KhDnCapVonBoNganhSearchResponse> responseList = new ArrayList<>();
        if(!StringUtils.isEmpty(req.getNguonTongHop()) && req.getNguonTongHop().equals("TCDT") ){
            List<Object[]> abcd = chiTietRepository.getDnCapVonDonViByNam(req.getNam());
            this.addResponseDataBtc(responseList, abcd, req.getNam());
        }
        return responseList;
    }

    private void setConditionSearch(KhDnCapVonBoNganhSearchRequest req, StringBuilder builder, QueryUtils khDnCapVonBoNganh, QueryUtils dmDonVi) {
        QueryUtils.buildWhereClause(builder);
        khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.SO_DE_NGHI, req.getSoDeNghi(), builder);
        khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.MA_BO_NGANH, req.getMaBoNganh(), builder);
        khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.NAM, req.getNam(), builder);
        khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.TRANG_THAI, req.getTrangThai(), builder);
        khDnCapVonBoNganh.eq(Operator.AND, KhDnCapVonBoNganh_.TRANG_THAI_TH, req.getTrangThaiTh(), builder);
        khDnCapVonBoNganh.start(Operator.AND, KhDnCapVonBoNganh_.NGAY_DE_NGHI, req.getNgayDeNghiTuNgay(), builder);
        khDnCapVonBoNganh.end(Operator.AND, KhDnCapVonBoNganh_.NGAY_DE_NGHI, req.getNgayDeNghiDenNgay(), builder);
        dmDonVi.eq(Operator.AND, QlnvDmDonvi_.CAP_DVI, "0", builder);

    }

    private int count(KhDnCapVonBoNganhSearchRequest req, QueryUtils khDnCapVonBoNganh, QueryUtils dmDonVi) {
        log.debug("Build count query");
        StringBuilder builder = khDnCapVonBoNganh.countBy(KhDnCapVonBoNganh_.ID);

        builder.append(QueryUtils.buildInnerJoin(khDnCapVonBoNganh, dmDonVi, KhDnCapVonBoNganh_.MA_BO_NGANH, QlnvDmDonvi_.CODE));

        log.debug("Set condition search");
        this.setConditionSearch(req, builder, khDnCapVonBoNganh, dmDonVi);

        log.debug("Create query");
        TypedQuery<Long> query = em.createQuery(builder.toString(), Long.class);

        log.debug("Set parameter");
        this.setParameterSearch(req, query, khDnCapVonBoNganh, dmDonVi);

        return query.getSingleResult().intValue();
    }

    private void setParameterSearch(KhDnCapVonBoNganhSearchRequest req, Query query, QueryUtils khDnCapVonBoNganh, QueryUtils dmDonVi) {
        khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.SO_DE_NGHI, req.getSoDeNghi());
        khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.MA_BO_NGANH, req.getMaBoNganh());
        khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.NAM, req.getNam());
        khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.TRANG_THAI, req.getTrangThai());
        khDnCapVonBoNganh.setParam(query, KhDnCapVonBoNganh_.TRANG_THAI_TH, req.getTrangThaiTh());
        khDnCapVonBoNganh.setParamStart(query, KhDnCapVonBoNganh_.NGAY_DE_NGHI, req.getNgayDeNghiTuNgay());
        khDnCapVonBoNganh.setParamEnd(query, KhDnCapVonBoNganh_.NGAY_DE_NGHI, req.getNgayDeNghiDenNgay());
        khDnCapVonBoNganh.setParamEnd(query, KhDnCapVonBoNganh_.NGAY_DE_NGHI, req.getNgayDeNghiDenNgay());
        dmDonVi.setParam(query, QlnvDmDonvi_.CAP_DVI, "0");
    }

    private void buildSearchResponse(List<KhDnCapVonBoNganhSearchResponse> responses) {
        Set<Long> ids = responses.stream().map(KhDnCapVonBoNganhSearchResponse::getId).collect(Collectors.toSet());
        List<KhDnCapVonBoNganhCt> chiTietList = chiTietRepository.findByDeNghiCapVonBoNganhIdIn(ids);
        if (CollectionUtils.isEmpty(chiTietList)) return;

        //group chi tiết : key = deNghiCapVonBoNganhId, value = List<KhDnCapVonBoNganhCt>
        Map<Long, List<KhDnCapVonBoNganhCt>> chiTietMap = chiTietList.stream().collect(Collectors.groupingBy(KhDnCapVonBoNganhCt::getDeNghiCapVonBoNganhId));

        responses.forEach(item -> {
            List<KhDnCapVonBoNganhCt> ctList = chiTietMap.get(item.getId());
            if (CollectionUtils.isEmpty(ctList)) return;
            BigDecimal tongTien = ctList.stream()
                    .map(KhDnCapVonBoNganhCt::getThanhTien)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            item.setTongTien(tongTien);

            BigDecimal kinhPhiDaCap = ctList.stream()
                    .map(KhDnCapVonBoNganhCt::getKinhPhiDaCap)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            item.setKinhPhiDaCap(kinhPhiDaCap);

            BigDecimal ycCapThem = ctList.stream()
                    .map(KhDnCapVonBoNganhCt::getYcCapThem)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            item.setYcCapThem(ycCapThem);
        });

    }

    private void addResponseDataBtc(List<KhDnCapVonBoNganhSearchResponse> responses, List<Object[]> dataBtc, Integer nam) {
        KhDnCapVonBoNganhSearchResponse btc = new KhDnCapVonBoNganhSearchResponse();
        BigDecimal tongTien = BigDecimal.ZERO;
        BigDecimal kpDaCap = BigDecimal.ZERO;
        BigDecimal ycCapThem = BigDecimal.ZERO;
        List<KhDnCapVonBoNganhSearchResponse> arrayDetail = new ArrayList<>();
        Integer i = 0;
        if (!CollectionUtils.isEmpty(dataBtc)) {
            btc.setTenBoNganh("Tổng cục Dự Trữ");
            for (Object[] item : dataBtc) {
                KhDnCapVonBoNganhSearchResponse itemDetail = new KhDnCapVonBoNganhSearchResponse();
                itemDetail.setTenBoNganh((String) item[4]);
                itemDetail.setTongTien((BigDecimal) item[0]);
                itemDetail.setKinhPhiDaCap((BigDecimal) item[1]);
                itemDetail.setYcCapThem((BigDecimal) item[2]);
                itemDetail.setTenBoNganh((String) item[4]);
                itemDetail.setNgayDeNghi(LocalDate.parse((String) item[6]));
                itemDetail.setNam(nam);
                itemDetail.setMaBn("BTC");
                itemDetail.setLoaiHang((String) item[5]);
                itemDetail.setIsSum(Boolean.FALSE);
                itemDetail.setLoaiBn("TCDT");
                itemDetail.setParentName("Tổng cục Dự Trữ");
                itemDetail.setSoDeNghi((String) item[3]);
                responses.add(i, itemDetail);
                tongTien = tongTien.add((BigDecimal) item[0]);
                kpDaCap = kpDaCap.add((BigDecimal) item[1]);
                ycCapThem = ycCapThem.add((BigDecimal) item[2]);
                i++;
            }
            btc.setKinhPhiDaCap(kpDaCap);
            btc.setTongTien(tongTien);
            btc.setYcCapThem(ycCapThem);
            btc.setListDetail(arrayDetail);
            btc.setIsSum(Boolean.TRUE);
            btc.setLoaiBn("TCDT");
            responses.add(0, btc);
        }
    }
}
