package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachDtaoBoiDuongGd3n;

public interface QlnvKhvonphiKhoachDtaoBoiDuongGd3nRepository extends CrudRepository < QlnvKhvonphiKhoachDtaoBoiDuongGd3n, Long>{
	String qlnvKhvonphiKhoachDtaoBoiDuongGd3n = "SELECT * FROM QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";
	
	String tongHop = "SELECT ROWNUM as ID,a.* FROM (\r\n" + 
			"			    SELECT '' as QLNV_KHVONPHI_ID\r\n" + 
			"			    ,'' as STT\r\n" + 
			"			    ,t.MA_LOAI,t.MA_KHOAN,t.MA_CHI_MUC\r\n" + 
			"			    ,Sum(t.SO_LUOT_NGUOI_N1) as SO_LUOT_NGUOI_N1\r\n" + 
			"			    ,Sum(t.THANH_TIEN_N1) as THANH_TIEN_N1\r\n" + 
			"			    ,Sum(t.SO_LUOT_NGUOI_N2) as SO_LUOT_NGUOI_N2\r\n" + 
			"			    ,Sum(t.THANH_TIEN_N2) as THANH_TIEN_N2\r\n" + 
			"			    ,Sum(t.SO_LUOT_NGUOI_N3) as SO_LUOT_NGUOI_N3\r\n" + 
			"			    ,Sum(t.THANH_TIEN_N3) as THANH_TIEN_N3\r\n" + 
			"						FROM (\r\n" + 
			"			                SELECT * FROM QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N ct \r\n" + 
			"			                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id \r\n" + 
			"			                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t \r\n" + 
			"						GROUP BY t.MA_LOAI,t.MA_KHOAN,t.MA_CHI_MUC) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachDtaoBoiDuongGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiKhoachDtaoBoiDuongGd3n, nativeQuery = true)
	ArrayList< QlnvKhvonphiKhoachDtaoBoiDuongGd3n> findQlnvKhvonphiKhoachDtaoBoiDuongGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
