package com.tcdt.qlnvkhoachphi.service.baocao;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface QlnvKhvonphiBCaoExportService {
	Boolean exportToExcel (HttpServletResponse response, List<String> types, Long id) throws Exception;
}
