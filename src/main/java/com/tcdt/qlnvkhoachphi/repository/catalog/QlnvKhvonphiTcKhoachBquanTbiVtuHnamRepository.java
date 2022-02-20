package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachBquanTbiVtuHnam;

public interface QlnvKhvonphiTcKhoachBquanTbiVtuHnamRepository extends CrudRepository <QlnvKhvonphiTcKhoachBquanTbiVtuHnam, Long>{
	String qlnvKhvonphiTcKhoachBquanTbiVtuHnam = "SELECT * FROM QLNV_KHVONPHI_TC_KHOACH_BQUAN_TBI_VTU_HNAM t "
			+ "WHERE t.BQUAN_HNAM_ID = :bquanHnamId ";



	@Query(value = qlnvKhvonphiTcKhoachBquanTbiVtuHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachBquanTbiVtuHnam> findQlnvKhvonphiTcKhoachBquanTbiVtuHnamByBquanHnamId(Long bquanHnamId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_KHOACH_BQUAN_TBI_VTU_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}