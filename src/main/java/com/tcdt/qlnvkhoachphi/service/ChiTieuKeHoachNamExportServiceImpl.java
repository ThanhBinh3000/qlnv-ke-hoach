package com.tcdt.qlnvkhoachphi.service;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoachphi.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ChiTieuKeHoachNamExportServiceImpl implements ChiTieuKeHoachNamExportService {


	@Override
	public Boolean exportToExcel(HttpServletResponse response) {
		try {
			this.export(response);
		} catch (IOException e) {
			log.error("Error export", e);
		}
		return true;
	}

	private List<KeHoachLuongThucDuTruRes> buildDataExport() {
		return new LinkedList<>();
	}


	private MergeCellObj buildMergeCell(Row row, String value, int firstRow, int lastRow, int firstCol, int lastCol) {
		return MergeCellObj.builder()
				.firstRow(firstRow)
				.lastRow(lastRow)
				.firstCol(firstCol)
				.lastCol(lastCol)
				.value(value)
				.row(row)
				.build();
	}

	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet) {
		//STYLE
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(11);
		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		Row row0 = sheet.createRow(0);
		Row row2 = sheet.createRow(2);
		Row row3 = sheet.createRow(3);
		Row row4 = sheet.createRow(4);


		List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

		//STT
		mergeCellHeaderRow.add(this.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 5, 0, 0));

		//Cục DTTNN khu vực
		mergeCellHeaderRow.add(this.buildMergeCell(row0, Constants.ExcelHeader.CUC_DTNN_KHU_VUC, 0, 5, 1, 1));

		//--------------TỒN KHO ĐẦU NĂM
		mergeCellHeaderRow.add(this.buildMergeCell(row0, Constants.ExcelHeader.TON_KHO_DAU_NAM, 0, 1, 2, 9));

		//TỒN KHO ĐẦU NĂM: Tổng số
		mergeCellHeaderRow.add(this.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO_QUY_THOC, 2, 5, 2, 2));

		//TỒN KHO ĐẦU NĂM: Trong đó
		mergeCellHeaderRow.add(this.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2, 2, 3, 9));

		//TỒN KHO ĐẦU NĂM: Thóc
		mergeCellHeaderRow.
				add(this.buildMergeCell(row3, Constants.ExcelHeader.THOC, 3, 3, 3, 6));

		//TỒN KHO ĐẦU NĂM: Gạo
		mergeCellHeaderRow.
				add(this.buildMergeCell(row3, Constants.ExcelHeader.GAO, 3, 3, 7, 9));

		//TỒN KHO ĐẦU NĂM: Tổng
		mergeCellHeaderRow.
				add(this.buildMergeCell(row4, Constants.ExcelHeader.TONG, 4, 5, 3, 3));

		//TỒN KHO ĐẦU NĂM: Thóc - Nhập 2019
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2019"), 4, 5, 4, 4));

		//TỒN KHO ĐẦU NĂM: Thóc - Nhập 2020
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 4, 5, 5, 5));

		//TỒN KHO ĐẦU NĂM: Thóc - Nhập 2021
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 4, 5, 6, 6));

		//TỒN KHO ĐẦU NĂM: Tổng-gạo
		mergeCellHeaderRow.add(this.buildMergeCell(row4, Constants.ExcelHeader.TONG, 4, 5, 7, 7));

		//TỒN KHO ĐẦU NĂM: Gạo - Nhập 2020
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 4, 5, 8, 8));

		//TỒN KHO ĐẦU NĂM: Gạo - Nhập 2021
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 4, 5, 9, 9));

		//-------------------------NHẬP TRONG NĂM
		mergeCellHeaderRow.add(this.buildMergeCell(row0, Constants.ExcelHeader.NHAP_TRONG_NAM, 0, 1, 10, 12));

		//NHẬP TRONG NĂM: Trong đó
		mergeCellHeaderRow.add(this.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2, 2, 10, 12));

		//NHẬP TRONG NĂM: Tổng số quy thóc
		mergeCellHeaderRow.add(this.buildMergeCell(row3, Constants.ExcelHeader.TONG_SO_QUY_THOC, 3, 5, 10, 10));

		//NHẬP TRONG NĂM: Thóc
		mergeCellHeaderRow.add(this.buildMergeCell(row3, Constants.ExcelHeader.THOC, 3, 5, 11, 11));

		//NHẬP TRONG NĂM: GẠO
		mergeCellHeaderRow.add(this.buildMergeCell(row3, Constants.ExcelHeader.GAO, 3, 5, 12, 12));

		//--------------XUẤT TRONG NĂM
		mergeCellHeaderRow.add(this.buildMergeCell(row0, Constants.ExcelHeader.XUAT_TRONG_NAM, 0, 1, 13, 20));

		//XUẤT TRONG NĂM: Tổng số quy thóc
		mergeCellHeaderRow.add(this.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO_QUY_THOC, 2, 5, 13, 13));

		//XUẤT TRONG NĂM: Trong đó
		mergeCellHeaderRow.add(this.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2, 2, 14, 20));

		//XUẤT TRONG NĂM: Thóc
		mergeCellHeaderRow.add(this.buildMergeCell(row3, Constants.ExcelHeader.THOC, 3, 3, 14, 17));

		//XUẤT TRONG NĂM: Gạo
		mergeCellHeaderRow.add(this.buildMergeCell(row3, Constants.ExcelHeader.GAO, 3, 3, 18, 20));

		//XUẤT TRONG NĂM: Tổng-Thóc
		mergeCellHeaderRow.add(this.buildMergeCell(row4, Constants.ExcelHeader.THOC, 4,5, 14,14 ));

		//XUẤT TRONG NĂM: Thóc- nhập 2019
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2019"), 4, 5, 15, 15));

		//XUẤT TRONG NĂM: Thóc- nhập 2020
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 4, 5, 16, 16));

		//XUẤT TRONG NĂM: Thóc- nhập 2021
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 4, 5, 17, 17));

		//XUẤT TRONG NĂM: Gạo- tổng
		mergeCellHeaderRow.add(this.buildMergeCell(row4, Constants.ExcelHeader.THOC, 4,5, 18,18 ));

		//XUẤT TRONG NĂM: Gạo- nhập 2020
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 4, 5, 19, 19));

		//XUẤT TRONG NĂM: Gạo- nhập 2021
		mergeCellHeaderRow.add(this.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 4, 5, 20, 20));

		//TỒN KHO CUỐI NĂM------------------
		mergeCellHeaderRow.add(this.buildMergeCell(row0, Constants.ExcelHeader.TON_KHO_CUOI_NAM, 0,1, 21,23 ));

		//TỒN KHO CUỐI NĂM-TRONG ĐÓ
		mergeCellHeaderRow.add(this.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2,2, 21,23 ));

		//TỒN KHO CUỐI NĂM-Tổng số quy thóc
		mergeCellHeaderRow.add(this.buildMergeCell(row3, Constants.ExcelHeader.TONG_SO_QUY_THOC, 3,5, 21,21 ));

		//TỒN KHO CUỐI NĂM-Trong đó - thóc
		mergeCellHeaderRow.add(this.buildMergeCell(row3, Constants.ExcelHeader.THOC, 3,5, 22,22 ));

		//TỒN KHO CUỐI NĂM-Trong đó - gạo
		mergeCellHeaderRow.add(this.buildMergeCell(row3, Constants.ExcelHeader.GAO, 3,5, 23,23 ));

		for (MergeCellObj mergeCellObj : mergeCellHeaderRow) {
			CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();
			sheet.addMergedRegion(cellRangeAddress);
			createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(), style, sheet);
		}

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
//		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook) {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		List<KeHoachLuongThucDuTruRes> data = this.buildDataExport();

		for (KeHoachLuongThucDuTruRes line : data) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, "id", style, sheet);
			createCell(row, columnCount++, "Email", style, sheet);
			createCell(row, columnCount++, "FullName", style, sheet);
			createCell(row, columnCount++, "Role", style, sheet);
			createCell(row, columnCount++, "abc", style, sheet);

		}
	}

	public void export(HttpServletResponse response) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_KE_HOACH_LUONG_THUC_DU_TRU_NHA_NUOC);

		writeHeaderLine(workbook, sheet);
		writeDataLines(sheet, workbook);

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();
	}
}
