package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiNxuatDtqgHnamThocGao;

public interface QlnvKhvonphiNxuatDtqgHnamThocGaoRepository extends CrudRepository <QlnvKhvonphiNxuatDtqgHnamThocGao, Long>{
	String qlnvKhvonphiNxuatDtqgHnamThocGao = "SELECT * FROM QLNV_KHVONPHI_NXUAT_DTQG_HNAM_THOC_GAO t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (\r\n" + 
			"			    SELECT '' as QLNV_KHVONPHI_ID\r\n" + 
			"			    ,'' as STT\r\n" + 
			"			    ,Sum(t.LUONG_THOC_XUAT) as LUONG_THOC_XUAT\r\n" + 
			"                ,Sum(t.LUONG_THOC_NHAP) as LUONG_THOC_NHAP\r\n" + 
			"			    ,Sum(t.LUONG_GAO_XUAT) as LUONG_GAO_XUAT\r\n" + 
			"			    ,Sum(t.LUONG_GAO_NHAP) as LUONG_GAO_NHAP\r\n" + 
			"						FROM (\r\n" + 
			"			                SELECT * FROM QLNV_KHVONPHI_NXUAT_DTQG_HNAM_THOC_GAO ct \r\n" + 
			"			                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id \r\n" + 
			"			                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t ) a";

	@Query(value = tongHop, nativeQuery = true)
	QlnvKhvonphiNxuatDtqgHnamThocGao synthesis(String maDviCha, String namHienHanh);
	
	@Query(value = qlnvKhvonphiNxuatDtqgHnamThocGao, nativeQuery = true)
	QlnvKhvonphiNxuatDtqgHnamThocGao findQlnvKhvonphiNxuatDtqgHnamThocGaoByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_NXUAT_DTQG_HNAM_THOC_GAO u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}

