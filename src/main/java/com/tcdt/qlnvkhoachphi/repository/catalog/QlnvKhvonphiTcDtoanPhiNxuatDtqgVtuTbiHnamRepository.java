package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam;


public interface QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository extends CrudRepository <QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam, Long>{
	String qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam = "SELECT * FROM QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_VTU_TBI_HNAM t "
			+ "WHERE t.NXUAT_DTQG_HNAM_ID = :nxuatDtqgHnamId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" + 
			"    SELECT '' as NXUAT_DTQG_HNAM_ID" + 
			"    ,'' as STT" + 
			"    ,t.MA_TBI as MA_VTU_TBI" + 
			"    ,Sum(t.SL_NHAP) as SL_NHAP" + 
			"            FROM (" + 
			"                SELECT * FROM QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU ct " + 
			"                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi " + 
			"                WHERE dv.ma_dvi_cha=:maDviCha " + 
			"                AND vp.nam_hien_hanh=:namHienHanh" + 
			"                AND vp.ma_dvi=:maDvi) t " + 
			"            GROUP BY t.MA_TBI) a ";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam> synthesis(String maDviCha, String namHienHanh, String maDvi);

	@Query(value = qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam> findQlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamByNxuatDtqgHnamId(Long nxuatDtqgHnamId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_VTU_TBI_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
