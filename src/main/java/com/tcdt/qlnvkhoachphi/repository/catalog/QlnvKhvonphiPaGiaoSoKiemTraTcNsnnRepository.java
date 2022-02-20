package com.tcdt.qlnvkhoachphi.repository.catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnn;

public interface QlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository extends 
	CrudRepository<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn, Long>{
	
	String value = "SELECT * FROM QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN t ";

	String countQuery = "SELECT count(1) FROM QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN t ";

	@Query(value = value, countQuery = countQuery, nativeQuery = true)
	Page<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> selectParams(Pageable Page);
}
