package com.tcdt.qlnvkhoachphi.repository.catalog.baocaodutoanchi;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl1;

public interface QlnvKhvonphiBcaoThinhSdungDtoanPl1Repository extends CrudRepository <QlnvKhvonphiBcaoThinhSdungDtoanPl1, Long>{
	String qlnvKhvonphiBcaoThinhSdungDtoanPl1 = "SELECT * FROM QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1 t "
			+ "WHERE (:qlnvKhvonphiBcaoId is null or t.QLNV_KHVONPHI_BCAO_ID = :qlnvKhvonphiBcaoId) ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" +
			"    SELECT '' as QLNV_KHVONPHI_BCAO_ID" +
			"    ,'' as STT" +
			"    ,t.MA_NDUNG" +
			"    ,Sum(t.KPHI_SDUNG_TCONG) as KPHI_SDUNG_TCONG" +
			"    ,Sum(t.KPHI_SDUNG_DTOAN) as KPHI_SDUNG_DTOAN" +
			"    ,Sum(t.KPHI_SDUNG_NGUON_KHAC) as KPHI_SDUNG_NGUON_KHAC" +
			"    ,Sum(t.KPHI_SDUNG_NGUON_QUY) as KPHI_SDUNG_NGUON_QUY" +
			"    ,Sum(t.KPHI_SDUNG_NSTT) as KPHI_SDUNG_NSTT" +
			"    ,Sum(t.KPHI_SDUNG_CK) as KPHI_SDUNG_CK" +

			"    ,Sum(t.KPHI_CHUYEN_SANG_TCONG) as KPHI_CHUYEN_SANG_TCONG" +
			"    ,Sum(t.KPHI_CHUYEN_SANG_DTOAN) as KPHI_CHUYEN_SANG_DTOAN" +
			"    ,Sum(t.KPHI_CHUYEN_SANG_NGUON_KHAC) as KPHI_CHUYEN_SANG_NGUON_KHAC" +
			"    ,Sum(t.KPHI_CHUYEN_SANG_NGUON_QUY) as KPHI_CHUYEN_SANG_NGUON_QUY" +
			"    ,Sum(t.KPHI_CHUYEN_SANG_NSTT) as KPHI_CHUYEN_SANG_NSTT" +
			"    ,Sum(t.KPHI_CHUYEN_SANG_CK) as KPHI_CHUYEN_SANG_CK" +

			"    ,Sum(t.DTOAN_GIAO_TCONG) as DTOAN_GIAO_TCONG" +
			"    ,Sum(t.DTOAN_GIAO_DTOAN) as DTOAN_GIAO_DTOAN" +
			"    ,Sum(t.DTOAN_GIAO_NGUON_KHAC) as DTOAN_GIAO_NGUON_KHAC" +
			"    ,Sum(t.DTOAN_GIAO_NGUON_QUY) as DTOAN_GIAO_NGUON_QUY" +
			"    ,Sum(t.DTOAN_GIAO_NSTT) as DTOAN_GIAO_NSTT" +
			"    ,Sum(t.DTOAN_GIAO_CK) as DTOAN_GIAO_CK" +

			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_TCONG) as GIAI_NGAN_THANG_BCAO_TCONG" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_TCONG_TLE) as GIAI_NGAN_THANG_BCAO_TCONG_TLE" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_DTOAN) as GIAI_NGAN_THANG_BCAO_DTOAN" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_DTOAN_TLE) as GIAI_NGAN_THANG_BCAO_DTOAN_TLE" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_NGUON_KHAC) as GIAI_NGAN_THANG_BCAO_NGUON_KHAC" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_NGUON_KHAC_TLE) as GIAI_NGAN_THANG_BCAO_NGUON_KHAC_TLE" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_NGUON_QUY) as GIAI_NGAN_THANG_BCAO_NGUON_QUY" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_NGUON_QUY_TLE) as GIAI_NGAN_THANG_BCAO_NGUON_QUY_TLE" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_NSTT) as GIAI_NGAN_THANG_BCAO_NSTT" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_NSTT_TLE) as GIAI_NGAN_THANG_BCAO_NSTT_TLE" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_CK) as GIAI_NGAN_THANG_BCAO_CK" +
			"    ,Sum(t.GIAI_NGAN_THANG_BCAO_CK_TLE) as GIAI_NGAN_THANG_BCAO_CK_TLE" +

			"    ,Sum(t.LUY_KE_GIAI_NGAN_TCONG) as LUY_KE_GIAI_NGAN_TCONG" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_TCONG_TLE) as LUY_KE_GIAI_NGAN_TCONG_TLE" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_DTOAN) as LUY_KE_GIAI_NGAN_DTOAN" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_DTOAN_TLE) as LUY_KE_GIAI_NGAN_DTOAN_TLE" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_KHAC) as LUY_KE_GIAI_NGAN_NGUON_KHAC" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_KHAC_TLE) as LUY_KE_GIAI_NGAN_NGUON_KHAC_TLE" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_QUY) as LUY_KE_GIAI_NGAN_NGUON_QUY" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_QUY_TLE) as LUY_KE_GIAI_NGAN_NGUON_QUY_TLE" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NSTT) as LUY_KE_GIAI_NGAN_NSTT" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NSTT_TLE) as LUY_KE_GIAI_NGAN_NSTT_TLE" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_CK) as LUY_KE_GIAI_NGAN_CK" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_CK_TLE) as LUY_KE_GIAI_NGAN_CK_TLE" +
			"			FROM (" +
			"                SELECT * FROM QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1 ct " +
			"                INNER JOIN QLNV_KHVONPHI_BCAO vp ON vp.id = ct.QLNV_KHVONPHI_BCAO_ID " +
			"                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha "+
			"                AND (:namBcao is null or lower(vp.NAM_BCAO) = lower(concat(concat('%', :namBcao),'%'))) AND (:thangBcao is null or lower(vp.THANG_BCAO) = lower(concat(concat('%', :thangBcao),'%')))     ) t " +
			"			GROUP BY t.MA_NDUNG) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl1> synthesis(String maDviCha,Long namBcao,Long thangBcao);

	@Query(value = qlnvKhvonphiBcaoThinhSdungDtoanPl1, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl1> findQlnvKhvonphiBcaoThinhSdungDtoanPl1ByQlnvKhvonphiBcaoId(Long qlnvKhvonphiBcaoId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1 u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);
}
