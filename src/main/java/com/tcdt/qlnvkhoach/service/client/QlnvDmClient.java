package com.tcdt.qlnvkhoach.service.client;

import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.request.HhDmDviLquanSearchReq;
import com.tcdt.qlnvkhoach.request.QlnvDmDonviSearchReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping( PathClientConstants.URL_DM_DON_VI_GET_ALL)
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getAllDanhMucDonVi(@PathVariable("capDvi") String capDvi);

	@GetMapping(PathClientConstants.URL_DM_HANG_HOA_GET_ALL)
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getDanhMucHangHoa();

	@GetMapping(PathClientConstants.URL_DM_DUNG_CHUNG)
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getDanhMucChung(@PathVariable("loai") String loai);

	@GetMapping(PathClientConstants.URL_DM_TCHUAN_HDR)
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getTchuanCluong(@PathVariable("maHh") String loai);

	//Add new endpoints
	@PostMapping("/dmuc-donvi/chi-tiet")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getDetailByCode(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@RequestBody BaseRequest objReq);

	@GetMapping("/dmuc-chung/danh-sach/{loai}")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getDanhMucChung(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@PathVariable("loai") String loai);

	@PostMapping("/dmuc-donvi/tat-ca")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getDanhMucDvi(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,@RequestBody QlnvDmDonviSearchReq objReq);

	@PostMapping("/dmuc-dvi-lquan/tat-ca")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getDanhMucDviLquan(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@RequestBody HhDmDviLquanSearchReq objReq);

	@GetMapping("/dm-hang/danh-sach/dvql")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getDanhMucHangHoa(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

}
