package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcKhoachBquanTbiVtuHnam;

public interface QlnvKhvonphiTcKhoachBquanTbiVtuHnamRepository extends CrudRepository <QlnvKhvonphiTcKhoachBquanTbiVtuHnam, Long>{
	String qlnvKhvonphiTcKhoachBquanTbiVtuHnam = "SELECT * FROM QLNV_KHVONPHI_TC_KHOACH_BQUAN_TBI_VTU_HNAM t "
			+ "WHERE t.BQUAN_HNAM_ID = :bquanHnamId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM ( " + 
			"    SELECT '' as BQUAN_HNAM_ID " + 
			"    ,'' as STT " + 
			"    ,t.MA_MAT_HANG as MA_VTU_TBI" + 
			"    ,t.MA_NHOM as LOAI_DINH_MUC" + 
			"    ,Sum(t.KPHI) as KPHI " + 
			"        FROM ( " + 
			"            SELECT * FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG ct  " + 
			"            INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id  " + 
			"            INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi  " + 
			"            WHERE dv.ma_dvi_cha=:maDviCha  " + 
			"            AND vp.nam_hien_hanh=:namHienHanh " + 
			"            AND vp.ma_dvi=:maDvi) t  " + 
			"        GROUP BY t.MA_MAT_HANG,t.MA_NHOM) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachBquanTbiVtuHnam> synthesis(String maDviCha, String namHienHanh, String maDvi);

	@Query(value = qlnvKhvonphiTcKhoachBquanTbiVtuHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachBquanTbiVtuHnam> findQlnvKhvonphiTcKhoachBquanTbiVtuHnamByBquanHnamId(Long bquanHnamId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_KHOACH_BQUAN_TBI_VTU_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}