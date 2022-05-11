package com.tcdt.qlnvkhoach.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcdt.qlnvkhoach.config.FeignConfig;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.util.PathClientConstants;

import feign.Headers;

@FeignClient(name = "qlnv-category", configuration = FeignConfig.class)
public interface QlnvDmClient {

	@GetMapping(PathClientConstants.URL_DM_DVI_CHI_TIET_BY_ID)
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/json" })
	public Resp getDmDviByIdDetail(@PathVariable("ids") String ids);

	@GetMapping(PathClientConstants.URL_DM_VPHI_CHI_TIET_BY_ID)
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/json" })
	public Resp getDmKhoachVphiByIdDetail(@PathVariable("ids") String ids);

	@GetMapping(PathClientConstants.URL_DM_VPHI_GET_ALL)
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/json" })
	public Resp getAllByLoaiDmAndLevelDm(@PathVariable("loaiDm") String loaiDm,
			@PathVariable("levelDm") Integer levelDm);

}
