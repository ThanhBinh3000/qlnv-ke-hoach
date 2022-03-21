package com.tcdt.qlnvkhoachphi.util.exporter.quyettoanvonphi;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqg;

public interface DchinhSauQtoanExportService {
	void export(XSSFWorkbook workbook, Page<QlnvKhvonphiDchinhSauQtoanDtqg> data);
}
