package com.tcdt.qlnvkhoachphi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiRepository;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphi;

@Service
//@CacheConfig(cacheNames = "QlnvKhvonphiCache")
public class QlnvKhvonphiService {
	@Autowired
	private QlnvKhvonphiRepository baoCaoRepository;

//	@Cacheable
	public QlnvKhvonphi getBaoCaoById(long id) {
		Optional<QlnvKhvonphi> qOptional = baoCaoRepository.findById(id);
		return qOptional.get();
	}
}
