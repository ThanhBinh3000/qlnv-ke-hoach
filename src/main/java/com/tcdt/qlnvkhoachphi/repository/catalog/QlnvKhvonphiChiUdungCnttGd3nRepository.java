package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiChiUdungCnttGd3n;

public interface QlnvKhvonphiChiUdungCnttGd3nRepository extends CrudRepository <QlnvKhvonphiChiUdungCnttGd3n, Long>{
	String qlnvKhvonphiChiUdungCnttGd3n = "SELECT * FROM QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";
	
	String tongHop = "SELECT ct.ID,ct.QLNV_KHVONPHI_ID,ct.STT,ct.NDUNG,ct.LOAI_KHOACH,ct.LOAI_DAN,ct.TONG_DTOAN_SL,ct.TONG_DTOAN_GTRI,ct.THIEN_NAM_TRUOC,ct.CB_DTU_N,ct.TH_DTU_N,ct.CB_DTU_N1,ct.TH_DTU_N1,ct.CB_DTU_N2,ct.TH_DTU_N2,ct.CB_DTU_N3,ct.TH_DTU_N3,ct.GHI_CHU FROM QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N ct \r\n" + 
			"INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id  \r\n" + 
			"INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiChiUdungCnttGd3n> synthesis(String maDviCha, String namHienHanh);
	
	@Query(value = qlnvKhvonphiChiUdungCnttGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiChiUdungCnttGd3n> findQlnvKhvonphiChiUdungCnttGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
