package com.tcdt.qlnvkhoachphi.service;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ChiTieuKeHoachNamExportServiceImpl implements ChiTieuKeHoachNamExportService {


	@Override
	public Boolean exportToExcel() {

		return true;
	}

	private List<KeHoachLuongThucDuTruRes> buildDataExport() {
		return new LinkedList<>();
	}



	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet) {
		sheet = workbook.createSheet("Users");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "User ID", style, sheet);
		createCell(row, 1, "E-mail", style, sheet);
		createCell(row, 2, "Full Name", style, sheet);
		createCell(row, 3, "Roles", style, sheet);
		createCell(row, 4, "Enabled", style, sheet);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

//	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook) {
//		int rowCount = 1;
//
//		CellStyle style = workbook.createCellStyle();
//		XSSFFont font = workbook.createFont();
//		font.setFontHeight(14);
//		style.setFont(font);
//
//		List<KeHoachLuongThucDuTruRes> data = this.buildDataExport();
//
//		for (KeHoachLuongThucDuTruRes line : data) {
//			Row row = sheet.createRow(rowCount++);
//			int columnCount = 0;
//
//			createCell(row, columnCount++, user.getId(), style);
//			createCell(row, columnCount++, user.getEmail(), style);
//			createCell(row, columnCount++, user.getFullName(), style);
//			createCell(row, columnCount++, user.getRoles().toString(), style);
//			createCell(row, columnCount++, user.isEnabled(), style);
//
//		}
//	}
//
//	public void export(HttpServletResponse response) throws IOException {
//		writeHeaderLine();
//		writeDataLines();
//
//		ServletOutputStream outputStream = response.getOutputStream();
//		workbook.write(outputStream);
//		workbook.close();
//
//		outputStream.close();
//
//	}
}
