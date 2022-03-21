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
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmKhoachVonPhi;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiDmVondtXdcbgd3n;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

@Service
public class QlnvKhvonphiDmVondtXdcbgd3nExporter implements LapThamDinhDuToanExportService {
	
	@Autowired
	private QlnvDmService qlnvDmService;

	@Override
	public void export(XSSFWorkbook workbook, QlnvKhvonphiResp data) throws Exception {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_DM_VONDT_XDCBGD3N);

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

		List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

		//STT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 4, 0, 0));

		//Tên dự án
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Tên dự án", 0, 4, 1, 1));
		
		//Loại kế hoạch
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Loại kế hoạch", 0, 4, 2, 2));
		
		//Khối dự án
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Khối dự án", 0, 4, 3, 3));
		
		//Địa điểm xây dựng
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Địa điểm xây dựng", 0, 4, 4, 4));
		
		//Địa điểm mở tài khoản
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Địa điểm xây dựng", 0, 4, 5, 5));
		
		//Mã số dự án
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Mã số dự án", 0, 4, 6, 6));
		
		//Mã ngành kinh tế (Loại khoản)
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Mã ngành kinh tế (Loại khoản)", 0, 4, 7, 7));
		
		//Năng lực thiết kế
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Năng lực thiết kế", 0, 4, 8, 8));
		
		//Năm KC - HT thực tế
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Năm KC - HT thực tế", 0, 0, 9, 10));
		//ExcelUtils.buildMergeCell(row, value, firstRow, lastRow, firstCol, lastCol)
			//KC
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "KC", 1, 4, 9, 9));
			//HT
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "HT", 1, 4, 10, 10));
		
		//QD duyệt dự án đầu tư
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "QD duyệt dự án đầu tư", 0, 0, 11, 12));
			//CQ duyệt, số, ngày tháng
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "CQ duyệt, số, ngày tháng", 1, 4, 11, 11));
			//Tổng vốn đầu tư
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng vốn đầu tư", 1, 4, 12, 12));
		
		//QD điều chỉnh DA (Lần cuối)
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "QD điều chỉnh DA (Lần cuối)", 0, 0, 13, 14));
			//CQ duyệt, số, ngày tháng
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "CQ duyệt, số, ngày tháng", 1, 4, 13, 13));
			//Tổng vốn đầu tư
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng vốn đầu tư", 1, 4, 14, 14));
		
		//Quyết định duyệt thiết kế dự án
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Quyết định duyệt thiết kế dự án", 0, 0, 15, 19));
			//CQ duyệt, số, ngày tháng
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "CQ duyệt, số, ngày tháng", 1, 4, 15, 15));
			//Tổng
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng", 1, 4, 16, 16));
			//Trong đó
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Trong đó", 1, 1, 17, 19));
				//XL
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "XL", 2, 4, 17, 17));
				//TB
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "TB", 2, 4, 18, 18));
				//CK
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "CK", 2, 4, 19, 19));
		
		//KL TH, cấp phát đến 30/06 năm thực hiện
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "KL TH, cấp phát đến 30/06 năm thực hiện", 0, 0, 20, 23));
			//CQ duyệt, số, ngày tháng
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "CQ duyệt, số, ngày tháng", 1, 4, 20, 20));
			//Trong đó
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Trong đó", 1, 1, 21, 23));
				//NSTT
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "NSTT", 2, 4, 21, 21));
				//Khác
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Khác", 2, 2, 22, 23));
					//DT chi TX
					mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "DT chi TX", 3, 4, 22, 22));
					//Quỹ khác
					mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Quỹ khác", 3, 4, 23, 23));
		
		//KL TH, cấp phát đến 31/12 năm thực hiện
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "KL TH, cấp phát đến 31/12 năm thực hiện", 0, 0, 24, 27));
			//CQ duyệt, số, ngày tháng
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "CQ duyệt, số, ngày tháng", 1, 4, 24, 24));
			//Trong đó
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Trong đó", 1, 1, 25, 27));
				//NSTT
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "NSTT", 2, 4, 25, 25));
				//Khác
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Khác", 2, 2, 26, 27));
					//DT chi TX
					mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "DT chi TX", 3, 4, 26, 26));
					//Quỹ khác
					mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, "Quỹ khác", 3, 4, 27, 27));
		
		//Nhu cầu vốn năm N+1
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Nhu cầu vốn năm N+1", 0, 4, 28, 28));
		
		//Nhu cầu vốn năm N+2
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Nhu cầu vốn năm N+2", 0, 4, 29, 29));
		
		//Nhu cầu vốn năm N+3
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Nhu cầu vốn năm N+3", 0, 4, 30, 30));
		
		//Ghi chú: Ghi rõ công trình thuộc loại hoàn thành hay dự kiến hoàn thành
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Ghi chú: Ghi rõ công trình thuộc loại hoàn thành hay dự kiến hoàn thành", 0, 4, 31, 31));
		
		for (MergeCellObj mergeCellObj : mergeCellHeaderRow) {
			CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();
			sheet.addMergedRegion(cellRangeAddress);
			ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(), style, sheet);
		}
	}


	@SuppressWarnings("unchecked")
	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, QlnvKhvonphiResp data) throws Exception {

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
		
		ArrayList<QlnvKhvonphiDmVondtXdcbgd3n> lstCTietBCao = (ArrayList<QlnvKhvonphiDmVondtXdcbgd3n>) data.getLstCTietBCao();
		
		for (QlnvKhvonphiDmVondtXdcbgd3n line : lstCTietBCao) {
			
			QlnvDmKhoachVonPhi dmLoaiKeHoach = new QlnvDmKhoachVonPhi();
			QlnvDmKhoachVonPhi dmLoaiKhoiDan = new QlnvDmKhoachVonPhi();
			QlnvDmKhoachVonPhi dmLoaiMaDdiemXd = new QlnvDmKhoachVonPhi();
			QlnvDmKhoachVonPhi dmLoaiMaNganhKte = new QlnvDmKhoachVonPhi();
			try {
				dmLoaiKeHoach = qlnvDmService
						.getDmKhoachVonPhiById(line.getMaKhoach());
				
				dmLoaiKhoiDan = qlnvDmService
						.getDmKhoachVonPhiById(line.getMaKhoiDan());
				
				dmLoaiMaDdiemXd = qlnvDmService
						.getDmKhoachVonPhiById(line.getMaDdiemXd());
				
				dmLoaiMaNganhKte = qlnvDmService
						.getDmKhoachVonPhiById(line.getMaNganhKte());
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Id QlnvDmKhoachVonPhi không đúng");
			}
			
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTenDan(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, dmLoaiKeHoach.getTenDm(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, dmLoaiKhoiDan.getTenDm(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, dmLoaiMaDdiemXd.getTenDm(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getDdiemMoTaikhoan(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getMasoDan(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, dmLoaiMaNganhKte.getTenDm(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getNlucTke(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getNamKcTte().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getNamHtTte().toString(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetDanDtuSongaythang(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetDanDtuTongVon().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getQdDchinhDanDtuSongaythang(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getQdDchinhDanDtuTongVon().toString(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanSongaythang(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanTong().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanXl().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanTb().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getQdDuyetTkDtoanCk().toString(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3006Songaythang(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3006Nstt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3006DtoanChiTx().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3006Quykhac().toString(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3112Songaythang(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3112Nstt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3112DtoanChiTx().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getKlthCapDen3112Quykhac().toString(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getNcauVonN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getNcauVonN2().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getNcauVonN3().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getGhiChu(), style, sheet);
		}
	}
}
