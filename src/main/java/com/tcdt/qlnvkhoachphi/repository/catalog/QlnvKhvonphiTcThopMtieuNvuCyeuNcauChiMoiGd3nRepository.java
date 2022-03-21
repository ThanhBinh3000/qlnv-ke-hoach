package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n;

public interface QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nRepository extends CrudRepository <QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n, Long>{
	String qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

//	String tongHop = "";
//
//	@Query(value = tongHop, nativeQuery = true)
//	ArrayList<QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3n> findQlnvKhvonphiTcThopMtieuNvuCyeuNcauChiMoiGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
