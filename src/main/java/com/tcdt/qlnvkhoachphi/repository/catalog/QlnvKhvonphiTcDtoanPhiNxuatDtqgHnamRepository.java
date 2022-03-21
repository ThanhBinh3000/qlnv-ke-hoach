package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam;



public interface QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository extends CrudRepository <QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam, Long>{
	String qlnvKhvonphiTcDtoanPhiNxuatDtqgHnam = "SELECT * FROM QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_HNAM t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" + 
			"    SELECT '' as QLNV_KHVONPHI_NXUAT_DTQG_HNAM_THOC_GAO" + 
			"    ,'' as STT" + 
			"    ,t.MA_DVI as MA_CUC_DTNN_KVUC" + 
			"    ,Sum(t.LUONG_THOC_XUAT) as NXUAT_THOC_LUONG_XUAT" + 
			"    ,Sum(t.LUONG_THOC_NHAP) as NXUAT_THOC_LUONG_NHAP" + 
			"    ,Sum(t.LUONG_GAO_XUAT) as NXUAT_GAO_LUONG_XUAT" + 
			"    ,Sum(t.LUONG_GAO_NHAP) as NXUAT_GAO_LUONG_NHAP" + 
			"    ,'' as NXUAT_THOC_LUONG_DMUC_PHI_XUAT" + 
			"    ,'' as NXUAT_THOC_LUONG_DMUC_PHI_NHAP" + 
			"    ,'' as NXUAT_THOC_LUONG_TTIEN" + 
			"    ,'' as NXUAT_GAO_LUONG_DMUC_PHI_XUAT" + 
			"    ,'' as NXUAT_GAO_LUONG_DMUC_PHI_NHAP" + 
			"    ,'' as NXUAT_GAO_LUONG_TTIEN" + 
			"            FROM (" + 
			"                SELECT vp.MA_DVI,ct.LUONG_THOC_XUAT,ct.LUONG_THOC_NHAP,ct.LUONG_GAO_XUAT,ct.LUONG_GAO_NHAP FROM QLNV_KHVONPHI_NXUAT_DTQG_HNAM_THOC_GAO ct " + 
			"                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t " + 
			"            GROUP BY t.MA_DVI) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcDtoanPhiNxuatDtqgHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam> findQlnvKhvonphiTcDtoanPhiNxuatDtqgHnamByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}