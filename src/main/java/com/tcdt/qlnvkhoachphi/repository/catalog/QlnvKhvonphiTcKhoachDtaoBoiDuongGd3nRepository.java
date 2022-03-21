package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcKhoachDtaoBoiDuongGd3n;

public interface QlnvKhvonphiTcKhoachDtaoBoiDuongGd3nRepository extends CrudRepository <QlnvKhvonphiTcKhoachDtaoBoiDuongGd3n, Long>{
	String qlnvKhvonphiTcKhoachDtaoBoiDuongGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_KHOACH_DTAO_BOI_DUONG_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";


	@Query(value = qlnvKhvonphiTcKhoachDtaoBoiDuongGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachDtaoBoiDuongGd3n> findQlnvKhvonphiTcKhoachDtaoBoiDuongGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_KHOACH_DTAO_BOI_DUONG_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}