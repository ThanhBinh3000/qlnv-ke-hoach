package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcNcauKhoachDtxdGd3n;

public interface QlnvKhvonphiTcNcauKhoachDtxdGd3nRepository extends CrudRepository <QlnvKhvonphiTcNcauKhoachDtxdGd3n, Long>{
	String qlnvKhvonphiTcNcauKhoachDtxdGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	@Query(value = qlnvKhvonphiTcNcauKhoachDtxdGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcNcauKhoachDtxdGd3n> findQlnvKhvonphiTcNcauKhoachDtxdGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}