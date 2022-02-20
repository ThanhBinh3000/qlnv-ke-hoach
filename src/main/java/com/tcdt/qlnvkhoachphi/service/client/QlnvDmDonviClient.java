package com.tcdt.qlnvkhoachphi.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tcdt.qlnvkhoachphi.config.FeignConfig;
import com.tcdt.qlnvkhoachphi.request.BaseRequest;
import com.tcdt.qlnvkhoachphi.response.Resp;

import feign.Headers;

@FeignClient(name = "qlnv-category", configuration = FeignConfig.class)
public interface QlnvDmDonviClient {

	@GetMapping("/dmuc-donvi/chi-tiet/{ids}")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/json" })
	public Resp getDetail(@PathVariable("ids") String ids);

	@PostMapping("/dmuc-donvi/chi-tiet")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/json" })
	public Resp getDetailByCode(@RequestBody BaseRequest objReq);
}
