package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachQuyTienLuongHnam;

public interface QlnvKhvonphiKhoachQuyTienLuongHnamRepository extends CrudRepository <QlnvKhvonphiKhoachQuyTienLuongHnam, Long>{
	String qlnvKhvonphiKhoachQuyTienLuongHnam = "SELECT * FROM QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" +
			"    SELECT '' as QLNV_KHVONPHI_ID" +
			"    ,'' as STT" +
			"    ,t.MA_DVI" +
			"    ,Sum(t.BCHE_GIAO_N1) as BCHE_GIAO_N1" +
			"    ,Sum(t.DU_KIEN_SO_CCVC_CO_MAT_N1) as DU_KIEN_SO_CCVC_CO_MAT_N1" +
			"    ,Sum(t.DU_KIEN_SO_HDONG_CO_MAT_N1) as DU_KIEN_SO_HDONG_CO_MAT_N1" +
			"    ,Sum(t.BCHE_CHUA_SDUNG) as BCHE_CHUA_SDUNG" +
			"    ,Sum(t.TONGQUY_LUONG_PCAP_THEO_LUONG_CCVC_HDLD) as TONGQUY_LUONG_PCAP_THEO_LUONG_CCVC_HDLD" +
			"    ,Sum(t.TONG_SO) as TONG_SO" +
			"    ,Sum(t.CCVC_DU_KIEN_CO_MAT_N1_CONG) as CCVC_DU_KIEN_CO_MAT_N1_CONG" +
			"    ,Sum(t.CCVC_DU_KIEN_CO_MAT_N1_LUONG_THEO_BAC) as CCVC_DU_KIEN_CO_MAT_N1_LUONG_THEO_BAC" +
			"    ,Sum(t.CCVC_DU_KIEN_CO_MAT_N1_PCAP) as CCVC_DU_KIEN_CO_MAT_N1_PCAP" +
			"    ,Sum(t.CCVC_DU_KIEN_CO_MAT_N1_CKDG) as CCVC_DU_KIEN_CO_MAT_N1_CKDG" +
			"    ,Sum(t.QUY_LUONG_TANG_NANG_BAC_LUONG_N1) as QUY_LUONG_TANG_NANG_BAC_LUONG_N1" +
			"    ,Sum(t.BCHE_CHUA_SDUNG_CONG) as BCHE_CHUA_SDUNG_CONG" +
			"    ,Sum(t.BCHE_CHUA_SDUNG_LUONG) as BCHE_CHUA_SDUNG_LUONG" +
			"    ,Sum(t.BCHE_CHUA_SDUNG_CKDG) as BCHE_CHUA_SDUNG_CKDG" +
			"    ,Sum(t.QUY_LUONG_PCAP_THEO_HDLD) as QUY_LUONG_PCAP_THEO_HDLD" +
			"			FROM (" +
			"                SELECT ct.MA_DVI,ct.BCHE_GIAO_N1,ct.DU_KIEN_SO_CCVC_CO_MAT_N1,ct.DU_KIEN_SO_HDONG_CO_MAT_N1,ct.BCHE_CHUA_SDUNG,ct.TONGQUY_LUONG_PCAP_THEO_LUONG_CCVC_HDLD,ct.TONG_SO,ct.CCVC_DU_KIEN_CO_MAT_N1_CONG,ct.CCVC_DU_KIEN_CO_MAT_N1_LUONG_THEO_BAC,ct.CCVC_DU_KIEN_CO_MAT_N1_PCAP,ct.CCVC_DU_KIEN_CO_MAT_N1_CKDG,ct.QUY_LUONG_TANG_NANG_BAC_LUONG_N1,ct.BCHE_CHUA_SDUNG_CONG,ct.BCHE_CHUA_SDUNG_LUONG,ct.BCHE_CHUA_SDUNG_CKDG,ct.QUY_LUONG_PCAP_THEO_HDLD FROM QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM ct " +
			"                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " +
			"                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t " +
			"			GROUP BY t.MA_DVI) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachQuyTienLuongHnam> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiKhoachQuyTienLuongHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachQuyTienLuongHnam> findQlnvKhvonphiKhoachQuyTienLuongHnamByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}

