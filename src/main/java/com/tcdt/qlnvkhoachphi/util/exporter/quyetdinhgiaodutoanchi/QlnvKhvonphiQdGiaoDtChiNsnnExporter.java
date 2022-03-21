package com.tcdt.qlnvkhoachphi.util.exporter.quyetdinhgiaodutoanchi;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnn;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

public class QlnvKhvonphiQdGiaoDtChiNsnnExporter implements
	QdGiaoDtChiExportService{

	@Override
	public void export(XSSFWorkbook workbook, Page<QlnvKhvonphiQdGiaoDtChiNsnn> data) {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_DCHINH_SAU_QTOAN);

		writeHeaderLine(workbook, sheet, data);
		writeDataLines(sheet, workbook, data);
		
	}
	
	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet, Page<QlnvKhvonphiQdGiaoDtChiNsnn> data) {
		//STYLE
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(11);
		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);  
		Row row0 = sheet.createRow(0);

		//STT
		ExcelUtils.createCell(row0, 0, Constants.ExcelHeader.STT, style, sheet);
		ExcelUtils.createCell(row0, 1, "Số quyết định", style, sheet);
		ExcelUtils.createCell(row0, 2, "Ngày quyết định", style, sheet);
		ExcelUtils.createCell(row0, 3, "Nơi ra quyết định", style, sheet);
		ExcelUtils.createCell(row0, 4, "Về việc", style, sheet);
	}
	
	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, Page<QlnvKhvonphiQdGiaoDtChiNsnn> data) {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        Row row;
		int index = 1;
		for(QlnvKhvonphiQdGiaoDtChiNsnn item : data.getContent()) {
			row = sheet.createRow(index);
			ExcelUtils.createCell(row, 0, 1, style, sheet);
			ExcelUtils.createCell(row, 1, item.getSoQd(), style, sheet);
			ExcelUtils.createCell(row, 2, item.getNguoiTao(), style, sheet);
			ExcelUtils.createCell(row, 3, item.getNguoiTao(), style, sheet);
			ExcelUtils.createCell(row, 4, item.getMaDvi(), style, sheet);
			index++;
		}


//		for (KeHoachLuongThucDuTruRes line : data.get) {
//			row = sheet.createRow(startRowIndex++);
//			int colIndex = 0;
//			// stt
//			ExcelUtils.createCell(row, colIndex++, line.getStt(), style, sheet);
//
//		}
	}

}
