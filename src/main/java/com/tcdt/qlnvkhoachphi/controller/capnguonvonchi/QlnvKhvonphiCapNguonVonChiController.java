package com.tcdt.qlnvkhoachphi.controller.capnguonvonchi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.request.object.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvonReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.capnguonvonchi.QlnvKhvonphiThopCapvonReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvonSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.capnguonvonchi.QlnvKhvonphiThopCapvonSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.capnguonvonchi.QlnvKhvonphiCapNguonVonChiService;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_CAP_NGUON_VON_CHI)
@Slf4j
@Api(tags = "Quản lý cấp nguồn vốn chi")
public class QlnvKhvonphiCapNguonVonChiController extends BaseController {
	@Autowired
	private QlnvKhvonphiCapNguonVonChiService khvonphiCapNguonVonChiService;

	@ApiOperation(value = "Lấy chi tiết đề nghị cấp vốn", response = List.class)
	@GetMapping(value = "/dncv/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailDncv(@PathVariable("ids") Long id) {
		Resp resp = new Resp();
		try {
			resp.setData(khvonphiCapNguonVonChiService.detailDncv(id));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tạo mới đề nghị cấp vốn", response = List.class)
	@PostMapping(value = "/dncv", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> createDncv(@Valid @RequestBody QlnvKhvonphiDnghiCapvonReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(khvonphiCapNguonVonChiService.createDncv(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu đề nghị cấp vốn", response = List.class)
	@PostMapping(value = "/search-dncv", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> searchDncv(@Valid @RequestBody QlnvKhvonphiDnghiCapvonSearchReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(khvonphiCapNguonVonChiService.searchDncv(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Sửa đề nghị cấp vốn", response = List.class)
	@PutMapping(value = "/dncv", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> updateDncv(@Valid @RequestBody QlnvKhvonphiDnghiCapvonReq qlnvBaoCaoReq) {
		Resp resp = new Resp();
		try {
			resp.setData(khvonphiCapNguonVonChiService.updateDncv(qlnvBaoCaoReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Lấy chi tiết tổng hợp cấp vốn", response = List.class)
	@GetMapping(value = "/thcv/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailThcv(@PathVariable("id") Long id) {
		Resp resp = new Resp();
		try {
			resp.setData(khvonphiCapNguonVonChiService.detailThcv(id));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tạo mới tổng hợp cấp vốn", response = List.class)
	@PostMapping(value = "/thcv", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> createThcv(@Valid @RequestBody QlnvKhvonphiThopCapvonReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(khvonphiCapNguonVonChiService.createThcv(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu tổng hợp cấp vốn", response = List.class)
	@PostMapping(value = "/search-thcv", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> searchThcv(@Valid @RequestBody QlnvKhvonphiThopCapvonSearchReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(khvonphiCapNguonVonChiService.searchThcv(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Sửa tổng hợp cấp vốn", response = List.class)
	@PutMapping(value = "/thcv", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> updateThcv(@Valid @RequestBody QlnvKhvonphiThopCapvonReq qlnvBaoCaoReq) {
		Resp resp = new Resp();
		try {
			resp.setData(khvonphiCapNguonVonChiService.updateThcv(qlnvBaoCaoReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

}
