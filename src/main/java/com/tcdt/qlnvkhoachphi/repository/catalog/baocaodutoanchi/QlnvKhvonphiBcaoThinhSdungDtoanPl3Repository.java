package com.tcdt.qlnvkhoachphi.repository.catalog.baocaodutoanchi;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl3;

public interface QlnvKhvonphiBcaoThinhSdungDtoanPl3Repository extends CrudRepository <QlnvKhvonphiBcaoThinhSdungDtoanPl3, Long>{
	String qlnvKhvonphiBcaoThinhSdungDtoanPl3 = "SELECT * FROM QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3 t "
			+ "WHERE (:qlnvKhvonphiBcaoId is null or t.QLNV_KHVONPHI_BCAO_ID = :qlnvKhvonphiBcaoId) ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" +
			"    SELECT '' as QLNV_KHVONPHI_BCAO_ID" +
			"    ,'' as STT" +
			"    ,t.MA_DAN" +
			"    ,t.DDIEM_XDUNG" +
			"    ,t.QDDT_SO_QDINH" +
			"    ,Sum(t.QDDT_TMDT_TSO) as QDDT_TMDT_TSO" +
			"    ,Sum(t.QDDT_TMDT_NSNN) as QDDT_TMDT_NSNN" +

			"    ,Sum(t.LUY_KE_VON_TSO) as LUY_KE_VON_TSO" +
			"    ,Sum(t.LUY_KE_VON_NSNN) as LUY_KE_VON_NSNN" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_HET_NAM_TSO) as LUY_KE_GIAI_NGAN_HET_NAM_TSO" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_HET_NAM_NSNN_TSO) as LUY_KE_GIAI_NGAN_HET_NAM_NSNN_TSO" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_HET_NAM_NSNN_KH_NAM_TRUOC) as LUY_KE_GIAI_NGAN_HET_NAM_NSNN_KH_NAM_TRUOC" +
			"    ,Sum(t.KHOACH_VON_DTU) as KHOACH_VON_DTU" +
			"    ,Sum(t.KHOACH_TSO) as KHOACH_TSO" +
			"    ,Sum(t.KHOACH_NSNN) as KHOACH_NSNN" +

			"    ,Sum(t.KLUONG_THIEN) as KLUONG_THIEN" +
			"    ,Sum(t.GIAI_NGAN_TSO) as GIAI_NGAN_TSO" +
			"    ,Sum(t.GIAI_NGAN_TSO_TLE) as GIAI_NGAN_TSO_TLE" +
			"    ,Sum(t.GIAI_NGAN_NSNN) as GIAI_NGAN_NSNN" +
			"    ,Sum(t.GIAI_NGAN_NSNN_TLE) as GIAI_NGAN_NSNN_TLE" +

			"    ,Sum(t.LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_TSO) as LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_TSO" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_TSO_TLE) as LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_TSO_TLE" +
			"    ,Sum(t.LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_NSNN_TLE) as LUY_KE_GIAI_NGAN_DAU_NAM_NSNN_NSNN_TLE" +
			"    ,'' as NDUNG_CVIEC_HTHANH_CUOI_THANG" +
			"    ,'' as NDUNG_CVIEC_DANG_THIEN" +
			"    ,'' as KHOACH_THIEN_NDUNG_CVIEC_THANG_CON_LAI_NAM" +
			"    ,'' as GHI_CHU" +
			"			FROM (" +
			"                SELECT * FROM QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3 ct " +
			"                INNER JOIN QLNV_KHVONPHI_BCAO vp ON vp.id = ct.QLNV_KHVONPHI_BCAO_ID " +
			"                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha "+
			"                AND:namBcao= vp.NAM_BCAO AND:maLoaiBcao= vp.MA_LOAI_BCAO AND :thangBcao = vp.THANG_BCAO ) t " +
			"			GROUP BY t.MA_DAN,t.DDIEM_XDUNG) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl3> synthesis(String maDviCha,Long namBcao,Long thangBcao,String maLoaiBcao);

	@Query(value = qlnvKhvonphiBcaoThinhSdungDtoanPl3, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl3> findQlnvKhvonphiBcaoThinhSdungDtoanPl3ByQlnvKhvonphiBcaoId(Long qlnvKhvonphiBcaoId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3 u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);
}
