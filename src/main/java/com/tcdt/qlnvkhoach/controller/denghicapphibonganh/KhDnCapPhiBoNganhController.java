package com.tcdt.qlnvkhoach.controller.denghicapphibonganh;


import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.enums.EnumResponse;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhRequest;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhResponse;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhSearchResponse;
import com.tcdt.qlnvkhoach.service.denghicapphibonganh.KhDnCapPhiBoNganhService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/de-nghi-cap-phi-bo-nganh")
@Slf4j
@Api(tags = "Đề nghị cấp phí bộ ngành")
@RequiredArgsConstructor
public class KhDnCapPhiBoNganhController extends BaseController {
	private final KhDnCapPhiBoNganhService khDnCapPhiBoNganhService;

	@ApiOperation(value = "Tạo mới Đề nghị cấp phí bộ ngành", response = KhDnCapPhiBoNganhResponse.class)
	@PostMapping()
	public ResponseEntity<Resp> create(@RequestBody KhDnCapPhiBoNganhRequest req) {
		Resp resp = new Resp();
		try {
			KhDnCapPhiBoNganhResponse res = khDnCapPhiBoNganhService.create(req);
			resp.setData(res);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error("Create error", e);
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Sửa Đề nghị cấp phí bộ ngành", response = KhDnCapPhiBoNganhResponse.class)
	@PutMapping()
	public ResponseEntity<Resp> update(@Valid @RequestBody KhDnCapPhiBoNganhRequest req) {
		Resp resp = new Resp();
		try {
			KhDnCapPhiBoNganhResponse res = khDnCapPhiBoNganhService.update(req);
			resp.setData(res);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error("Update error", e);
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Xoá Đề nghị cấp phí bộ ngành", response = Boolean.class)
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> delete(@PathVariable("id") Long id) {
		Resp resp = new Resp();
		try {
			Boolean res = khDnCapPhiBoNganhService.delete(id);
			resp.setData(res);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Xoá danh sách Đề nghị cấp phí bộ ngành", response = Boolean.class)
	@DeleteMapping()
	public ResponseEntity<Resp> deleteMultiple(@RequestBody @Valid DeleteReq req) {
		Resp resp = new Resp();
		try {
			Boolean res = khDnCapPhiBoNganhService.deleteMultiple(req.getIds());
			resp.setData(res);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Search Đề nghị cấp phí bộ ngành", response = Page.class)
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> search(KhDnCapPhiBoNganhSearchRequest req) {
		Resp resp = new Resp();
		try {
			Page<KhDnCapPhiBoNganhSearchResponse> res = khDnCapPhiBoNganhService.search(req);
			resp.setData(res);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error("Search Error", e);
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Thông tin chi tiết Đề nghị cấp phí bộ ngành", response = Boolean.class)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> detail(@PathVariable("id") Long id) {
		Resp resp = new Resp();
		try {
			KhDnCapPhiBoNganhResponse res = khDnCapPhiBoNganhService.detail(id);
			resp.setData(res);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Update trạng thái Đề nghị cấp phí bộ ngành", response = KhDnCapPhiBoNganhResponse.class)
	@PutMapping(value = "/trang-thai", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateTrangThai(@RequestParam Long id,
												@RequestParam String trangThaiId) {
		Resp resp = new Resp();
		try {
			KhDnCapPhiBoNganhResponse res = khDnCapPhiBoNganhService.updateTrangThai(id, trangThaiId);
			resp.setData(res);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Export kế hoạch Đề nghị cấp phí bộ ngành", response = List.class)
	@PostMapping(value = "/export/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void exportToExcel(HttpServletResponse response, @RequestBody KhDnCapPhiBoNganhSearchRequest req) {

		try {
			khDnCapPhiBoNganhService.exportToExcel(req, response);
		} catch (Exception e) {
			log.error("Error can not export", e);
		}
	}

}



