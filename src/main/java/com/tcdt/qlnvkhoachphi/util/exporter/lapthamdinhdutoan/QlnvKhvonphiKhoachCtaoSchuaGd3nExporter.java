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
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiKhoachCtaoSchuaGd3n;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiNcauPhiNhapXuatGd3n;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

@Service
public class QlnvKhvonphiKhoachCtaoSchuaGd3nExporter implements LapThamDinhDuToanExportService{
	@Autowired
	private QlnvDmService qlnvDmService;

	@Override
	public void export(XSSFWorkbook workbook, QlnvKhvonphiResp data) {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_KHOACH_CTAO_SCHUA_GD3N);

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
		Row row2 = sheet.createRow(2);
		Row row3 = sheet.createRow(3);
		Row row4 = sheet.createRow(4);
		Row row5 = sheet.createRow(5);


		List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

		//STT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 5, 0, 0));

		//Tên công trình
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Tên công trình", 0, 5, 1, 1));

		//Nguồn vốn
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Nguồn vốn", 0, 5, 2, 2));

		//Loại CT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Loại CT", 0, 5, 3, 3));

		//Thời gian KC-HT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Thời gian KC-HT", 0, 0, 4, 5));
		     //KC
		     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "KC", 1, 5, 4, 4));

			 //HT
			 mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "HT", 1, 5, 5, 5));

		//Dự toán được duyệt
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Dự toán được duyệt", 0, 0, 6, 7));
		     //Cơ quan QĐ
		     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Cơ quan QĐ", 1, 5, 6, 6));

		     //Tổng giá trị
		     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng giá trị", 1, 5, 7, 7));

		//Thành hiện KL năm N
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Thành hiện KL năm N", 0, 0, 8, 9));
		     //Dự toán kinh phí được sử dụng đến ngày 30/06/N
			 mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Dự toán kinh phí được sử dụng đến ngày 30/06/N", 1, 5, 8, 8));

			 //Ước thực hiện cả năm N
			 mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Ước thực hiện cả năm N", 1, 5, 9, 9));

		//Thành toán năm N
	    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Thành toán năm N", 0, 0, 10, 11));
		     //Đã TT từ KC đến 30/06/N
	         mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Đã TT từ KC đến 30/06/N", 1, 5, 10, 10));

		     //Ước thanh toán cả năm N
	          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Ước thanh toán cả năm N", 1, 5, 11, 11));

	    //Kế hoạch năm N+1
	    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Kế hoạch năm N+1", 0, 0, 12, 14));
	         //Tổng số
	         mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số", 1, 5, 12, 12));

	         //Chia ra
	         mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Chia ra", 1, 1, 13, 14));

	            //Thanh toán khối lượng năm trước chuyển sang
	            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Thanh toán khối lượng năm trước chuyển sang", 2, 5, 13, 13));

	            //Phát sinh mới trong năm
	            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Phát sinh mới trong năm", 2, 5, 14, 14));

	    //Kế hoạch năm N+2
	    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Kế hoạch năm N+2", 0, 0, 15, 17));

	         //Tổng số
             mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số", 1, 5, 15, 15));

             //Chia ra
             mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Chia ra", 1, 1, 16, 17));

               //Thanh toán khối lượng năm trước chuyển sang
               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Thanh toán khối lượng năm trước chuyển sang", 2, 5, 16, 16));

               //Phát sinh mới trong năm
               mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Phát sinh mới trong năm", 2, 5, 17, 17));

	    //Kế hoạch năm N+3
	    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Kế hoạch năm N+3", 0, 0, 18, 20));

           //Tổng số
           mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số", 1, 5, 18, 18));

           //Chia ra
           mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Chia ra", 1, 1, 19, 20));

              //Thanh toán khối lượng năm trước chuyển sang
              mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Thanh toán khối lượng năm trước chuyển sang", 2, 5, 19, 19));

              //Phát sinh mới trong năm
              mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Phát sinh mới trong năm", 2, 5, 20, 20));


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
		int startRowIndex = 2;

		ArrayList<QlnvKhvonphiKhoachCtaoSchuaGd3n> lstCTietBCao = (ArrayList<QlnvKhvonphiKhoachCtaoSchuaGd3n>) data.getLstCTietBCao();

		for (QlnvKhvonphiKhoachCtaoSchuaGd3n line : lstCTietBCao) {

//			QlnvDmKhoachVonPhi dmLoaiKeHoach = new QlnvDmKhoachVonPhi();
//			QlnvDmKhoachVonPhi dmLoaiKhoiDan = new QlnvDmKhoachVonPhi();
//			QlnvDmKhoachVonPhi dmLoaiMaDdiemXd = new QlnvDmKhoachVonPhi();
//			QlnvDmKhoachVonPhi dmLoaiMaNganhKte = new QlnvDmKhoachVonPhi();
//			try {
//				dmLoaiKeHoach = qlnvDmService
//						.getDmKhoachVonPhiById(line.getMaKhoach());
//
//				dmLoaiKhoiDan = qlnvDmService
//						.getDmKhoachVonPhiById(line.getMaKhoiDan());
//
//				dmLoaiMaDdiemXd = qlnvDmService
//						.getDmKhoachVonPhiById(line.getMaDdiemXd());
//
//				dmLoaiMaNganhKte = qlnvDmService
//						.getDmKhoachVonPhiById(line.getMaNganhKte());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTenCtrinh(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, dmLoaiKeHoach.getTenDm(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, dmLoaiKhoiDan.getTenDm(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, dmLoaiMaDdiemXd.getTenDm(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getDdiemMoTaikhoan(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getMasoDan(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, dmLoaiMaNganhKte.getTenDm(), style, sheet);
//
//			ExcelUtils.createCell(row, colIndex++, line.getNlucTke(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getNamKcTte().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getNamHtTte().toString(), style, sheet);
//
//			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetDanDtuSongaythang(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetDanDtuTongVon().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getQdDchinhDanDtuSongaythang(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getQdDchinhDanDtuTongVon().toString(), style, sheet);
//
//			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanSongaythang(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanTong().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanXl().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanTb().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanCk().toString(), style, sheet);
//
//			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3006Songaythang(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3006Nstt().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3006DtoanChiTx().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3006Quykhac().toString(), style, sheet);
//
//			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3112Songaythang(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3112Nstt().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3112DtoanChiTx().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3112Quykhac().toString(), style, sheet);
//
//			ExcelUtils.createCell(row, colIndex++, line.getNcauVonN1().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getNcauVonN2().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getNcauVonN3().toString(), style, sheet);
//			ExcelUtils.createCell(row, colIndex++, line.getGhiChu(), style, sheet);
		}
	}


}
