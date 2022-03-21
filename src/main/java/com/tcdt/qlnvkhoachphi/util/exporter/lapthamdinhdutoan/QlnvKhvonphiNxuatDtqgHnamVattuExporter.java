package com.tcdt.qlnvkhoachphi.util.exporter.lapthamdinhdutoan;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvKhvonphiNxuatDtqgHnamVattuEntity;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmKhoachVonPhi;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiNxuatDtqgHnamVattu;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiNxuatDtqgHnamVattuExporter implements LapThamDinhDuToanExportService {

	@Autowired
	private QlnvDmService qlnvDmService;
	
	@Override
	public void export(XSSFWorkbook workbook, QlnvKhvonphiResp data) throws Exception {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_NXUAT_DTQG_HNAM_VATTU);

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

		//STT
		ExcelUtils.createCell(row0, 0, Constants.ExcelHeader.STT, style, sheet);
		ExcelUtils.createCell(row0, 1, "Tên thiết bị", style, sheet);
		ExcelUtils.createCell(row0, 2, "Số lượng nhập", style, sheet);
	}


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
		int startRowIndex = 1;
		
		QlnvKhvonphiNxuatDtqgHnamVattuEntity cTietBCao = (QlnvKhvonphiNxuatDtqgHnamVattuEntity) data.getLstCTietBCao();
		ArrayList<QlnvKhvonphiNxuatDtqgHnamVattu> lstCTietBCao = (ArrayList<QlnvKhvonphiNxuatDtqgHnamVattu>) cTietBCao.getLstCTiet();
		
		for (QlnvKhvonphiNxuatDtqgHnamVattu line : lstCTietBCao) {
			
			QlnvDmKhoachVonPhi dmLoaiThietBi = new QlnvDmKhoachVonPhi();
			try {
				dmLoaiThietBi = qlnvDmService
						.getDmKhoachVonPhiById(line.getMaTbi());
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Id QlnvDmKhoachVonPhi không đúng");
			}
			
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, dmLoaiThietBi.getTenDm(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getSlNhap().toString(), style, sheet);

		}
	}
}
