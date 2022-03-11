package com.tcdt.qlnvkhoachphi.repository.catalog.baocaodutoanchi;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl2;

public interface QlnvKhvonphiBcaoThinhSdungDtoanPl2Repository extends CrudRepository <QlnvKhvonphiBcaoThinhSdungDtoanPl2, Long> {
	String qlnvKhvonphiBcaoThinhSdungDtoanPl2 = "SELECT * FROM QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2 t "
			+ "WHERE (:qlnvKhvonphiBcaoId is null or t.QLNV_KHVONPHI_BCAO_ID = :qlnvKhvonphiBcaoId) ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" +
			"    SELECT '' as QLNV_KHVONPHI_BCAO_ID" +
			"    ,'' as STT" +
			"    ,t.MA_NDUNG" +
			"    ,Sum(t.DTOAN_SDUNG_NAM_TCONG) as DTOAN_SDUNG_NAM_TCONG" +
			"    ,Sum(t.DTOAN_SDUNG_NAM_NGUON_NSNN) as DTOAN_SDUNG_NAM_NGUON_NSNN" +
			"    ,Sum(t.DTOAN_SDUNG_NAM_NGUON_SN) as DTOAN_SDUNG_NAM_NGUON_SN" +
			"    ,Sum(t.DTOAN_SDUNG_NAM_NGUON_QUY) as DTOAN_SDUNG_NAM_NGUON_QUY" +

			"    ,Sum(t.GIAI_NGAN_THANG_TCONG) as GIAI_NGAN_THANG_TCONG" +
			"    ,Sum(t.GIAI_NGAN_THANG_TCONG_TLE) as GIAI_NGAN_THANG_TCONG_TLE" +
			"    ,Sum(t.GIAI_NGAN_THANG_NGUON_NSNN) as GIAI_NGAN_THANG_NGUON_NSNN" +
			"    ,Sum(t.GIAI_NGAN_THANG_NGUON_NSNN_TLE) as GIAI_NGAN_THANG_NGUON_NSNN_TLE" +
			"    ,Sum(t.GIAI_NGAN_THANG_NGUON_SN) as GIAI_NGAN_THANG_NGUON_SN" +
			"    ,Sum(t.GIAI_NGAN_THANG_NGUON_SN_TLE) as GIAI_NGAN_THANG_NGUON_SN_TLE" +
			"    ,Sum(t.GIAI_NGAN_THANG_NGUON_QUY) as GIAI_NGAN_THANG_NGUON_QUY" +
			"    ,Sum(t.GIAI_NGAN_THANG_NGUON_QUY_TLE) as GIAI_NGAN_THANG_NGUON_QUY_TLE" +

			"    ,Sum(t.LUY_KE_GIAI_NGAN_TCONG) as LUY_KE_GIAI_NGAN_TCONG" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_TCONG_TLE) as LUY_KE_GIAI_NGAN_TCONG_TLE" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_NSNN) as LUY_KE_GIAI_NGAN_NGUON_NSNN" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_NSNN_TLE) as LUY_KE_GIAI_NGAN_NGUON_NSNN_TLE" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_SN) as LUY_KE_GIAI_NGAN_NGUON_SN" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_SN_TLE) as LUY_KE_GIAI_NGAN_NGUON_SN_TLE" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_QUY) as LUY_KE_GIAI_NGAN_NGUON_QUY" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_NGUON_QUY_TLE) as LUY_KE_GIAI_NGAN_NGUON_QUY_TLE" +
			"			FROM (" +
			"                SELECT * FROM QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2 ct " +
			"                INNER JOIN QLNV_KHVONPHI_BCAO vp ON vp.id = ct.QLNV_KHVONPHI_BCAO_ID " +
			"                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha "+
			"                AND (:namBcao is null or lower(vp.NAM_BCAO) = lower(concat(concat('%', :namBcao),'%'))) AND (:thangBcao is null or lower(vp.THANG_BCAO) = lower(concat(concat('%', :thangBcao),'%')))     ) t " +
			"			GROUP BY t.MA_NDUNG) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl2> synthesis(String maDviCha,Long namBcao,Long thangBcao);

	@Query(value = qlnvKhvonphiBcaoThinhSdungDtoanPl2, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl2> findQlnvKhvonphiBcaoThinhSdungDtoanPl2ByQlnvKhvonphiBcaoId(Long qlnvKhvonphiBcaoId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2 u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);
}
