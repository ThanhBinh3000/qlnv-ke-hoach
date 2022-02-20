package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcThopNncauChiTxGd3n;



public interface QlnvKhvonphiTcThopNncauChiTxGd3nRepository extends CrudRepository <QlnvKhvonphiTcThopNncauChiTxGd3n, Long>{
	String qlnvKhvonphiTcThopNncauChiTxGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (\r\n" + 
			"    SELECT '' as QLNV_KHVONPHI_ID\r\n" + 
			"    ,'' as STT\r\n" + 
			"    ,t.MA_NOI_DUNG as MA_NDUNG\r\n" + 
			"    ,t.MA_NHOM_CHI\r\n" + 
			"    , Sum(t.NAM_HHANH_N) as NAM_HHANH_N\r\n" + 
			"    , Sum(t.TRAN_CHI_DUOC_TB_N1) as TRAN_CHI_N1\r\n" + 
			"    , Sum(t.NCAU_CHI_CUA_DVI_N1) as NCAU_CHI_N1\r\n" + 
			"    , Sum(t.CLECH_TRAN_CHI_VS_NCAU_N1) as CLECH_TRAN_CHI_VS_NCAU_CHI_N1\r\n" + 
			"    , Sum(t.TRAN_CHI_DUOC_TB_N2) as TRAN_CHI_N2\r\n" + 
			"    , Sum(t.NCAU_CHI_CUA_DVI_N2) as NCAU_CHI_N2\r\n" + 
			"    , Sum(t.CLECH_TRAN_CHI_VS_NCAU_N2) as CLECH_TRAN_CHI_VS_NCAU_CHI_N2\r\n" + 
			"    , Sum(t.TRAN_CHI_DUOC_TB_N3) as TRAN_CHI_N3\r\n" + 
			"    , Sum(t.NCAU_CHI_CUA_DVI_N3) as NCAU_CHI_N3\r\n" + 
			"    , Sum(t.CLECH_TRAN_CHI_VS_NCAU_N3) as CLECH_TRAN_CHI_VS_NCAU_CHI_N3 \r\n" + 
			"     FROM ( SELECT * FROM QLNV_KHVONPHI_CHI_TX_GD3N ct \r\n" + 
			"     INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id \r\n" + 
			"     INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi  \r\n" + 
			"     WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t \r\n" + 
			"     GROUP BY t.MA_NOI_DUNG,t.MA_NHOM_CHI) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcThopNncauChiTxGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcThopNncauChiTxGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcThopNncauChiTxGd3n> findQlnvKhvonphiTcThopNncauChiTxGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
