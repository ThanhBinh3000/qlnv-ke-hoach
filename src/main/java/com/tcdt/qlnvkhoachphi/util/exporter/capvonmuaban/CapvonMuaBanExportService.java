package com.tcdt.qlnvkhoachphi.util.exporter.capvonmuaban;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tcdt.qlnvkhoachphi.response.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp;

public interface CapvonMuaBanExportService {
	
	void export(XSSFWorkbook workbook, QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp data);
	
}
