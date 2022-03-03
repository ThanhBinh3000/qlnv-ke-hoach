package com.tcdt.qlnvkhoachphi.util;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelUtils {
	public static MergeCellObj buildMergeCell(Row row, String value, int firstRow, int lastRow, int firstCol, int lastCol) {
		return MergeCellObj.builder()
				.firstRow(firstRow)
				.lastRow(lastRow)
				.firstCol(firstCol)
				.lastCol(lastCol)
				.value(value)
				.row(row)
				.build();
	}

	public static void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		cell.setCellStyle(style);
		if (value == null) {
			cell.setCellValue("");
			return;
		}
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
	}
}
