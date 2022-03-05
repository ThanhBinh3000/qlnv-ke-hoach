package com.tcdt.qlnvkhoachphi.controller;

import com.tcdt.qlnvkhoachphi.request.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.service.ChiTieuKeHoachNamExportService;
import com.tcdt.qlnvkhoachphi.service.ImportService;
import com.tcdt.qlnvkhoachphi.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/chi-tieu-ke-hoach-nam")
@Slf4j
@Api(tags = "Export chỉ tiêu kế hoạch năm")
public class ChiTieuKeHoachNamExportController extends BaseController {

	@Autowired
	private ImportService importSv;

	@Autowired
	private ChiTieuKeHoachNamExportService chiTieuKeHoachNamExportSv;

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

	@ApiOperation(value = "Export Chỉ tiêu kế hoạch năm ra excel", response = List.class)
	@PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void exportToExcel(HttpServletResponse response,
							  @RequestParam(required = false) List<String> type) throws IOException {

		try {
			response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=chi-tieu-ke-hoach-nam_" + currentDateTime + ".xlsx";
			response.setHeader(headerKey, headerValue);
			chiTieuKeHoachNamExportSv.exportToExcel(response, type);
		} catch (Exception e) {
			log.error("Error can not export", e);
		}

	}

	@ApiOperation(value = "Tra cứu chỉ tiêu kế hoạch năm", response = List.class)
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/search")
	public final ResponseEntity<Resp> search(SearchChiTieuKeHoachNamReq req, Pageable pageable) {
		Resp resp = new Resp();
		try {
			resp.setData(chiTieuKeHoachNamExportSv.search(req, pageable));
			resp.setStatusCode(Constants.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Constants.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error("Tra cứu chỉ tiêu kế hoạch năm lỗi ", e);
		}
		return ResponseEntity.ok(resp);
	}
}