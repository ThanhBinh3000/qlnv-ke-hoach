package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam;



public interface QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamRepository extends CrudRepository <QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam, Long>{
	String qlnvKhvonphiTcDtoanPhiNxuatDtqgHnam = "SELECT * FROM QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_HNAM t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";



	@Query(value = qlnvKhvonphiTcDtoanPhiNxuatDtqgHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgHnam> findQlnvKhvonphiTcDtoanPhiNxuatDtqgHnamByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}