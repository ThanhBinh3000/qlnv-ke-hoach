package com.tcdt.qlnvkhoachphi.util.exporter.baocao;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tcdt.qlnvkhoachphi.response.baocao.QlnvKhvonphiBcaoResp;

public interface BCaoExportService {
	void exportBCao(XSSFWorkbook workbook,QlnvKhvonphiBcaoResp data);
}
