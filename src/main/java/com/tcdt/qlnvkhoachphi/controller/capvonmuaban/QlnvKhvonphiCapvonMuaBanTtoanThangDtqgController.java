package com.tcdt.qlnvkhoachphi.controller.capvonmuaban;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.enums.EnumResponse;
import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgExportService;
import com.tcdt.qlnvkhoachphi.service.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgServiceImpl;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_CAP_VON_DTQG)
@Slf4j
@Api(tags = "Cấp vốn mua, bán, thanh toán tiền hàng DTQG")
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgController extends BaseController {

	@Autowired
	private QlnvKhvonphiCapvonMuaBanTtoanThangDtqgServiceImpl qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl;

	@Autowired
	private QlnvKhvonphiCapvonMuaBanTtoanThangDtqgExportService qlnvKhvonphiCapvonMuaBanTtoanThangDtqgExportServicel;

	@ApiOperation(value = "Lấy chi tiết thông tin cấp vốn", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@RequestParam Long id) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.getDetail(id));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Lấy danh sách cấp vốn", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.getList(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Thêm mới cấp vốn", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.create(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Xóa cấp vốn", response = List.class)
	@DeleteMapping(value = PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.delete(ids);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Sửa cấp vốn", response = List.class)
	@PutMapping(value = PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq qlnvBaoCaoReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.update(qlnvBaoCaoReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Nhóm nút chức năng", response = List.class)
	@PutMapping(value = PathConstants.URL_CHUC_NANG, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> function(@Valid @RequestBody NhomNutChucNangReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiCapVonMuaBanTToanTHangDtqgServiceImpl.function(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Export excel", response = List.class)
	@PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void exportToExcel(HttpServletResponse response, @RequestParam(required = false) List<String> type,
			@RequestParam Long id) {

		try {
			response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=cap-von-mua-ban-thanh-toan-tien-hang-dtqg_" + currentDateTime
					+ ".xlsx";
			response.setHeader(headerKey, headerValue);
			qlnvKhvonphiCapvonMuaBanTtoanThangDtqgExportServicel.exportToExcel(response, type, id);
		} catch (Exception e) {
			log.error("Error can not export", e);
		}

	}
}