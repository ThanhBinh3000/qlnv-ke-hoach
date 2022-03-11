package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiNcauXuatDtqgVtroVtuHnam;

public interface QlnvKhvonphiNcauXuatDtqgVtroVtuHnamRepository extends CrudRepository <QlnvKhvonphiNcauXuatDtqgVtroVtuHnam, Long> {
	String qlnvKhvonphiNcauXuatDtqgVtroVtuHnam = "SELECT * FROM QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_VTU_HNAM t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";


	@Query(value = qlnvKhvonphiNcauXuatDtqgVtroVtuHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiNcauXuatDtqgVtroVtuHnam> findQlnvKhvonphiNcauXuatDtqgVtroVtuHnamByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_VTU_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);
}
