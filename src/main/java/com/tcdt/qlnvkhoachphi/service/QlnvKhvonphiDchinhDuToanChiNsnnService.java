package com.tcdt.qlnvkhoachphi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiDchinhDuToanChiNsnnRepository;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiDchinhDuToanChiNsnn;

@Service
//@CacheConfig(cacheNames = "QlnvKhvonphiCache")
public class QlnvKhvonphiDchinhDuToanChiNsnnService {
	@Autowired
	private QlnvKhvonphiDchinhDuToanChiNsnnRepository qlnvKhvonphiDchinhDuToanChiNsnnRepository;
//	@Cacheable
	public QlnvKhvonphiDchinhDuToanChiNsnn getBaoCaoById(long id) {
		Optional<QlnvKhvonphiDchinhDuToanChiNsnn> qOptional = qlnvKhvonphiDchinhDuToanChiNsnnRepository.findById(id);
		return qOptional.get();
	}
}


