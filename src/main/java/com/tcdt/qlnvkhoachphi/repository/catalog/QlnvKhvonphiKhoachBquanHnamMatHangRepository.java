package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiKhoachBquanHnamMatHang;

public interface QlnvKhvonphiKhoachBquanHnamMatHangRepository extends CrudRepository <QlnvKhvonphiKhoachBquanHnamMatHang, Long>{
	String qlnvKhvonphiKhoachBquanHnamMatHang = "SELECT * FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM ( " + 
			"						    SELECT '' as QLNV_KHVONPHI_ID " + 
			"						    ,'' as STT " + 
			"						    ,t.MA_MAT_HANG,t.MA_NHOM " + 
			"						    ,Sum(t.KPHI) as KPHI " + 
			"									FROM ( " + 
			"						                SELECT * FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG ct  " + 
			"						                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id  " + 
			"						                INNER JOIN dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t  " +
			"									GROUP BY t.MA_MAT_HANG,t.MA_NHOM) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachBquanHnamMatHang> synthesis(String maDviCha, String namHienHanh);
	
	@Query(value = qlnvKhvonphiKhoachBquanHnamMatHang, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachBquanHnamMatHang> findQlnvKhvonphiKhoachBquanHnamMatHangByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
