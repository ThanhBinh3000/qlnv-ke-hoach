package com.tcdt.qlnvkhoachphi.controller.dieuchinhdutoan;

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
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.TongHopDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDchinhDuToanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnExportService;
import com.tcdt.qlnvkhoachphi.service.dieuchinhdutoan.IQlnvKhvonphiDchinhDuToanChiNsnnService;
import com.tcdt.qlnvkhoachphi.util.PathConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathConstants.URL_DIEU_CHINH_DU_TOAN_CHI)
@Slf4j
@Api(tags = "Quản lý điều chỉnh dự toán chi")
public class QlnvKhvonphiDchinhDuToanChiNsnnController extends BaseController {

	@Autowired
	private IQlnvKhvonphiDchinhDuToanChiNsnnService qlnvKhvonphiDchinhDuToanChiNsnnService;

	@Autowired
	private QlnvKhvonphiDchinhDuToanChiNsnnExportService dchinhDuToanChiNsnnExportService;

	@ApiOperation(value = "Lấy chi tiết thông tin điều chỉnh", response = List.class)
	@GetMapping(value = PathConstants.URL_CHI_TIET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(@PathVariable("ids") Long id) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnnService.getDetail(id));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu thông tin điều chỉnh", response = List.class)
	@PostMapping(value = PathConstants.URL_DANH_SACH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvKhvonphiDchinhDuToanChiNsnnSearchReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnnService.getList(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tổng hợp thông tin điều chỉnh", response = List.class)
	@PostMapping(value = PathConstants.URL_TONG_HOP, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> synthesis(@Valid @RequestBody TongHopDchinhDuToanChiNsnnReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnnService.synthetic(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tạo mới thông tin điều chỉnh", response = List.class)
	@PostMapping(value = PathConstants.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> create(@Valid @RequestBody QlnvKhvonphiDchinhDuToanChiNsnnReq objReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnnService.add(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Sửa thông tin điều chỉnh", response = List.class)
	@PutMapping(value = "/cap-nhat", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resp> update(@Valid @RequestBody QlnvKhvonphiDchinhDuToanChiNsnnReq qlnvBaoCaoReq) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnnService.update(qlnvBaoCaoReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Xóa thông tin điều chỉnh", response = List.class)
	@GetMapping(value = PathConstants.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnnService.delete(ids));
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
			resp.setData(qlnvKhvonphiDchinhDuToanChiNsnnService.function(objReq));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Export chi tiết điều chỉnh dự toán chi ra excel", response = List.class)
	@PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void exportToExcel(HttpServletResponse response,
							  @RequestParam(required = false) List<String> type,
							  @RequestParam Long id) {

		try {
			response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=dieu-chinh-du-toan-chi_" + currentDateTime + ".xlsx";
			response.setHeader(headerKey, headerValue);
			dchinhDuToanChiNsnnExportService.exportToExcel(response, type, id);
		} catch (Exception e) {
			log.error("Error can not export", e);
		}
	}

}
