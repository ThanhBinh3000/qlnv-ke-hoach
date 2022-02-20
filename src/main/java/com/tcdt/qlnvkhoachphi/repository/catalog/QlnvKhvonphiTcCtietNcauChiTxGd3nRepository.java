package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcCtietNcauChiTxGd3n;



public interface QlnvKhvonphiTcCtietNcauChiTxGd3nRepository extends CrudRepository <QlnvKhvonphiTcCtietNcauChiTxGd3n, Long>{
	String qlnvKhvonphiTcCtietNcauChiTxGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM ( \r\n" + 
			"SELECT '' as QLNV_KHVONPHI_ID \r\n" + 
			",'' as STT \r\n" + 
			",t.MA_NOI_DUNG as MA_NDUNG \r\n" + 
			",t.MA_NHOM_CHI \r\n" + 
			", Sum(t.NAM_HHANH_N) as THIEN_NAM_HHANH_N \r\n" + 
			", Sum(t.NCAU_CHI_CUA_DVI_N1) as NCAU_DTOAN_N1 \r\n" + 
			", Sum(t.NCAU_CHI_CUA_DVI_N2) as NCAU_DTOAN_N2 \r\n" + 
			", Sum(t.NCAU_CHI_CUA_DVI_N3) as NCAU_DTOAN_N3 \r\n" + 
			" FROM ( SELECT * FROM QLNV_KHVONPHI_CHI_TX_GD3N ct  \r\n" + 
			" INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id  \r\n" + 
			" INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi   \r\n" + 
			" WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t  \r\n" + 
			" GROUP BY t.MA_NOI_DUNG,t.MA_NHOM_CHI) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcCtietNcauChiTxGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcCtietNcauChiTxGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcCtietNcauChiTxGd3n> findQlnvKhvonphiTcCtietNcauChiTxGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
