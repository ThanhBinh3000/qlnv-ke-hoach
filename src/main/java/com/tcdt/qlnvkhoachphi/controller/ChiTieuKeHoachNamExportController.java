package com.tcdt.qlnvkhoachphi.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/chi-tieu-ke-hoach-nam")
@Slf4j
@Api(tags = "Export chỉ tiêu kế hoạch năm")
public class ChiTieuKeHoachNamExportController extends BaseController {

//	@Autowired
//	private ChiTieuKeHoachNamExportService chiTieuKeHoachNamExportSv;
//
//	@ApiOperation(value = "Export excel", response = List.class)
//	@GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseStatus(HttpStatus.OK)
//
//	public void exportToExcel(HttpServletResponse response) throws IOException {
//		response.setContentType("application/octet-stream");
//		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//		String currentDateTime = dateFormatter.format(new Date());
//
//		String headerKey = "Content-Disposition";
//		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
//		response.setHeader(headerKey, headerValue);
//
//		List<User> listUsers = service.listAll();
//
//		UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
//
//		excelExporter.export(response);
//	}
}