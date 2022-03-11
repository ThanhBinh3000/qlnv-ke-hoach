package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiChiDtaiDanNckhGd3n;

public interface QlnvKhvonphiChiDtaiDanNckhGd3nRepository extends CrudRepository <QlnvKhvonphiChiDtaiDanNckhGd3n, Long>{
	String qlnvKhvonphiChiDtaiDanNckhGd3n = "SELECT * FROM QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";
	
	String tongHop = "SELECT ct.ID,ct.QLNV_KHVONPHI_ID,ct.STT,ct.TEN_DTAI_DAN,ct.MA_DVI,ct.TG_BDAU,ct.TG_KTHUC,ct.KPHI_TONG_PHI_DUOC_DUYET,ct.KPHI_DA_DUOC_BTRI,ct.KPHI_DUOC_THIEN_DEN_THOI_DIEM_BCAO,ct.KPHI_DU_KIEN_BTRI_N1,ct.KPHI_DU_KIEN_BTRI_N2,ct.KPHI_DU_KIEN_BTRI_N3,ct.KPHI_THUHOI,ct.KPHI_TGIAN_THUHOI FROM QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N ct  " + 
			"INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id" + 
			"INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiChiDtaiDanNckhGd3n> synthesis(String maDviCha, String namHienHanh);
	
	@Query(value = qlnvKhvonphiChiDtaiDanNckhGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiChiDtaiDanNckhGd3n> findQlnvKhvonphiChiDtaiDanNckhGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);
}
