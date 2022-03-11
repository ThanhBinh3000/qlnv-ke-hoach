package com.tcdt.qlnvkhoachphi.repository.catalog.ketquathuchienvonphi;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienXuatHang;



public interface QlnvKhvonphiBcaoKquaThienXuatHangRepository extends CrudRepository <QlnvKhvonphiBcaoKquaThienXuatHang, Long>{
	String qlnvKhvonphiBcaoKquaThienXuatHang = "SELECT * FROM QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG t "
			+ "WHERE t.QLNV_KHVONPHI_BCAO_ID = :qlnvKhvonphiBcaoId ";

	String tongHop = "SELECT ct.ID,ct.QLNV_KHVONPHI_ID,ct.STT,ct.TEN_DAN,ct.MA_KHOACH,ct.MA_KHOI_DAN,ct.MA_DDIEM_XD,ct.DDIEM_MO_TAIKHOAN,ct.MASO_DAN,ct.MA_NGANH_KTE,ct.NLUC_TKE,ct.NAM_KC_TTE,ct.NAM_HT_TTE,ct.QD_DUYET_DAN_DTU_SONGAYTHANG,ct.QD_DUYET_DAN_DTU_TONG_VON,ct.QD_DCHINH_DAN_DTU_SONGAYTHANG,ct.QD_DCHINH_DAN_DTU_TONG_VON,ct.QD_DUYET_TK_DTOAN_SONGAYTHANG,ct.QD_DUYET_TK_DTOAN_TONG,ct.QD_DUYET_TK_DTOAN_XL,ct.QD_DUYET_TK_DTOAN_TB,ct.QD_DUYET_TK_DTOAN_CK,ct.KLTH_CAP_DEN_3006_SONGAYTHANG,ct.KLTH_CAP_DEN_3006_NSTT,ct.KLTH_CAP_DEN_3006_DTOAN_CHI_TX,ct.KLTH_CAP_DEN_3006_QUYKHAC,ct.KLTH_CAP_DEN_3112_SONGAYTHANG,ct.KLTH_CAP_DEN_3112_NSTT,ct.KLTH_CAP_DEN_3112_DTOAN_CHI_TX,ct.KLTH_CAP_DEN_3112_QUYKHAC,ct.NCAU_VON_N1,ct.NCAU_VON_N2,ct.NCAU_VON_N3,ct.GHICHU FROM QLNV_KHVONPHI_DM_VONDT_XDCBGD3N ct" +
			"INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id" +
			"INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoKquaThienXuatHang> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiBcaoKquaThienXuatHang, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoKquaThienXuatHang> findQlnvKhvonphiBcaoKquaThienXuatHangByQlnvKhvonphiBcaoId(Long qlnvKhvonphiBcaoId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
