package com.tcdt.qlnvkhoachphi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmKhoachVonPhi;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.client.QlnvDmClient;
import com.tcdt.qlnvkhoachphi.util.Constants;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QlnvDmService {
	@Autowired
	private QlnvDmClient qlnvDmClient;
	@Autowired
	private Gson gson;

	@Autowired
	private ModelMapper modelMapper;

	@Cacheable(value = "dmDonviCache", key = "#maDvi", unless = "#result==null")
	public QlnvDmDonvi getDonViById(String maDvi) throws Exception {
		QlnvDmDonvi qlnvDmDonvi = null;
		try {
			Resp resp = qlnvDmClient.getDmDviByIdDetail(maDvi);
			log.info("Kết quả danh mục client: {}", gson.toJson(resp));
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

	@Cacheable(value = "dmKhoachVonPhiCache", key = "#maDmKhoachVonPhi", unless = "#result==null")
	public QlnvDmKhoachVonPhi getDmKhoachVonPhiById(String maDmKhoachVonPhi) throws Exception {
		QlnvDmKhoachVonPhi qlnvDmVonPhi = null;
		try {
			Resp resp = qlnvDmClient.getDmKhoachVphiByIdDetail(maDmKhoachVonPhi);
			log.info("Kết quả danh mục client: {}", gson.toJson(resp));
			if (resp.getStatusCode() != Constants.RESP_SUCC) {
				throw new NotFoundException(resp.getMsg());
			}
			if (resp == null || resp.getData() == null) {
				return null;
			}
			qlnvDmVonPhi = modelMapper.map(resp.getData(), QlnvDmKhoachVonPhi.class);
		} catch (Exception e) {
			log.error("Không thể lấy thông tin danh mục kế hoạch phí", e);
			throw e;
		}
		return qlnvDmVonPhi;
	}

}
