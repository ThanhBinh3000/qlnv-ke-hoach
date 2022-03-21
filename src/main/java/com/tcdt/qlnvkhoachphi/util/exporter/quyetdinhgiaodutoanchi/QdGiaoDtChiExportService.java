package com.tcdt.qlnvkhoachphi.util.exporter.quyetdinhgiaodutoanchi;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnn;

public interface QdGiaoDtChiExportService {
	void export(XSSFWorkbook workbook, Page<QlnvKhvonphiQdGiaoDtChiNsnn> data);
}
