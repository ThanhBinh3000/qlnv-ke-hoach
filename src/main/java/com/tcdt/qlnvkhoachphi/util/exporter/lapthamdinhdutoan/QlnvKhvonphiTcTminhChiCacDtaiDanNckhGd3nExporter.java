package com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiResp;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiDmVondtXdcbgd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

@Service
public class QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3nExporter implements LapThamDinhDuToanExportService {

	@Override
	public void export(XSSFWorkbook workbook, QlnvKhvonphiResp data) {
		XSSFSheet sheet = workbook.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_NXUAT_DTQG_HNAM_VATTU);

		writeHeaderLine(workbook, sheet, data);
		writeDataLines(sheet, workbook, data);

	}

	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet, QlnvKhvonphiResp data) {
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
		Row row1 = sheet.createRow(1);

		List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

		// STT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 2, 0, 0));

		// Tên đề tài, dự án
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Tên đề tài, dự án nghiên cứu khoa học", 0, 2, 1, 1));

		// Đơn vị chủ trì thực hiện
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Đơn vị chủ trì thực hiện", 0, 2, 2, 2));

		// Thời gian
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Thời gian", 0, 0, 3, 4));

		// Bắt đầu
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Bắt đầu", 1, 2, 3, 3));

		// Kết thúc
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Kết thúc", 1, 2, 4, 4));

		// Kinh phí
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Kinh phí", 0, 0, 5, 10));

		// Tổng phí được duyệt
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng phí được duyệt", 1, 2, 5, 5));

		// Kinh phí đã được bố trí đến năm N-1
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Kinh phí đã được bố trí đến năm N-1", 1, 2, 6, 6));

		// Kinh phí được thực hiện đến thời điểm báo cáo
		mergeCellHeaderRow
				.add(ExcelUtils.buildMergeCell(row1, "Kinh phí được thực hiện đến thời điểm báo cáo", 1, 2, 7, 7));

		// Dự kiến bố trí kinh phí năm N+1
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Dự kiến bố trí kinh phí năm N+1", 1, 2, 8, 8));

		// Dự kiến bố trí kinh phí năm N+2
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Dự kiến bố trí kinh phí năm N+2", 1, 2, 9, 9));

		// Dự kiến bố trí kinh phí năm N+3
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Dự kiến bố trí kinh phí năm N+3", 1, 2, 10, 10));

		// Kinh phi thu hoi
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Kinh phi thu hoi", 0, 2, 11, 11));

		// Thời gian thu hồi
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Thời gian thu hồi", 0, 2, 12, 12));

		for (MergeCellObj mergeCellObj : mergeCellHeaderRow) {
			CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();
			sheet.addMergedRegion(cellRangeAddress);
			ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(),
					style, sheet);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, QlnvKhvonphiResp data) {
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        
        Row row;
		int startRowIndex = 5;
		
		ArrayList<QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n> lstCTietBCao = (ArrayList<QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n>) data.getLstCTietBCao();
		
		for(QlnvKhvonphiTcTminhChiCacDtaiDanNckhGd3n line: lstCTietBCao) {
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			
			ExcelUtils.createCell(row, colIndex++, line.getStt(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTenDtaiDanNckh().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getMaDvi().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTgianBdau().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTgianKthuc().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiTongPhiDuocDuyet().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiDkienBtriN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiDaDuocThienDenTdiemBcao().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiDkienBtriN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiDkienBtriN2().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiDkienBtriN3().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiThuHoi().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTgianThuHoi().toString(), style, sheet);
			
			
		}
	}

}
