package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiKhoachQuyTienLuongGd3n;

public interface QlnvKhvonphiKhoachQuyTienLuongGd3nRepository extends CrudRepository <QlnvKhvonphiKhoachQuyTienLuongGd3n, Long>{
	String qlnvKhvonphiKhoachQuyTienLuongGd3n = "SELECT * FROM QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";
	
	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" + 
			"    SELECT '' as QLNV_KHVONPHI_ID" + 
			"    ,'' as STT" + 
			"    ,t.MA_DVI" + 
			"    ,Sum(t.TONG_CBO_N) as TONG_CBO_N" + 
			"    ,Sum(t.TONG_BCHE_DUOC_PD_N) as TONG_BCHE_DUOC_PD_N" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_N) as TONG_QUY_LUONG_CO_TCHAT_LUONG_N" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N) as TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N" + 
			"    ,Sum(t.LUONG_CBAN_N) as LUONG_CBAN_N" + 
			"    ,Sum(t.PHU_CAP_N) as PHU_CAP_N" + 
			"    ,Sum(t.CAC_KHOAN_DGOP_N) as CAC_KHOAN_DGOP_N" + 
			"    ,Sum(t.TONG_CBO_THIEN_N) as TONG_CBO_THIEN_N" + 
			"    ,Sum(t.TONG_BCHE_DUOC_PD_THIEN_N) as TONG_BCHE_DUOC_PD_THIEN_N" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_THIEN_N) as TONG_QUY_LUONG_CO_TCHAT_LUONG_THIEN_N" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_THIEN_N) as TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_THIEN_N" + 
			"    ,Sum(t.LUONG_CBAN_THIEN_N) as LUONG_CBAN_THIEN_N" + 
			"    ,Sum(t.PHU_CAP_THIEN_N) as PHU_CAP_THIEN_N" + 
			"    ,Sum(t.CAC_KHOAN_DGOP_THIEN_N) as CAC_KHOAN_DGOP_THIEN_N" + 
			"    ,Sum(t.TONG_CBO_N1) as TONG_CBO_N1" + 
			"    ,Sum(t.TONG_BCHE_DUOC_PD_N1) as TONG_BCHE_DUOC_PD_N1" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_N1) as TONG_QUY_LUONG_CO_TCHAT_LUONG_N1" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N1) as TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N1" + 
			"    ,Sum(t.LUONG_CBAN_N1) as LUONG_CBAN_N1" + 
			"    ,Sum(t.PHU_CAP_N1) as PHU_CAP_N1" + 
			"    ,Sum(t.CAC_KHOAN_DGOP_N1) as CAC_KHOAN_DGOP_N1" + 
			"    ,Sum(t.TONG_CBO_N2) as TONG_CBO_N2" + 
			"    ,Sum(t.TONG_BCHE_DUOC_PD_N2) as TONG_BCHE_DUOC_PD_N2" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_N2) as TONG_QUY_LUONG_CO_TCHAT_LUONG_N2" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N2) as TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N2" + 
			"    ,Sum(t.LUONG_CBAN_N2) as LUONG_CBAN_N2" + 
			"    ,Sum(t.PHU_CAP_N2) as PHU_CAP_N2" + 
			"    ,Sum(t.CAC_KHOAN_DGOP_N2) as CAC_KHOAN_DGOP_N2" + 
			"    ,Sum(t.TONG_CBO_N3) as TONG_CBO_N3" + 
			"    ,Sum(t.TONG_BCHE_DUOC_PD_N3) as TONG_BCHE_DUOC_PD_N3" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_N3) as TONG_QUY_LUONG_CO_TCHAT_LUONG_N3" + 
			"    ,Sum(t.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N3) as TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N3" + 
			"    ,Sum(t.LUONG_CBAN_N3) as LUONG_CBAN_N3" + 
			"    ,Sum(t.PHU_CAP_N3) as PHU_CAP_N3" + 
			"    ,Sum(t.CAC_KHOAN_DGOP_N3) as CAC_KHOAN_DGOP_N3" + 
			"			FROM (" + 
			"                SELECT ct.MA_DVI,ct.TONG_CBO_N,ct.TONG_BCHE_DUOC_PD_N,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_N,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N,ct.LUONG_CBAN_N,ct.PHU_CAP_N,ct.CAC_KHOAN_DGOP_N,ct.TONG_CBO_THIEN_N,ct.TONG_BCHE_DUOC_PD_THIEN_N,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_THIEN_N,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_THIEN_N,ct.LUONG_CBAN_THIEN_N,ct.PHU_CAP_THIEN_N,ct.CAC_KHOAN_DGOP_THIEN_N,ct.TONG_CBO_N1,ct.TONG_BCHE_DUOC_PD_N1,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_N1,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N1,ct.LUONG_CBAN_N1,ct.PHU_CAP_N1,ct.CAC_KHOAN_DGOP_N1,ct.TONG_CBO_N2,ct.TONG_BCHE_DUOC_PD_N2,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_N2,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N2,ct.LUONG_CBAN_N2,ct.PHU_CAP_N2,ct.CAC_KHOAN_DGOP_N2,ct.TONG_CBO_N3,ct.TONG_BCHE_DUOC_PD_N3,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_N3,ct.TONG_QUY_LUONG_CO_TCHAT_LUONG_THEO_BCHE_N3,ct.LUONG_CBAN_N3,ct.PHU_CAP_N3,ct.CAC_KHOAN_DGOP_N3 FROM QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N ct " + 
			"                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"                INNER JOIN dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t " +
			"			GROUP BY t.MA_DVI) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachQuyTienLuongGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiKhoachQuyTienLuongGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachQuyTienLuongGd3n> findQlnvKhvonphiKhoachQuyTienLuongGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}

