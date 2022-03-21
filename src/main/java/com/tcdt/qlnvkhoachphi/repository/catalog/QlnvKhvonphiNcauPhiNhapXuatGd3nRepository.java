package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiNcauPhiNhapXuatGd3n;

public interface QlnvKhvonphiNcauPhiNhapXuatGd3nRepository extends CrudRepository <QlnvKhvonphiNcauPhiNhapXuatGd3n, Long>{
	String qlnvKhvonphiNcauPhiNhapXuatGd3n = "SELECT * FROM QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" + 
			"			    SELECT '' as QLNV_KHVONPHI_ID" + 
			"			    ,'' as STT" + 
			"			    ,t.MA_VTU,t.MA_NHOM_CHI,t.MA_LOAI_CHI,t.MA_DVI_TINH" + 
			"			    ,Sum(t.SL) as SL" + 
			"			    ,Sum(t.DMUC_PHI_TC) as DMUC_PHI_TC" + 
			"			    ,Sum(t.THANH_TIEN) as THANH_TIEN" + 
			"						FROM (" + 
			"			                SELECT * FROM QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N ct " + 
			"			                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"			                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t " + 
			"						GROUP BY t.MA_VTU,t.MA_NHOM_CHI,t.MA_LOAI_CHI,t.MA_DVI_TINH) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiNcauPhiNhapXuatGd3n> synthesis(String maDviCha, String namHienHanh);
	
	@Query(value = qlnvKhvonphiNcauPhiNhapXuatGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiNcauPhiNhapXuatGd3n> findQlnvKhvonphiNcauPhiNhapXuatGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}

