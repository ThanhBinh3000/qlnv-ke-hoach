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
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiKhoachQuyTienLuongGd3n;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

@Service
public class QlnvKhvonphiKhoachQuyTienLuongGd3nExporter implements LapThamDinhDuToanExportService {
	
	@Autowired
	private QlnvDmService qlnvDmService;

	@Override
	public void export(XSSFWorkbook workbook, QlnvKhvonphiResp data) throws Exception {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_KHOACH_QUY_TIEN_LUONG_GD3N);

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

		//Tên đơn vị
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Tên đơn vị", 0, 4, 1, 1));
		
		//Dự toán năm N
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Dự toán năm N", 0, 0, 2, 8));
			//Tổng số cán bộ công nhân viên
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số cán bộ công nhân viên", 1, 4, 2, 2));
			//Tổng số biên chế được phê duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số biên chế được phê duyệt", 1, 4, 3, 3));
			//Tổng quỹ lương có tính chất lương
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương", 1, 4, 4, 4));
			//Tổng quỹ lương có tính chất lương theo biên chế được duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương theo biên chế được duyệt", 1, 4, 5, 5));
			//Trong đó
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Trong đó", 1, 1, 6, 8));
				//Lương cơ bản
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Lương cơ bản", 2, 4, 6, 6));
				//Phụ cấp lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Phụ cấp lương", 2, 4, 7, 7));
				//Các khoản đóng góp theo lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Các khoản đóng góp theo lương", 2, 4, 8, 8));
		//Ước thực hiện năm N
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Dự toán năm N", 0, 0, 9, 15));
			//Tổng số cán bộ công nhân viên
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số cán bộ công nhân viên", 1, 4, 9, 9));
			//Tổng số biên chế được phê duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số biên chế được phê duyệt", 1, 4, 10, 10));
			//Tổng quỹ lương có tính chất lương
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương", 1, 4, 11, 11));
			//Tổng quỹ lương có tính chất lương theo biên chế được duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương theo biên chế được duyệt", 1, 4, 12, 12));
			//Trong đó
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Trong đó", 1, 1, 13, 15));
				//Lương cơ bản
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Lương cơ bản", 2, 4, 13, 13));
				//Phụ cấp lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Phụ cấp lương", 2, 4, 14, 14));
				//Các khoản đóng góp theo lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Các khoản đóng góp theo lương", 2, 4, 15, 15));
		//Kế hoạch quỹ lương năm N+1
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Dự toán năm N", 0, 0, 16, 22));
			//Tổng số cán bộ công nhân viên
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số cán bộ công nhân viên", 1, 4, 16, 16));
			//Tổng số biên chế được phê duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số biên chế được phê duyệt", 1, 4, 17, 17));
			//Tổng quỹ lương có tính chất lương
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương", 1, 4, 18, 18));
			//Tổng quỹ lương có tính chất lương theo biên chế được duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương theo biên chế được duyệt", 1, 4, 19, 19));
			//Trong đó
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Trong đó", 1, 1, 20, 22));
				//Lương cơ bản
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Lương cơ bản", 2, 4, 20, 20));
				//Phụ cấp lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Phụ cấp lương", 2, 4, 21, 21));
				//Các khoản đóng góp theo lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Các khoản đóng góp theo lương", 2, 4, 22, 22));
		//Kế hoạch quỹ lương năm N+2
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Dự toán năm N", 0, 0, 23, 29));
			//Tổng số cán bộ công nhân viên
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số cán bộ công nhân viên", 1, 4, 23, 23));
			//Tổng số biên chế được phê duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số biên chế được phê duyệt", 1, 4, 24, 24));
			//Tổng quỹ lương có tính chất lương
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương", 1, 4, 25, 25));
			//Tổng quỹ lương có tính chất lương theo biên chế được duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương theo biên chế được duyệt", 1, 4, 26, 26));
			//Trong đó
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Trong đó", 1, 1, 27, 29));
				//Lương cơ bản
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Lương cơ bản", 2, 4, 27, 27));
				//Phụ cấp lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Phụ cấp lương", 2, 4, 28, 28));
				//Các khoản đóng góp theo lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Các khoản đóng góp theo lương", 2, 4, 29, 29));
		//Kế hoạch quỹ lương năm N+3
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Dự toán năm N", 0, 0, 30, 36));
			//Tổng số cán bộ công nhân viên
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số cán bộ công nhân viên", 1, 4, 30, 30));
			//Tổng số biên chế được phê duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng số biên chế được phê duyệt", 1, 4, 31, 31));
			//Tổng quỹ lương có tính chất lương
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương", 1, 4, 32, 32));
			//Tổng quỹ lương có tính chất lương theo biên chế được duyệt
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tổng quỹ lương có tính chất lương theo biên chế được duyệt", 1, 4, 33, 33));
			//Trong đó
			mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Trong đó", 1, 1, 34, 36));
				//Lương cơ bản
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Lương cơ bản", 2, 4, 34, 34));
				//Phụ cấp lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Phụ cấp lương", 2, 4, 35, 35));
				//Các khoản đóng góp theo lương
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Các khoản đóng góp theo lương", 2, 4, 36, 36));
		
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
		
		ArrayList<QlnvKhvonphiKhoachQuyTienLuongGd3n> lstCTietBCao = (ArrayList<QlnvKhvonphiKhoachQuyTienLuongGd3n>) data.getLstCTietBCao();
		
		for (QlnvKhvonphiKhoachQuyTienLuongGd3n line : lstCTietBCao) {	
			
			QlnvDmDonvi dmDvi = new QlnvDmDonvi();
			try {
				dmDvi = qlnvDmService
						.getDonViById(line.getMaDvi());
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Id QlnvDmDonvi không đúng");
			}
			
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, dmDvi.getTenDvi(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getTongCboN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongBcheDuocPdN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongTheoBcheN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTuongCbanN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getPhuCapN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getCacKhoanDgopN().toString(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getTongCboThienN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongBcheDuocPdThienN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongThienN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongTheoBcheThienN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuongCbanThienN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getPhuCapThienN().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getCacKhoanDgopThienN().toString(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getTongCboN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongBcheDuocPdN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongTheoBcheN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuongCbanN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getPhuCapN1().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getCacKhoanDgopN1().toString(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getTongCboN2().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongBcheDuocPdN2().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongN2().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongTheoBcheN2().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuongCbanN2().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getPhuCapN2().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getCacKhoanDgopN2().toString(), style, sheet);
			
			ExcelUtils.createCell(row, colIndex++, line.getTongCboN3().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongBcheDuocPdN3().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongN3().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getTongQuyLuongCoTchatLuongTheoBcheN3().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getLuongCbanN3().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getPhuCapN3().toString(), style, sheet);
			ExcelUtils.createCell(row, colIndex++, line.getCacKhoanDgopN3().toString(), style, sheet);
		}
	}
}
