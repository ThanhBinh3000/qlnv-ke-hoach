package com.tcdt.qlnvkhoachphi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.client.QlnvDmDonviClient;
import com.tcdt.qlnvkhoachphi.util.Constants;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QlnvDmDonViService {
	@Autowired
	private QlnvDmDonviClient qlnvDmDonviClient;
	@Autowired
	private Gson gson;

	@Autowired
	private ModelMapper modelMapper;

	@Cacheable(value = "dmDonviCache", key = "#maDvi", unless = "#result == null")
	public QlnvDmDonvi getDonViById(String maDvi) throws Exception {
//		String ids = maDvi.toString();
		QlnvDmDonvi qlnvDmDonvi = null;
		try {
			Resp resp = qlnvDmDonviClient.getDetail(maDvi);
			log.info("qlnvDmDonviClient.getDetail: {}", gson.toJson(resp));
			if (resp.getStatusCode() != Constants.RESP_SUCC) {
				throw new NotFoundException(resp.getMsg());
			}
			qlnvDmDonvi = modelMapper.map(resp.getData(), QlnvDmDonvi.class);
		} catch (Exception e) {
			log.error("Không thể lấy thông tin danh mục đơn vị", e);
			throw e;
		}
		return qlnvDmDonvi;
	}
}
