package com.tcdt.qlnvkhoachphi.util.exporter.quyettoanvonphi;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqg;

public interface QtoanDtqgExportService {
	void export(XSSFWorkbook workbook, Page<QlnvKhvonphiQtoanDtqg> data);
}
