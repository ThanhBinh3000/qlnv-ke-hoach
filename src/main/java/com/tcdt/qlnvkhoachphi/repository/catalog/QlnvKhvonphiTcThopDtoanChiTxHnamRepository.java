package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcThopDtoanChiTxHnam;

public interface QlnvKhvonphiTcThopDtoanChiTxHnamRepository extends CrudRepository <QlnvKhvonphiTcThopDtoanChiTxHnam, Long>{
	String qlnvKhvonphiTcThopDtoanChiTxHnam = "SELECT * FROM QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" + 
			"			    SELECT '' as QLNV_KHVONPHI_ID" + 
			"			    ,'' as STT" + 
			"			    ,t.MA_LOAI,t.MA_KHOAN,t.MA_CHI_MUC" + 
			"			    ,Sum(t.SO_LUOT_NGUOI_N1) as SO_LUOT_NGUOI_N1" + 
			"			    ,Sum(t.THANH_TIEN_N1) as THANH_TIEN_N1" + 
			"			    ,Sum(t.SO_LUOT_NGUOI_N2) as SO_LUOT_NGUOI_N2" + 
			"			    ,Sum(t.THANH_TIEN_N2) as THANH_TIEN_N2" + 
			"			    ,Sum(t.SO_LUOT_NGUOI_N3) as SO_LUOT_NGUOI_N3" + 
			"			    ,Sum(t.THANH_TIEN_N3) as THANH_TIEN_N3" + 
			"						FROM (" + 
			"			                SELECT * FROM QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N ct " + 
			"			                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"			                INNER JOIN dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t " +
			"						GROUP BY t.MA_LOAI,t.MA_KHOAN,t.MA_CHI_MUC) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcThopDtoanChiTxHnam> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcThopDtoanChiTxHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcThopDtoanChiTxHnam> findQlnvKhvonphiTcThopDtoanChiTxHnamByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
