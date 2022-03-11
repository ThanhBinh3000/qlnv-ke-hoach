package com.tcdt.qlnvkhoachphi.repository.catalog.quyetdinhdutoanchi;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnCtiet;

@Repository
public interface QlnvKhvonphiQdGiaoDtChiNsnnCtietRepository extends CrudRepository<QlnvKhvonphiQdGiaoDtChiNsnnCtiet, Long>{
	
	ArrayList<QlnvKhvonphiQdGiaoDtChiNsnnCtiet> findByQlnvKhvonphiDsQdId(Long qlnvKhvonphiDsQdId);
}
