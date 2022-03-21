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
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl1;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QlnvKhvonphiBcaoThinhSdungDtoanPl1Exporter implements BCaoExportService {
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
		Row row2 = sheet.createRow(2);
		Row row3 = sheet.createRow(3);
		Row row6 = sheet.createRow(6);
		List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

		// STT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 7, 0, 0));

		// Đơn vị-Nội dung
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Đơn vị-Nội dung", 0, 7, 1, 1));

		// Kinh phí được sử dụng năm
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Kinh phí được sử dụng năm", 0, 1, 2, 7));
		    // Tổng cộng
		    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng cộng", 2, 6, 2, 2));

		    // Trong đó
		    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó", 2, 2, 3, 7));

		        // Dự toán NSNN
		        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Dự toán NSNN", 3, 7, 3, 3));

		        // Nguồn khác
		        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn khác", 3, 7, 4, 4));

		        // Nguồn quỹ
		        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn quỹ", 3, 7, 5, 5));

		        // Vốn NSTT
		        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn NSTT", 3, 7, 6, 6));

		        // Vốn CK
		        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn CK", 3, 7, 7, 7));


		  // Nguồn kinh phí năm trước được chuyển sang năm-sử dụng
		  mergeCellHeaderRow.add(
			ExcelUtils.buildMergeCell(row0, "Nguồn kinh phí năm trước được chuyển sang năm-sử dụng", 0, 1, 8, 13));
	  	    // Tổng cộng
				    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng cộng", 2, 7, 8, 8));

	  	    // Trong đó
	  	    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó", 2, 2, 9, 13));

	  	        // Dự toán NSNN
	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Dự toán NSNN", 3, 7, 9, 9));

	  	        // Nguồn khác
	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn khác", 3, 7, 10, 10));

	  	        // Nguồn quỹ
	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn quỹ", 3, 7, 11, 11));

	  	        // Vốn NSTT
	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn NSTT", 3, 7, 12, 12));

	  	        // Vốn CK
	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn CK", 3, 7, 13, 13));


	  	  // Dự toán giao,phê duyệt sử dụng nguồn kinh phí năm
	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Dự toán giao,phê duyệt sử dụng nguồn kinh phí năm", 0, 1, 14, 19));
	  	  	    // Tổng cộng
	  				    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng cộng", 2, 7, 14, 14));

	  	  	    // Trong đó
	  	  	    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó", 2, 2, 15, 19));

	  	  	        // Dự toán NSNN
	  	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Dự toán NSNN", 3, 7, 15, 15));

	  	  	        // Nguồn khác
	  	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn khác", 3, 7, 16, 16));

	  	  	        // Nguồn quỹ
	  	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn quỹ", 3, 7, 17, 17));

	  	  	        // Vốn NSTT
	  	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn NSTT", 3, 7, 18, 18));

	  	  	        // Vốn CK
	  	  	        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn CK", 3, 7, 19, 19));

	  	  //Giải ngân tháng báo cáo
	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Giải ngân tháng báo cáo", 0, 1, 20, 31));
	  		 // Tổng cộng
	  		  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng cộng", 2, 5, 20, 21));
	  		          // Cộng
	  		          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Cộng", 6, 7, 20, 20));

	  		          // Tỷ lệ
	  		          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Tỷ lệ", 6, 7, 21, 21));
	  		  	 // Trong đó
	  		 	 mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó", 2, 2, 22, 31));

	  			     // Dự toán NSNN
	  		  	     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Dự toán NSNN", 3, 5, 22, 23));
	  		  	        // Số tiền
	  		            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Số tiền", 6, 7, 22, 22));

	  		            // Tỷ lệ
	  		             mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Tỷ lệ", 6, 7, 23, 23));

	  		 	     // Nguồn khác
	  		  	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn khác", 3, 5, 24, 25));
	  		  	        // Số tiền
	  		            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Số tiền", 6, 7, 24, 24));

	  		            // Tỷ lệ
	  		             mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Tỷ lệ", 6, 7, 25, 25));

	  		  	  	 // Nguồn quỹ
	  		  	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn quỹ", 3, 5, 26, 27));
	  		  	        // Số tiền
	  		            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Số tiền", 6, 7, 26, 26));

	  		            // Tỷ lệ
	  		             mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Tỷ lệ", 6, 7, 27, 27));


	  		  	  	 // Vốn NSTT
	  		  	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn NSTT", 3, 5, 28, 29));
	  		  	        // Số tiền
	  		            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Số tiền", 6, 7, 28, 28));

	  		            // Tỷ lệ
	  		             mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Tỷ lệ", 6, 7, 29, 29));

	  		  	  	 // Vốn CK
	  		  	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn CK", 3, 5, 30, 31));
	  		  	        // Số tiền
	  		            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Số tiền", 6, 7, 30, 30));

	  		            // Tỷ lệ
	  		             mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row6, "Tỷ lệ", 6, 7, 31, 31));

//		// Lũy kế giải ngân từ đầu năm đến tháng báo cáo đối với báo cáo tháng(đến hết
//		// thời gian chỉnh lý quyết toán ngân sách - ngày 31/1 năm sau đối với)
//		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0,
//				"Lũy kế giải ngân từ đầu năm đến tháng báo cáo đối với báo cáo tháng(đến hết thời gian chỉnh lý quyết toán ngân sách - ngày 31/1 năm sau đối với",
//				0, 1, 26, 31));
//		 // Tổng cộng
//		  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng cộng", 2, 6, 26, 26));
//
//		  	 // Trong đó
//		 	 mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó", 2, 2, 27, 31));
//
//			     // Dự toán NSNN
//		  	     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Dự toán NSNN", 3, 6, 27, 27));
//
//		 	     // Nguồn khác
//		  	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn khác", 3, 6, 28, 28));
//
//		  	  	 // Nguồn quỹ
//		  	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Nguồn quỹ", 3, 6, 29, 29));
//
//		  	  	 // Vốn NSTT
//		  	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn NSTT", 3, 6, 30, 30));
//
//		  		 // Vốn CK
//  		  	  	  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Vốn CK", 3, 6, 31, 31));



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
		int startRowIndex = 8;
		ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl1> lstCTietBCao = (ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl1>) data
				.getLstCTietBCao();

		for (QlnvKhvonphiBcaoThinhSdungDtoanPl1 line : lstCTietBCao) {
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getMaNdung().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiSdungTcong().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiSdungDtoan().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiSdungNguonKhac().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiSdungNguonQuy().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiSdungNstt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiSdungCk().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiChuyenSangTcong().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiChuyenSangDtoan().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiChuyenSangNguonKhac().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiChuyenSangNstt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKphiChuyenSangCk().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanGiaoTcong().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanGiaoDtoan().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanGiaoNguonKhac().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanGiaoNguonQuy().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanGiaoNstt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDtoanGiaoCk().toString(), style, sheet);

			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoTcong().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoDtoan().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoDtoanTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoNguonKhac().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoNguonKhacTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoNguonQuy().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoNguonQuyTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoNstt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoNsttTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoCk().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGiaiNganThangBcaoCkTle().toString(), style, sheet);

			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganTcong().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganTcongTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganDtoan().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganDtoanTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonKhac().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonKhacTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonQuy().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNguonQuyTle().toString(), style, sheet);

			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNstt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganNsttTle().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganCk().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganCkTle().toString(), style, sheet);

		}
	}

}
