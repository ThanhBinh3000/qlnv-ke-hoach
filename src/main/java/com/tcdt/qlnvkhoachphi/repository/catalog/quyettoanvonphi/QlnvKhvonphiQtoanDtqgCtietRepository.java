package com.tcdt.qlnvkhoachphi.repository.catalog.quyettoanvonphi;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqgCtiet;

@Repository
public interface QlnvKhvonphiQtoanDtqgCtietRepository 
extends CrudRepository<QlnvKhvonphiQtoanDtqgCtiet, Long>{
	
	ArrayList<QlnvKhvonphiQtoanDtqgCtiet> findByQlnvKhvonphiQtoanId(Long qlnvKhvonphiCapvonId);
}
