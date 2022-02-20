package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanChiUdungCnttGd3n;

public interface QlnvKhvonphiTcDtoanChiUdungCnttGd3nRepository extends CrudRepository <QlnvKhvonphiTcDtoanChiUdungCnttGd3n, Long>{
	String qlnvKhvonphiTcDtoanChiUdungCnttGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ct.ID\r\n" + 
			",ct.QLNV_KHVONPHI_ID\r\n" + 
			",ct.STT\r\n" + 
			",ct.NDUNG\r\n" + 
			",ct.LOAI_KHOACH as MA_LOAI_KHOACH\r\n" + 
			",ct.LOAI_DAN as MA_LOAI_DAN\r\n" + 
			",ct.TONG_DTOAN_SL as TONG_DTOAN_SL\r\n" + 
			",ct.TONG_DTOAN_GTRI as TONG_DTOAN_GTRI\r\n" + 
			",ct.THIEN_NAM_TRUOC as THIEN_NAM_TRUOC\r\n" + 
			",ct.CB_DTU_N as DTOAN_THIEN_N_CB\r\n" + 
			",ct.TH_DTU_N as DTOAN_THIEN_N_TH\r\n" + 
			",ct.CB_DTU_N1 as DTOAN_THIEN_N1_CB\r\n" + 
			",ct.TH_DTU_N1 as DTOAN_THIEN_N1_TH\r\n" + 
			",ct.CB_DTU_N2 as DTOAN_THIEN_N2_CB\r\n" + 
			",ct.TH_DTU_N2 as DTOAN_THIEN_N2_TH\r\n" + 
			",ct.CB_DTU_N3 as DTOAN_THIEN_N3_CB\r\n" + 
			",ct.TH_DTU_N3 as DTOAN_THIEN_N3_TH\r\n" + 
			",ct.GHI_CHU FROM QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N ct \r\n" + 
			"			INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id  \r\n" + 
			"			INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh";


	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanChiUdungCnttGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcDtoanChiUdungCnttGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanChiUdungCnttGd3n> findQlnvKhvonphiTcDtoanChiUdungCnttGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
