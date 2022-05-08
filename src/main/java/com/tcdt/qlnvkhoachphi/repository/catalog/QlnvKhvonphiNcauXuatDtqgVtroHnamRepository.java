package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiNcauXuatDtqgVtroHnam;

public interface QlnvKhvonphiNcauXuatDtqgVtroHnamRepository extends CrudRepository <QlnvKhvonphiNcauXuatDtqgVtroHnam, Long>{
	String qlnvKhvonphiNcauXuatDtqgVtroHnam = "SELECT * FROM QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" +
			"    SELECT '' as QLNV_KHVONPHI_ID" +
			"    ,Sum(t.LUONG_XUAT_VTRO) as LUONG_XUAT_VTRO" +
			"			FROM (" +
			"                SELECT * FROM QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM ct " +
			"                INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " +
			"                INNER JOIN dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiNcauXuatDtqgVtroHnam> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiNcauXuatDtqgVtroHnam, nativeQuery = true)
	QlnvKhvonphiNcauXuatDtqgVtroHnam findQlnvKhvonphiNcauXuatDtqgVtroHnamByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}

