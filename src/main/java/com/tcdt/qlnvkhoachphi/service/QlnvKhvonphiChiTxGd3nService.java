package com.tcdt.qlnvkhoachphi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvkhoachphi.repository.catalog.QlnvKhvonphiChiTxGd3nRepository;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiChiTxGd3n;

@Service
//@CacheConfig(cacheNames = "QlnvKhvonphiChiTxGd3nCache")
public class QlnvKhvonphiChiTxGd3nService {
	@Autowired
	private QlnvKhvonphiChiTxGd3nRepository qlnvCTietBCaoCTXGD03NRepository;

//	@Cacheable
	public QlnvKhvonphiChiTxGd3n getCTietBaoCaoById(long id) {
		Optional<QlnvKhvonphiChiTxGd3n> qOptional = qlnvCTietBCaoCTXGD03NRepository.findById(id);
		return qOptional.get();
	}
}
