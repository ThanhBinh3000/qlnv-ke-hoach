package com.tcdt.qlnvkhoachphi.util.exporter.capvonmuaban;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgExporter implements CapvonMuaBanExportService {

	@Override
	public void export(XSSFWorkbook workbook, QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp data) {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_DM_VONDT_XDCBGD3N);

		writeHeaderLine(workbook, sheet, data);
		writeDataLines(sheet, workbook, data);
	}

	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet, QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp data) {
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


	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp data) {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

		Row row;
		row = sheet.createRow(1);
		
		ExcelUtils.createCell(row, 0, 1, style, sheet);
		ExcelUtils.createCell(row, 1, data.getSoQd(), style, sheet);
		ExcelUtils.createCell(row, 2, data.getNgayQuyetDinh(), style, sheet);
		ExcelUtils.createCell(row, 3, "", style, sheet);
		ExcelUtils.createCell(row, 4, "", style, sheet);


//		for (KeHoachLuongThucDuTruRes line : data.get) {
//			row = sheet.createRow(startRowIndex++);
//			int colIndex = 0;
//			// stt
//			ExcelUtils.createCell(row, colIndex++, line.getStt(), style, sheet);
//
//		}
	}
}
