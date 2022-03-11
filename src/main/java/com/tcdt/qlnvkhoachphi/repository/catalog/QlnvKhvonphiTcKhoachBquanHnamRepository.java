package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachBquanHnam;

public interface QlnvKhvonphiTcKhoachBquanHnamRepository extends CrudRepository <QlnvKhvonphiTcKhoachBquanHnam, Long>{
	String qlnvKhvonphiTcKhoachBquanHnam = "SELECT * FROM QLNV_KHVONPHI_TC_KHOACH_BQUAN_HNAM t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" + 
			"    SELECT '' as QLNV_KHVONPHI_ID" + 
			"    ,'' as STT" + 
			"    ,t.MA_DVI as MA_CUC_DTNN_KVUC" + 
			"    ,Sum(t.KPHI_BQUAN_THOC_TX) as KPHI_BQUAN_CO_DMUC_THOC_TX" + 
			"    ,Sum(t.KPHI_BQUAN_THOC_LD) as KPHI_BQUAN_CO_DMUC_THOC_LD" + 
			"    ,Sum(t.KPHI_BQUAN_GAO_TX) as KPHI_BQUAN_CO_DMUC_GAO_TX" + 
			"    ,Sum(t.KPHI_BQUAN_GAO_LD) as KPHI_BQUAN_CO_DMUC_GAO_LD" + 
			"    ,'' as KPHI_BQUAN_CO_DMUC_TONG" + 
			"    ,'' as KPHI_BQUAN_CHUA_DMUC_TONG" + 
			"    ,'' as TONG_CONG" + 
			"        FROM (" + 
			"            SELECT vp.MA_DVI,ct.KPHI_BQUAN_THOC_TX,ct.KPHI_BQUAN_THOC_LD,ct.KPHI_BQUAN_GAO_TX,ct.KPHI_BQUAN_GAO_LD FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_THOC_GAO ct " + 
			"            INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"            INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi " + 
			"            WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t " + 
			"        GROUP BY t.MA_DVI) a ";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachBquanHnam> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcKhoachBquanHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachBquanHnam> findQlnvKhvonphiTcKhoachBquanHnamByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_KHOACH_BQUAN_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
