package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachBquanHnam;

public interface QlnvKhvonphiTcKhoachBquanHnamRepository extends CrudRepository <QlnvKhvonphiTcKhoachBquanHnam, Long>{
	String qlnvKhvonphiTcKhoachBquanHnam = "SELECT * FROM QLNV_KHVONPHI_TC_KHOACH_BQUAN_HNAM t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";



	@Query(value = qlnvKhvonphiTcKhoachBquanHnam, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachBquanHnam> findQlnvKhvonphiTcKhoachBquanHnamByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_KHOACH_BQUAN_HNAM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
