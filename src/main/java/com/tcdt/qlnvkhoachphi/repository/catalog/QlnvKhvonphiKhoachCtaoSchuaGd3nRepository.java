package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiKhoachCtaoSchuaGd3n;

public interface QlnvKhvonphiKhoachCtaoSchuaGd3nRepository extends CrudRepository <QlnvKhvonphiKhoachCtaoSchuaGd3n, Long>{
	String qlnvKhvonphiKhoachCtaoSchuaGd3n = "SELECT * FROM QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";
	
	String tongHop = "SELECT ct.ID,ct.QLNV_KHVONPHI_ID,ct.STT,ct.TEN_CTRINH,ct.MA_NGUON_VON,ct.MA_LOAI_CT,ct.MA_DVI_TINH,ct.TGIAN_KC,ct.TGIAN_HT,ct.MA_CQUAN_QD,ct.TONG_GTRI_3006N,ct.DTOAN_KPHI,ct.DA_TTOAN_3006N,ct.UOC_TTOAN,ct.UOC_THIEN,ct.TSO_N1,ct.TTOAN_N1,ct.PHAT_SINH_N1,ct.TSO_N2,ct.TTOAN_N2,ct.PHAT_SINH_N2,ct.TSO_N3,ct.TTOAN_N3,ct.PHAT_SINH_N3 FROM QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N ct " + 
			"INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachCtaoSchuaGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiKhoachCtaoSchuaGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachCtaoSchuaGd3n> findQlnvKhvonphiKhoachCtaoSchuaGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
