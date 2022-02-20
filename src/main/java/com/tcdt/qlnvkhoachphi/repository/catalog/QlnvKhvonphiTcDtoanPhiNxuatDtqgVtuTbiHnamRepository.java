package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam;


public interface QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamRepository extends CrudRepository <QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam, Long>{
	String qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam = "SELECT * FROM QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_VTU_TBI_HNAM t "
			+ "WHERE t.NXUAT_DTQG_HNAM_ID = :nxuatDtqgHnamId ";



	@Query(value = qlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnam> findQlnvKhvonphiTcDtoanPhiNxuatDtqgVtuTbiHnamByNxuatDtqgHnamId(Long nxuatDtqgHnamId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_VTU_TBI_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
