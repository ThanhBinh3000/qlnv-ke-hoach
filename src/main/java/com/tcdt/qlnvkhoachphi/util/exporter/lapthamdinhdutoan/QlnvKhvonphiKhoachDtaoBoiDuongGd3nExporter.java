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
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiKhoachDtaoBoiDuongGd3n;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

@Service
public class QlnvKhvonphiKhoachDtaoBoiDuongGd3nExporter implements LapThamDinhDuToanExportService{
	@Autowired
	private QlnvDmService qlnvDmService;

	@Override
	public void export(XSSFWorkbook workbook, QlnvKhvonphiResp data) {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_KHOACH_DTAO_BOI_DUONG_GD3N);

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

		// Loại
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Tên vật tư hàng hóa", 0, 2, 1, 1));

		// Khoản
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Nhóm", 0, 2, 2, 2));

		// Chỉ mục
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Loại", 0, 2, 3, 3));

		// Năm N+1
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Năm N+1", 0, 0, 4, 5));
		   // Số lượng người
		   mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Số lượng người", 1, 2, 4, 4));

		   // Thành tiền
		   mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Thành tiền", 1, 2, 5, 5));

		// Năm N+2
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Năm N+2", 0, 0, 6, 7));
		   // Số lượng người
		   mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Số lượng người", 1, 2, 6, 6));

		   // Thành tiền
		   mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Thành tiền", 1, 2, 7, 7));

		// Năm N+3
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Năm N+3", 0, 0, 8, 9));
		   // Số lượng người
		   mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Số lượng người", 1, 2, 8, 8));

		   // Thành tiền
		   mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Thành tiền", 1, 2, 9, 9));





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

		ArrayList<QlnvKhvonphiKhoachDtaoBoiDuongGd3n> lstCTietBCao = (ArrayList<QlnvKhvonphiKhoachDtaoBoiDuongGd3n>) data.getLstCTietBCao();

		for (QlnvKhvonphiKhoachDtaoBoiDuongGd3n line : lstCTietBCao) {

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
//			ExcelUtils.createCell(row, colIndex++, line.getTenDan(), style, sheet);
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
