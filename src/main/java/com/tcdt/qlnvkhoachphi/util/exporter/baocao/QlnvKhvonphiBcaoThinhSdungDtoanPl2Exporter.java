package com.tcdt.qlnvkhoachphi.util.exporter.baocao;

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
import org.springframework.stereotype.Component;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.baocao.QlnvKhvonphiBcaoResp;
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl2;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QlnvKhvonphiBcaoThinhSdungDtoanPl2Exporter implements BCaoExportService{
	@Override
	public void exportBCao(XSSFWorkbook workbook, QlnvKhvonphiBcaoResp data) {
		if (data.getLstCTietBCao() == null) {
			log.info("Không có data báo cáo kết quả giải ngân dự toán chi NSNN định kỳ tháng/năm");
			throw new IllegalArgumentException(
					"Không có data báo cáo kết quả giải ngân dự toán chi NSNN định kỳ tháng/năm");
		}
		XSSFSheet sheet = workbook.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_QLNV_KHVONPHI_BAO_CAO);

		writeHeaderLine(workbook, sheet, data);
		writeDataLines(sheet, workbook, data);
	}

	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet, QlnvKhvonphiBcaoResp data) {
		// STYLE
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
		Row row3 = sheet.createRow(3);
		Row row4 = sheet.createRow(4);
		Row row5 = sheet.createRow(5);

		List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

		// STT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 6, 0, 0));

		// Danh mục nội dung
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Đơn vị-Nội dung", 0, 6, 1, 1));

		// Dự toán được sử dụng trong năm
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Dự toán được sử dụng trong năm", 0, 2, 2, 5));
		    // Tổng cộng
		    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Tổng cộng", 3, 6, 2, 2));

		    // Trong đó
		    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Trong đó", 3, 3, 3, 5));

		        // Nguồn NSNN
		        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nguồn NSNN", 4, 6, 3, 3));

		        // Nguồn thu SN
		        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nguồn thu SN", 4, 6, 4, 4));

		        // Nguồn quỹ
		        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nguồn quỹ", 4, 6, 5, 5));

		  // Giải ngân tháng báo cáo
		  mergeCellHeaderRow.add(
			ExcelUtils.buildMergeCell(row0, "Giải ngân tháng báo cáo", 0, 2, 6, 13));

	  	    // Tổng cộng
		    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Tổng cộng", 3, 4, 6, 7));
		       // Cộng
		       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Cộng", 5, 6, 6, 6));

		       // Tỷ lệ
		       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Tỷ lệ", 5, 6, 7, 7));


	  	    // Trong đó
	  	    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Trong đó", 3, 3, 8, 13));

	  	        // Nguồn NSNN
	            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nguồn NSNN", 4, 4, 8, 9));
	                //Số tiền
	               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Số tiền", 5, 6, 8, 8));
	               // Tỷ lệ(%)
	               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Tỷ lệ(%)", 5, 6, 9, 9));

	            // Nguồn thu SN
	            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nguồn thu SN", 4, 4, 10, 11));
	               // Số tiền
	               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Số tiền", 5, 6, 10, 10));
	               // Tỷ lệ(%)
	               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Tỷ lệ(%)", 5, 6, 11, 11));

	            // Nguồn quỹ
	            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nguồn quỹ", 4, 4, 12, 13));
	               // Số tiền
	               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Số tiền", 5, 6, 12, 12));
	               // Tỷ lệ(%)
	               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Tỷ lệ(%)", 5, 6, 13, 13));

		// Lũy kế giải ngân từ đầu năm đến tháng báo cáo đối với báo cáo tháng(đến hết
		// thời gian chỉnh lý quyết toán ngân sách - ngày 31/1 năm sau đối với)
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0,
				"Lũy kế giải ngân từ đầu năm đến tháng báo cáo đối với báo cáo tháng(đến hết thời gian chỉnh lý quyết toán ngân sách - ngày 31/1 năm sau đối với",
				0, 2, 14, 21));
  	    // Tổng cộng
	    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Tổng cộng", 3, 4, 14, 15));
	       // Cộng
	       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Cộng", 5, 6, 14, 14));

	       // Tỷ lệ
	       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Tỷ lệ", 5, 6, 15, 15));


  	    // Trong đó
  	    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Trong đó", 3, 3, 16, 21));

  	        // Nguồn NSNN
            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nguồn NSNN", 4, 4, 16, 17));
               // Số tiền
               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Nguồn NSNN", 5, 6, 16, 16));
               // Tỷ lệ(%)
               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Nguồn NSNN", 5, 6, 17, 17));

            // Nguồn thu SN
            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nguồn thu SN", 4, 4, 18, 19));
               // Số tiền
               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Nguồn NSNN", 5, 6, 18, 18));
               // Tỷ lệ(%)
               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Nguồn NSNN", 5, 6, 19, 19));

            // Nguồn quỹ
            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nguồn quỹ", 4, 4, 20, 21));
               // Số tiền
               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Nguồn NSNN", 5, 6, 20, 20));
               // Tỷ lệ(%)
               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row5, "Nguồn NSNN", 5, 6, 21, 21));



		for (MergeCellObj mergeCellObj : mergeCellHeaderRow) {
			CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();
			sheet.addMergedRegion(cellRangeAddress);
			ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(),
					style, sheet);
		}
	}

	@SuppressWarnings("unchecked")
	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, QlnvKhvonphiBcaoResp data) {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);

		Row row;
		int startRowIndex = 7;
		ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl2> lstCTietBCao = (ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl2>) data
				.getLstCTietBCao();

		for (QlnvKhvonphiBcaoThinhSdungDtoanPl2 line : lstCTietBCao) {
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getMaNdung().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanSdungNamTcong().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanSdungNamNguonNsnn().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanSdungNamNguonSn().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanSdungNamNguonQuy().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangTcong().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangTcongTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangNguonNsnn().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangNguonNsnnTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangNguonSn().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangNguonSnTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangNguonQuy().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangNguonQuyTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganTcong().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganTcongTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonNsnn().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonNsnnTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonSn().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonSnTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonQuy().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonQuyTle().toString(), style, sheet);

		}
	}



}
