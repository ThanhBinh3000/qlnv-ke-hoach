package com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiResp;

public interface LapThamDinhDuToanExportService {
	
	void export(XSSFWorkbook workbook, QlnvKhvonphiResp data) throws Exception;
	
}
