package com.tcdt.qlnvkhoach.controller.denghicapvonbonganh;


import com.tcdt.qlnvkhoach.controller.BaseController;
import com.tcdt.qlnvkhoach.enums.EnumResponse;
import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.response.Resp;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhResponse;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhSearchResponse;
import com.tcdt.qlnvkhoach.service.denghicapvonbonganh.KhDnCapVonBoNganhService;
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
@RequestMapping(value = "/de-nghi-cap-von-bo-nganh")
@Slf4j
@Api(tags = "Đề nghị cấp vốn bộ ngành")
@RequiredArgsConstructor
public class KhDnCapVonBoNganhController extends BaseController {
	private final KhDnCapVonBoNganhService khDnCapVonBoNganhService;

	@ApiOperation(value = "Tạo mới Đề nghị cấp vốn bộ ngành", response = KhDnCapVonBoNganhResponse.class)
	@PostMapping()
	public ResponseEntity<Resp> create(@RequestBody KhDnCapVonBoNganhRequest req) {
		Resp resp = new Resp();
		try {
			KhDnCapVonBoNganhResponse res = khDnCapVonBoNganhService.create(req);
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

	@ApiOperation(value = "Sửa Đề nghị cấp vốn bộ ngành", response = KhDnCapVonBoNganhResponse.class)
	@PutMapping()
	public ResponseEntity<Resp> update(@Valid @RequestBody KhDnCapVonBoNganhRequest req) {
		Resp resp = new Resp();
		try {
			KhDnCapVonBoNganhResponse res = khDnCapVonBoNganhService.update(req);
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

	@ApiOperation(value = "Xoá Đề nghị cấp vốn bộ ngành", response = Boolean.class)
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> delete(@PathVariable("id") Long id) {
		Resp resp = new Resp();
		try {
			Boolean res = khDnCapVonBoNganhService.delete(id);
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

	@ApiOperation(value = "Xoá danh sách Đề nghị cấp vốn bộ ngành", response = Boolean.class)
	@DeleteMapping()
	public ResponseEntity<Resp> deleteMultiple(@RequestBody @Valid DeleteReq req) {
		Resp resp = new Resp();
		try {
			Boolean res = khDnCapVonBoNganhService.deleteMultiple(req.getIds());
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

	@ApiOperation(value = "Search Đề nghị cấp vốn bộ ngành", response = Page.class)
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> search(KhDnCapVonBoNganhSearchRequest req) {
		Resp resp = new Resp();
		try {
			Page<KhDnCapVonBoNganhSearchResponse> res = khDnCapVonBoNganhService.search(req);
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

	@ApiOperation(value = "Thông tin chi tiết Đề nghị cấp vốn bộ ngành", response = Boolean.class)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> detail(@PathVariable("id") Long id) {
		Resp resp = new Resp();
		try {
			KhDnCapVonBoNganhResponse res = khDnCapVonBoNganhService.detail(id);
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

	@ApiOperation(value = "Update trạng thái Đề nghị cấp vốn bộ ngành", response = KhDnCapVonBoNganhResponse.class)
	@PutMapping(value = "/trang-thai", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateTrangThai(@RequestParam Long id,
												@RequestParam String trangThaiId) {
		Resp resp = new Resp();
		try {
			KhDnCapVonBoNganhResponse res = khDnCapVonBoNganhService.updateTrangThai(id, trangThaiId);
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

	@ApiOperation(value = "Export kế hoạch Đề nghị cấp vốn bộ ngành", response = List.class)
	@PostMapping(value = "/export/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void exportToExcel(HttpServletResponse response, @RequestBody KhDnCapVonBoNganhSearchRequest req) {

		try {
			khDnCapVonBoNganhService.exportToExcel(req, response);
		} catch (Exception e) {
			log.error("Error can not export", e);
		}
	}

}



