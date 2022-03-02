package com.tcdt.qlnvkhoachphi.controller;

import com.tcdt.qlnvkhoachphi.service.ChiTieuKeHoachNamExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	private ChiTieuKeHoachNamExportService chiTieuKeHoachNamExportSv;

	@ApiOperation(value = "Export excel", response = List.class)
	@GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)

	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		chiTieuKeHoachNamExportSv.exportToExcel(response);

	}
}