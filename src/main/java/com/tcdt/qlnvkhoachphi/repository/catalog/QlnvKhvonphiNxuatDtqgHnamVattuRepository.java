package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiNxuatDtqgHnamVattu;

public interface QlnvKhvonphiNxuatDtqgHnamVattuRepository extends CrudRepository <QlnvKhvonphiNxuatDtqgHnamVattu, Long>{
	String qlnvKhvonphiNxuatDtqgHnamVattu = "SELECT * FROM QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";
	
	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" + 
			"			    SELECT '' as QLNV_KHVONPHI_ID" + 
			"			    ,'' as STT" + 
			"			    ,t.MA_TBI" + 
			"			    ,Sum(t.SL_NHAP) as SL_NHAP" + 
			"						FROM (" + 
			"			                SELECT * FROM QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU ct " + 
			"			                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"			                INNER JOIN dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t " +
			"						GROUP BY t.MA_TBI) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiNxuatDtqgHnamVattu> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiNxuatDtqgHnamVattu, nativeQuery = true)
	ArrayList<QlnvKhvonphiNxuatDtqgHnamVattu> findQlnvKhvonphiNxuatDtqgHnamVattuByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}

