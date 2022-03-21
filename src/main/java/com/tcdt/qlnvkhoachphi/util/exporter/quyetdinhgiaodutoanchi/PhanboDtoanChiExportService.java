package com.tcdt.qlnvkhoachphi.util.exporter.quyetdinhgiaodutoanchi;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnn;

public interface PhanboDtoanChiExportService {
	void export(XSSFWorkbook workbook, Page<QlnvKhvonphiPhanboDtoanchiNsnn> data);
}
