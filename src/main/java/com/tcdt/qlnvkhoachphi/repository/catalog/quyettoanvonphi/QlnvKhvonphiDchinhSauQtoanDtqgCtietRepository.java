package com.tcdt.qlnvkhoachphi.repository.catalog.quyettoanvonphi;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgCtiet;

@Repository
public interface QlnvKhvonphiDchinhSauQtoanDtqgCtietRepository 
	extends CrudRepository<QlnvKhvonphiDchinhSauQtoanDtqgCtiet, Long>{
	
	ArrayList<QlnvKhvonphiDchinhSauQtoanDtqgCtiet> findByQlnvKhvonphiDchinhId(Long qlnvKhvonphiDchinhId);

}
