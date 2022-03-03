package com.tcdt.qlnvkhoachphi.util.exporter;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class CtkhnKeHoachVatTuExporter {
	private int startRowIndex = 6;

	public void export(XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_KE_HOACH_MUOI_DTNN);

		writeHeaderLine(workbook, sheet);
		writeDataLines(sheet, workbook);
	}

	private List<KeHoachMuoiDuTruRes> buildDataExport() {
		return new LinkedList<>();
	}

	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet) {
		//STYLE
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(11);
		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		Row row0 = sheet.createRow(0);
		Row row2 = sheet.createRow(2);
		Row row3 = sheet.createRow(3);


		List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

		//STT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0,5, 0,0));

		//Cục DTTNN khu vực
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.CUC_DTNN_KHU_VUC, 0, 5, 1, 1));

		//--------------TỒN KHO ĐẦU NĂM
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.TON_KHO_DAU_NAM, 0,1, 2,5));

		//TỒN KHO ĐẦU NĂM: Tổng số
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO, 2,5, 2,2));

		//TỒN KHO ĐẦU NĂM: Trong đó
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2,2, 3,5));

		//TỒN KHO ĐẦU NĂM: Nhập 2019
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, "2019"), 3,5, 3,3));

		//TỒN KHO ĐẦU NĂM: Nhập 2020
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 3,5, 4,4));

		//TỒN KHO ĐẦU NĂM: Nhập 2021
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 3,5, 5,5));


		//-------------------------NHẬP TRONG NĂM
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.NHAP_TRONG_NAM, 0,1, 6,8));

		//NHẬP TRONG NĂM: Tổng số
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO, 2,5, 6,8));


		//--------------XUẤT TRONG NĂM
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.XUAT_TRONG_NAM, 0,1, 9,12));

		//XUẤT TRONG NĂM: Tổng số
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO, 2,5, 9,9));

		//XUẤT TRONG NĂM: Trong đó
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2,2, 10,12));

		//XUẤT TRONG NĂM: nhập 2019
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, "2019"), 3,5, 10,10));

		//XUẤT TRONG NĂM: nhập 2020
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 3,5, 11,11));

		//XUẤT TRONG NĂM: nhập 2021
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 3,5, 12,12));

		//TỒN KHO CUỐI NĂM------------------
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.TON_KHO_CUOI_NAM, 0,1, 13,15));

		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO, 2,5, 13,15));


		for (MergeCellObj mergeCellObj : mergeCellHeaderRow) {
			CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();
			sheet.addMergedRegion(cellRangeAddress);
			ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(), style, sheet);
		}
	}

	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		List<KeHoachMuoiDuTruRes> data = this.buildDataExport();
		Row row;


		for (KeHoachMuoiDuTruRes line : data) {
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt(), style, sheet);

			//cuc DTTNN khu vuc
			ExcelUtils.createCell(row, colIndex++, line.getCucDTNNKhuVuc(), style, sheet);

			//TỒN KHO ĐẦU NĂM-------------------------
			//Tổng số
			ExcelUtils.createCell(row, colIndex++, line.getTkdnTongSoMuoi(), style, sheet);
			//Nhập
			for (VatTuNhapRes vatTuNhapRes : line.getTkdnMuoi()) {
				ExcelUtils.createCell(row, colIndex++, vatTuNhapRes.getSoLuong(), style, sheet);
			}

			//NHẬP TRONG NĂM-------------------------
			//Tổng số
			ExcelUtils.createCell(row, colIndex++, line.getNtnTongSoMuoi(), style, sheet);

			//XUẤT TRONG NĂM-------------------------
			//Tổng số
			ExcelUtils.createCell(row, colIndex++, line.getXtnTongSoMuoi(), style, sheet);

			//Nhập
			for (VatTuNhapRes vatTuNhapRes : line.getXtnMuoi()) {
				ExcelUtils.createCell(row, colIndex++, vatTuNhapRes.getSoLuong(), style, sheet);
			}

			//TỒN KHO CUỐI NĂM-------------------------
			//Tổng số
			ExcelUtils.createCell(row, colIndex++, line.getTkcnTongSoMuoi(), style, sheet);

		}
	}
}
