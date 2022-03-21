package com.tcdt.qlnvkhoachphi.util.exporter.dieuchinhdutoan;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tcdt.qlnvkhoachphi.response.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnResp;

public interface DchinhDuToanChiExportService {
	void exportDchinhDuToanChi(XSSFWorkbook workbook, QlnvKhvonphiDchinhDuToanChiNsnnResp data);
}
