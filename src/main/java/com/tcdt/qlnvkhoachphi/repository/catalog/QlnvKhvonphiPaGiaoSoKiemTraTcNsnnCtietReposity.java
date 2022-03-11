package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet;

public interface QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReposity extends 
	CrudRepository<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet, Long>{
	
	String qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet = "SELECT * FROM QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET t "
			+ "WHERE t.QLNV_KHVONPHI_PA_ID = :qlnvKhvonphiPaId ";

	@Query(value = qlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet, nativeQuery = true)
	ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtiet> findByQlnvKhvonphiPaGiaoSoKiemTraTcNsnnId(Long qlnvKhvonphiPaId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN_CTIET u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);
	
}
