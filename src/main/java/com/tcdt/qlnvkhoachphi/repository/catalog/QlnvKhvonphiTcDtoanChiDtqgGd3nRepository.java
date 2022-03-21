package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcDtoanChiDtqgGd3n;



public interface QlnvKhvonphiTcDtoanChiDtqgGd3nRepository extends CrudRepository <QlnvKhvonphiTcDtoanChiDtqgGd3n, Long>{
	String qlnvKhvonphiTcDtoanChiDtqgGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

//	String tongHop = "";
//
//	@Query(value = tongHop, nativeQuery = true)
//	ArrayList<QlnvKhvonphiTcDtoanChiDtqgGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcDtoanChiDtqgGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanChiDtqgGd3n> findQlnvKhvonphiTcDtoanChiDtqgGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
