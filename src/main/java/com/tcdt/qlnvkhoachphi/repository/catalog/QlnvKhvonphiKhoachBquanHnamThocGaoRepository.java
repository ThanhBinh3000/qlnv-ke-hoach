package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachBquanHnamThocGao;

public interface QlnvKhvonphiKhoachBquanHnamThocGaoRepository extends CrudRepository <QlnvKhvonphiKhoachBquanHnamThocGao, Long>{
	String qlnvKhvonphiKhoachBquanHnamThocGao = "SELECT * FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_THOC_GAO t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";
	
	String tongHop = "SELECT ROWNUM as ID,a.* FROM ( \r\n" + 
			"						    SELECT '' as QLNV_KHVONPHI_ID \r\n" + 
			"						    ,'' as STT \r\n" + 
			"						    ,Sum(t.KPHI_BQUAN_THOC_TX) as KPHI_BQUAN_THOC_TX \r\n" + 
			"			                ,Sum(t.KPHI_BQUAN_THOC_LD) as KPHI_BQUAN_THOC_LD \r\n" + 
			"						    ,Sum(t.KPHI_BQUAN_GAO_TX) as KPHI_BQUAN_GAO_TX \r\n" + 
			"						    ,Sum(t.KPHI_BQUAN_GAO_LD) as KPHI_BQUAN_GAO_LD \r\n" + 
			"									FROM ( \r\n" + 
			"						                SELECT * FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_THOC_GAO ct  \r\n" + 
			"						                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id  \r\n" + 
			"						                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t ) a";

	@Query(value = tongHop, nativeQuery = true)
	QlnvKhvonphiKhoachBquanHnamThocGao synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiKhoachBquanHnamThocGao, nativeQuery = true)
	QlnvKhvonphiKhoachBquanHnamThocGao findQlnvKhvonphiKhoachBquanHnamThocGaoByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_THOC_GAO u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
