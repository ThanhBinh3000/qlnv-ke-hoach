package com.tcdt.qlnvkhoachphi.util.exporter;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ExportService {
	void export(XSSFWorkbook workbook, ChiTieuKeHoachNamRes data);
}
