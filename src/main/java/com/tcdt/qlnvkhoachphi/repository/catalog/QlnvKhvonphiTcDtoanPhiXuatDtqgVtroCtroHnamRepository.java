package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam;

public interface QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamRepository extends CrudRepository <QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam, Long>{
	String qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam = "SELECT * FROM QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = " SELECT ROWNUM as ID,a.* FROM (" + 
			"    SELECT '' as QLNV_KHVONPHI_ID" + 
			"    ,'' as STT" + 
			"    ,t.MA_DVI as MA_CUC_DTNN_KVUC" + 
			"    ,Sum(t.LUONG_XUAT_VTRO) as LUONG" + 
			"    ,'' as CPHI_XUAT_CO_DMUC" + 
			"    ,'' as CPHI_XUAT_CHUA_DMUC" + 
			"    ,'' as THANH_TIEN_CO_DMUC" + 
			"    ,'' as THANH_TIEN_KHONG_DMUC" + 
			"    ,'' as THANH_TIEN_CONG" + 
			"			FROM (" + 
			"                SELECT ct.LUONG_XUAT_VTRO,vp.MA_DVI FROM QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM ct " + 
			"                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi " + 
			"                WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t" + 
			"                GROUP BY t.MA_DVI) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnam> findQlnvKhvonphiTcDtoanPhiXuatDtqgVtroCtroHnamByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
