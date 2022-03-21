package com.tcdt.qlnvkhoachphi.controller.quyetdinhgiaodutoanchi;

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
import org.springframework.transaction.annotation.Transactional;
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
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnExportService;
import com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnService;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_QD_GIAO_DU_TOAN_CHI)
@Api(tags = "Quyết định giao dự toán chi")
@Slf4j
public class QlnvKhvonphiQdGiaoDtChiNsnnController extends BaseController {

	@Autowired
	private QlnvKhvonphiQdGiaoDtChiNsnnService qlnvKhvonphiQdGiaoDtChiNsnnService;
	
	@Autowired
	private QlnvKhvonphiQdGiaoDtChiNsnnExportService qlnvKhvonphiQdGiaoDtChiNsnnExportService;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	@ApiOperation(value = "Thêm mới quyết định giao dự toán chi", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiQdGiaoDtChiNsnnReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiQdGiaoDtChiNsnnService.create(objReq));
			qlnvKhvonphiQdGiaoDtChiNsnnService.create(objReq);
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
	@ApiOperation(value = "Sửa quyết định giao dự toán chi", response = List.class)
	@PutMapping(value = PathConstants.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiQdGiaoDtChiNsnnReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiQdGiaoDtChiNsnnService.update(objReq));
			qlnvKhvonphiQdGiaoDtChiNsnnService.update(objReq);
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
	@ApiOperation(value = "Xóa quyết định giao dự toán chi", response = List.class)
	@DeleteMapping(value = PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			qlnvKhvonphiQdGiaoDtChiNsnnService.delete(ids);
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
	@ApiOperation(value = "Lấy chi tiết thông tin quyết định giao dự toán chi", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiQdGiaoDtChiNsnnService.detail(ids));
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
	@ApiOperation(value = "Tìm kiếm danh sách quyết đinh giao dự toán chi", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colectionPa(@Valid @RequestBody QlnvKhvonphiQdGiaoDtChiNsnnSearchReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiQdGiaoDtChiNsnnService.colection(objReq));
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
			@Valid @RequestBody QlnvKhvonphiQdGiaoDtChiNsnnSearchReq objReq) {
		try {
			response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "";

			for (String item : type) {
				if (item.equals(Constants.ExportDataType.QTOAN_DTQG)) {// 01
					headerValue = "attachment; filename=quyet-toan-du-tru-quoc-gia_"
							+ currentDateTime + ".xlsx";
				}
			}

			response.setHeader(headerKey, headerValue);
			qlnvKhvonphiQdGiaoDtChiNsnnExportService.exportToExcel(response, type, objReq);
		} catch (Exception e) {
			log.error("Error can not export", e);
		}

	}

}
