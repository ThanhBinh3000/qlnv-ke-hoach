package com.tcdt.qlnvkhoachphi.controller.chitieukehoachnam;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoachphi.util.Constants;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/chi-tieu-ke-hoach-nam")
@Slf4j
@Api(tags = "Quản lý chỉ tiêu kế hoạch năm")
public class ChiTieuKeHoachNamController extends BaseController {
	@Autowired
	private ChiTieuKeHoachNamService chiTieuKeHoachNamService;

	@PostMapping
	public final ResponseEntity<Resp> create(@RequestBody ChiTieuKeHoachNamReq req) throws Exception {
		Resp resp = new Resp();
//		try {
			resp.setData(chiTieuKeHoachNamService.create(req));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
//		} catch (Exception e) {
//			resp.setStatusCode(Constants.RESP_FAIL);
//			resp.setMsg(e.getMessage());
//			log.error(e.getMessage());
//		}
		return ResponseEntity.ok(resp);
	}

	@GetMapping("/{id}")
	public final ResponseEntity<Resp> getDetail(@PathVariable("id") Long id) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamService.detail(id));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
}
