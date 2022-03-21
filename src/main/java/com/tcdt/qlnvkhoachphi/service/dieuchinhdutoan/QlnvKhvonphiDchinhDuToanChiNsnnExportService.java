package com.tcdt.qlnvkhoachphi.service.dieuchinhdutoan;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface QlnvKhvonphiDchinhDuToanChiNsnnExportService{
	Boolean exportToExcel (HttpServletResponse response, List<String> types, Long id) throws Exception;
}
