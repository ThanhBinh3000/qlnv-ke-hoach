package com.tcdt.qlnvkhoachphi.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ChiTieuKeHoachNamExportService {
	Boolean exportToExcel (HttpServletResponse response, List<String> type, Long id) throws Exception;
}
