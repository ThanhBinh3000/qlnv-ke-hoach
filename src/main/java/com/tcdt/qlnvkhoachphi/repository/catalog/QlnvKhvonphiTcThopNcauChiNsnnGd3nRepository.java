package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcThopNcauChiNsnnGd3n;

public interface QlnvKhvonphiTcThopNcauChiNsnnGd3nRepository extends CrudRepository <QlnvKhvonphiTcThopNcauChiNsnnGd3n, Long>{
	String qlnvKhvonphiTcThopNcauChiNsnnGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" + 
			"    SELECT '' as QLNV_KHVONPHI_ID" + 
			"    ,'' as STT" + 
			"    ,t.MA_NDUNG" + 
			"    ,t.MA_LOAI_CHI as MA_NHOM_CHI_NSNN" + 
			"    ,Sum(t.DTOAN_N) as DTOAN_N" + 
			"    ,Sum(t.UOC_THIEN_N) as UOC_THIEN_N" + 
			"    ,Sum(t.TRAN_CHI_N1) as TRAN_CHI_N1" + 
			"    ,Sum(t.NCAU_CHI_N1) as NCAU_CHI_N1" + 
			"    ,Sum(t.CLECH_TRAN_CHI_VS_NCAU_CHI_N1) as CLECH_TRAN_CHI_VS_NCAU_CHI_N1" + 
			"    ,Sum(t.SSANH_NCAU_N_VOI_N_1) as SSANH_NCAU_N_VOI_N_1" + 
			"    ,Sum(t.TRAN_CHI_N2) as TRAN_CHI_N2" + 
			"    ,Sum(t.NCAU_CHI_N2) as NCAU_CHI_N2" + 
			"    ,Sum(t.CLECH_TRAN_CHI_VS_NCAU_CHI_N2) as CLECH_TRAN_CHI_VS_NCAU_CHI_N2" + 
			"    ,Sum(t.TRAN_CHI_N3) as TRAN_CHI_N3" + 
			"    ,Sum(t.NCAU_CHI_N3) as NCAU_CHI_N3" + 
			"    ,Sum(t.CLECH_TRAN_CHI_VS_NCAU_CHI_N3) as CLECH_TRAN_CHI_VS_NCAU_CHI_N3" + 
			"    FROM (" + 
			"        SELECT * FROM QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N ct " + 
			"        INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"        INNER JOIN dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t " +
			"    GROUP BY t.MA_NDUNG,t.MA_LOAI_CHI) a";
	
	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcThopNcauChiNsnnGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcThopNcauChiNsnnGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcThopNcauChiNsnnGd3n> findQlnvKhvonphiTcThopNcauChiNsnnGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
