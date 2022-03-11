package com.tcdt.qlnvkhoachphi.util.exporter;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi.NhomVatTuThietBiRes;
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
public class CtkhnKeHoachVatTuExporter implements ExportService {

	@Override
	public void export(XSSFWorkbook workbook, ChiTieuKeHoachNamRes data) {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_KE_HOACH_NHAP_VT_TB);

		writeHeaderLine(workbook, sheet);
		writeDataLines(sheet, workbook, data);
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
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 5, 0, 0));

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

	private void mergeCellData(XSSFSheet sheet, String data, CellStyle style, Row row,
							   int firstRow, int lastRow, int firstCol, int lastCol) {
		MergeCellObj mergeCellObj = ExcelUtils.buildMergeCell(row, data, firstRow, lastRow, firstCol, lastCol);

		CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();

		sheet.addMergedRegion(cellRangeAddress);

		ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(), style, sheet);
	}

	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, ChiTieuKeHoachNamRes data) {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		Row row;

		int firstRow = 0;
		int lastRow = 0;
		int firstCol = 0;
		int lastCol = 0;
		int rowIndex = 6;

		data.getKhVatTu().addAll(data.getKhVatTu());


		for (KeHoachVatTuRes line : data.getKhVatTu()) {
			//Tạo row CỤC DTNN KHU VỰC/STT
			row = sheet.createRow(rowIndex);
			int colIndex = 0;
			List<NhomVatTuThietBiRes> nhomVatTuThietBiList = line.getNhomVatTuThietBi();

			for (NhomVatTuThietBiRes nhomVatTuThietBiRes : nhomVatTuThietBiList) {
				// stt
				firstRow = rowIndex;
				lastRow = firstRow + nhomVatTuThietBiRes.getVatTuThietBi().size();
				firstCol = colIndex;
				lastCol = firstCol;

				this.mergeCellData(sheet, line.getStt().toString(), style, row, firstRow, lastRow, firstCol, lastCol);

				//cuc DTTNN khu vuc
				firstRow = rowIndex;
				lastRow = firstRow + nhomVatTuThietBiRes.getVatTuThietBi().size();
				firstCol = colIndex + 1;
				lastCol = firstCol;

				this.mergeCellData(sheet, line.getTenDonVi(), style, row, firstRow, lastRow, firstCol, lastCol);
				colIndex = colIndex + 2;
				//Mã hàng
				ExcelUtils.createCell(row, colIndex++, nhomVatTuThietBiRes.getMaVatTuCha(), style, sheet);
				//mặt hàng
				ExcelUtils.createCell(row, colIndex++, nhomVatTuThietBiRes.getTenVatTuCha(), style, sheet);
				//Đơn vị tính
				ExcelUtils.createCell(row, colIndex++, nhomVatTuThietBiRes.getDonViTinh(), style, sheet);
				//Tổng số
				ExcelUtils.createCell(row, colIndex++, nhomVatTuThietBiRes.getTongNhap().toString(), style, sheet);

				//Tổng các năm trước
				ExcelUtils.createCell(row, colIndex++, nhomVatTuThietBiRes.getTongCacNamTruoc().toString(), style, sheet);

				//Các năm khác chuyển sang
				for (VatTuNhapRes vtCacNamTruoc : nhomVatTuThietBiRes.getCacNamTruoc()) {
					ExcelUtils.createCell(row, colIndex++, vtCacNamTruoc.getSoLuong().toString(), style, sheet);
				}

				//Nhập trong năm
				ExcelUtils.createCell(row, colIndex++, nhomVatTuThietBiRes.getNhapTrongNam().toString(), style, sheet);

				for (VatTuThietBiRes vatTuThietBiRes : nhomVatTuThietBiRes.getVatTuThietBi()) {
					rowIndex = rowIndex + 1;
					row = sheet.createRow(rowIndex);
					colIndex = 2;
					//Mã hàng
					ExcelUtils.createCell(row, colIndex++, vatTuThietBiRes.getMaVatTu(), style, sheet);
					//mặt hàng
					ExcelUtils.createCell(row, colIndex++, vatTuThietBiRes.getTenVatTu(), style, sheet);
					//Đơn vị tính
					ExcelUtils.createCell(row, colIndex++, vatTuThietBiRes.getDonViTinh(), style, sheet);
					//Tổng số
					ExcelUtils.createCell(row, colIndex++, vatTuThietBiRes.getTongNhap().toString(), style, sheet);

					//Tổng các năm trước
					ExcelUtils.createCell(row, colIndex++, vatTuThietBiRes.getTongCacNamTruoc().toString(), style, sheet);

					//Các năm khác chuyển sang
					for (VatTuNhapRes vtCacNamTruoc : vatTuThietBiRes.getCacNamTruoc()) {
						ExcelUtils.createCell(row, colIndex++, vtCacNamTruoc.getSoLuong().toString(), style, sheet);
					}

					//Nhập trong năm
					ExcelUtils.createCell(row, colIndex++, vatTuThietBiRes.getNhapTrongNam().toString(), style, sheet);
				}
				rowIndex = rowIndex + 1;
			}

		}
	}
}
