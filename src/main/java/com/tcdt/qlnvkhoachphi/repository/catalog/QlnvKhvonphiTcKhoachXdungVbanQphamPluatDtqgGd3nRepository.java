package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n;

public interface QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nRepository extends CrudRepository <QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n, Long>{
	String qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ct.ID" + 
			",ct.QLNV_KHVONPHI_ID" + 
			",ct.STT" + 
			",ct.TEN_VBAN" + 
			",ct.LOAI_VBAN as MA_HTHUC_VBAN" + 
			",ct.TGIAN_DU_KIEN as TGIAN_DKIEN" + 
			",ct.MA_DVI_CHU_TRI" + 
			",ct.DVI_PHOI_HOP as DVI_PHOP" + 
			",ct.DTOAN_KPHI" + 
			",ct.CCU_LAP_DTOAN FROM QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N ct " + 
			"			INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"			INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3n> findQlnvKhvonphiTcKhoachXdungVbanQphamPluatDtqgGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
