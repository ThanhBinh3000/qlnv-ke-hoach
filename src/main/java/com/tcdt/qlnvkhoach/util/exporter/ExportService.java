package com.tcdt.qlnvkhoach.util.exporter;

import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ExportService {
	void export(XSSFWorkbook workbook, ChiTieuKeHoachNamRes data);
}
