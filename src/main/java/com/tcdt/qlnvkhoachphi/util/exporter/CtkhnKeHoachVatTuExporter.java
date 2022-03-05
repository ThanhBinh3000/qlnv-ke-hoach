package com.tcdt.qlnvkhoachphi.util.exporter;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi.VatTuThietBiRes;
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

	private List<KeHoachVatTuRes> buildDataExport() {
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

		//--------------Mã hàng
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.MA_HANG, 0, 5, 2, 2));

		//MẶT HÀNG
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.MAT_HANG, 0, 5, 3, 3));

		//đơn vị tính
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.DON_VI_TINH, 0, 5, 4, 4));

		//TỒN KHO cuối năm
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.TON_KHO_CUOI_NAM, 0, 1, 5, 10));

		//Tổng số
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO, 2, 5, 5, 5));

		//CHỈ TIÊU NHẬP CÁC NĂM KHÁC CHUYỂN SANG
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.CHI_TIEU_NHAP_CAC_NAM_KHAC_CHUYEN_SANG, 2, 2, 6, 9));

		//TỔNG
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.TONG, 3, 5, 6, 6));

		//KẾ HOẠCH 2019
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.KE_HOACH_NAM, "2020"), 3, 5, 7, 7));

		//KẾ HOẠCH 2020
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.KE_HOACH_NAM, "2020"), 3, 5, 8, 8));

		//KẾ HOẠCH 2021
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.KE_HOACH_NAM, "2021"), 3, 5, 9, 9));

		//KẾ HOẠCH 2022
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, String.format(Constants.ExcelHeader.KE_HOACH_NAM, "2022"), 2, 5, 10, 10));


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

		List<KeHoachVatTuRes> data = this.buildDataExport();
		Row row;


		for (KeHoachVatTuRes line : data) {
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt(), style, sheet);

			//cuc DTTNN khu vuc
			ExcelUtils.createCell(row, colIndex++, line.getCucDTNNKhuVuc(), style, sheet);

			//Mã hàng
			ExcelUtils.createCell(row, colIndex++, line.getTkdnTongSoMuoi(), style, sheet);
			//mặt hàng
			for (VatTuNhapRes vatTuNhapRes : line.getTkdnMuoi()) {
				ExcelUtils.createCell(row, colIndex++, vatTuNhapRes.getSoLuong(), style, sheet);
			}

			//Đơn vị tính
			ExcelUtils.createCell(row, colIndex++, line.getNtnTongSoMuoi(), style, sheet);

			//Tổng số
			ExcelUtils.createCell(row, colIndex++, line.getXtnTongSoMuoi(), style, sheet);

			//Tổng
			for (VatTuNhapRes vatTuNhapRes : line.getXtnMuoi()) {
				ExcelUtils.createCell(row, colIndex++, vatTuNhapRes.getSoLuong(), style, sheet);
			}

			//Chỉ tiêu nhập các năm khác chuyển sang
			for (VatTuThietBiRes vatTuNhapRes : line.getVatTuThietBi()) {
				ExcelUtils.createCell(row, colIndex++, vatTuNhapRes.getSoLuong(), style, sheet);
			}

		}
	}
}
