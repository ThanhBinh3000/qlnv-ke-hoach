package com.tcdt.qlnvkhoachphi.controller.chitieukehoachnam;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.ChiTieuKeHoachNamImportService;
import com.tcdt.qlnvkhoachphi.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoachphi.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/chi-tieu-ke-hoach-nam")
@Slf4j
@Api(tags = "Quản lý chỉ tiêu kế hoạch năm")
public class ChiTieuKeHoachNamController extends BaseController {
	@Autowired
	private ChiTieuKeHoachNamService chiTieuKeHoachNamService;

	@Autowired
	private ChiTieuKeHoachNamImportService importSv;

	@PostMapping
	public final ResponseEntity<Resp> create(@RequestBody ChiTieuKeHoachNamReq req) throws Exception {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamService.create(req));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
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

	@ApiOperation(value = "Import file kế hoạch lương thực", response = List.class)
	@PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@RequestPart("file") MultipartFile file) {
		Resp resp = new Resp();
		try {
			resp.setData(importSv.importKeHoach(file));
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
