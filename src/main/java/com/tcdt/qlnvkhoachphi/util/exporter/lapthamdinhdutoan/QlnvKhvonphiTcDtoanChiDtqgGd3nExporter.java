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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiTcDtoanChiDtqgGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmKhoachVonPhi;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;
@Service
public class QlnvKhvonphiTcDtoanChiDtqgGd3nExporter implements LapThamDinhDuToanExportService{
	@Autowired
	private QlnvDmService qlnvDmService;

	@Override
	public void export(XSSFWorkbook workbook, QlnvKhvonphiResp data) {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_TC_DTOAN_CHI_DTQG_GD3N);

		writeHeaderLine(workbook, sheet, data);
		writeDataLines(sheet, workbook, data);

	}

	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet, QlnvKhvonphiResp data) {
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
		Row row1 = sheet.createRow(1);

		List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

		// STT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 2, 0, 0));

		// Nội dung chi
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Nội dung chi", 0, 2, 1, 1));

		// Chi tiết
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Chi tiết", 0, 2, 2, 2));

		// Kế hoạch chi NSNN cho DTQG
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Kế hoạch chi NSNN cho DTQG", 0, 0, 3, 5));
		   // Năm N+1
		   mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Năm N+1", 1, 2, 3, 3));

		   // Năm N+2
		   mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Năm N+2", 1, 2, 4, 4));

		   // Năm N+3
		   mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Năm N+3", 1, 2, 5, 5));

		for (MergeCellObj mergeCellObj : mergeCellHeaderRow) {
			CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();
			sheet.addMergedRegion(cellRangeAddress);
			ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(), style, sheet);
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
		int startRowIndex = 3;

		ArrayList<QlnvKhvonphiTcDtoanChiDtqgGd3n> lstCTietBCao = (ArrayList<QlnvKhvonphiTcDtoanChiDtqgGd3n>) data.getLstCTietBCao();

		for (QlnvKhvonphiTcDtoanChiDtqgGd3n line : lstCTietBCao) {

			QlnvDmKhoachVonPhi dmNoiDungChi = new QlnvDmKhoachVonPhi();
			QlnvDmKhoachVonPhi dmChiTiet = new QlnvDmKhoachVonPhi();
			try {
				dmNoiDungChi = qlnvDmService
						.getDmKhoachVonPhiById(Long.parseLong(line.getMaNdungChi()));

				dmChiTiet = qlnvDmService
						.getDmKhoachVonPhiById(Long.parseLong(line.getChiTiet()));

			} catch (Exception e) {
				e.printStackTrace();
			}

			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, dmNoiDungChi.getTenDm().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, dmChiTiet.getTenDm().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKhoachChiNsnnN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKhoachChiNsnnN2().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKhoachChiNsnnN3().toString(), style, sheet);

		}
	}

}
