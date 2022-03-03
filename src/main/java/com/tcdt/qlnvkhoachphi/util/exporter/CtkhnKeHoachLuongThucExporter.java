package com.tcdt.qlnvkhoachphi.util.exporter;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
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
public class CtkhnKeHoachLuongThucExporter {
	private int startRowIndex = 6;

	public void export(XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_KE_HOACH_LUONG_THUC_DTNN);

		writeHeaderLine(workbook, sheet);
		writeDataLines(sheet, workbook);
	}

	private List<KeHoachLuongThucDuTruRes> buildDataExport() {
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
		Row row4 = sheet.createRow(4);


		List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

		//STT
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 5, 0, 0));

		//Cục DTTNN khu vực
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.CUC_DTNN_KHU_VUC, 0, 5, 1, 1));

		//--------------TỒN KHO ĐẦU NĂM
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.TON_KHO_DAU_NAM, 0, 1, 2, 9));

		//TỒN KHO ĐẦU NĂM: Tổng số
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO_QUY_THOC, 2, 5, 2, 2));

		//TỒN KHO ĐẦU NĂM: Trong đó
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2, 2, 3, 9));

		//TỒN KHO ĐẦU NĂM: Thóc
		mergeCellHeaderRow.
				add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.THOC, 3, 3, 3, 6));

		//TỒN KHO ĐẦU NĂM: Gạo
		mergeCellHeaderRow.
				add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.GAO, 3, 3, 7, 9));

		//TỒN KHO ĐẦU NĂM: Tổng
		mergeCellHeaderRow.
				add(ExcelUtils.buildMergeCell(row4, Constants.ExcelHeader.TONG, 4, 5, 3, 3));

		//TỒN KHO ĐẦU NĂM: Thóc - Nhập 2019
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2019"), 4, 5, 4, 4));

		//TỒN KHO ĐẦU NĂM: Thóc - Nhập 2020
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 4, 5, 5, 5));

		//TỒN KHO ĐẦU NĂM: Thóc - Nhập 2021
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 4, 5, 6, 6));

		//TỒN KHO ĐẦU NĂM: Tổng-gạo
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, Constants.ExcelHeader.TONG, 4, 5, 7, 7));

		//TỒN KHO ĐẦU NĂM: Gạo - Nhập 2020
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 4, 5, 8, 8));

		//TỒN KHO ĐẦU NĂM: Gạo - Nhập 2021
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 4, 5, 9, 9));

		//-------------------------NHẬP TRONG NĂM
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.NHAP_TRONG_NAM, 0, 1, 10, 12));

		//NHẬP TRONG NĂM: Trong đó
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2, 2, 10, 12));

		//NHẬP TRONG NĂM: Tổng số quy thóc
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.TONG_SO_QUY_THOC, 3, 5, 10, 10));

		//NHẬP TRONG NĂM: Thóc
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.THOC, 3, 5, 11, 11));

		//NHẬP TRONG NĂM: GẠO
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.GAO, 3, 5, 12, 12));

		//--------------XUẤT TRONG NĂM
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.XUAT_TRONG_NAM, 0, 1, 13, 20));

		//XUẤT TRONG NĂM: Tổng số quy thóc
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO_QUY_THOC, 2, 5, 13, 13));

		//XUẤT TRONG NĂM: Trong đó
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2, 2, 14, 20));

		//XUẤT TRONG NĂM: Thóc
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.THOC, 3, 3, 14, 17));

		//XUẤT TRONG NĂM: Gạo
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.GAO, 3, 3, 18, 20));

		//XUẤT TRONG NĂM: Tổng-Thóc
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, Constants.ExcelHeader.THOC, 4, 5, 14, 14));

		//XUẤT TRONG NĂM: Thóc- nhập 2019
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2019"), 4, 5, 15, 15));

		//XUẤT TRONG NĂM: Thóc- nhập 2020
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 4, 5, 16, 16));

		//XUẤT TRONG NĂM: Thóc- nhập 2021
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 4, 5, 17, 17));

		//XUẤT TRONG NĂM: Gạo- tổng
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, Constants.ExcelHeader.GAO, 4, 5, 18, 18));

		//XUẤT TRONG NĂM: Gạo- nhập 2020
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2020"), 4, 5, 19, 19));

		//XUẤT TRONG NĂM: Gạo- nhập 2021
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, String.format(Constants.ExcelHeader.NAM_NHAP, "2021"), 4, 5, 20, 20));

		//TỒN KHO CUỐI NĂM------------------
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.TON_KHO_CUOI_NAM, 0, 1, 21, 23));

		//TỒN KHO CUỐI NĂM-TRONG ĐÓ
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2, 2, 21, 23));

		//TỒN KHO CUỐI NĂM-Tổng số quy thóc
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.TONG_SO_QUY_THOC, 3, 5, 21, 21));

		//TỒN KHO CUỐI NĂM-Trong đó - thóc
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.THOC, 3, 5, 22, 22));

		//TỒN KHO CUỐI NĂM-Trong đó - gạo
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, Constants.ExcelHeader.GAO, 3, 5, 23, 23));

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

		List<KeHoachLuongThucDuTruRes> data = this.buildDataExport();
		Row row;


		for (KeHoachLuongThucDuTruRes line : data) {
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex++, line.getStt(), style, sheet);

			//cuc DTTNN khu vuc
			ExcelUtils.createCell(row, colIndex++, line.getCucDTNNKhuVuc(), style, sheet);

			//TỒN KHO ĐẦU NĂM-------------------------
			//Tổng số quy thóc
			ExcelUtils.createCell(row, colIndex++, line.getTkdnTongSoQuyThoc(), style, sheet);

			//THÓC:

			//Tổng
			ExcelUtils.createCell(row, colIndex++, line.getTkdnTongThoc(), style, sheet);

			//Thóc nhập
			for (VatTuNhapRes vatTuNhapRes : line.getTkdnThoc()) {
				ExcelUtils.createCell(row, colIndex++, vatTuNhapRes.getSoLuong(), style, sheet);
			}

			//GẠO:
			//Tổng
			ExcelUtils.createCell(row, colIndex++, line.getTkdnTongGao(), style, sheet);

			//Gạo nhập
			for (VatTuNhapRes vatTuNhapRes : line.getTkdnGao()) {
				ExcelUtils.createCell(row, colIndex++, vatTuNhapRes.getSoLuong(), style, sheet);
			}

			//NHẬP TRONG NĂM-------------------------
			//Tổng số quy thóc
			ExcelUtils.createCell(row, colIndex++, line.getNtnTongSoQuyThoc(), style, sheet);

			//Thóc
			ExcelUtils.createCell(row, colIndex++, line.getNtnThoc(), style, sheet);

			//Gạo
			ExcelUtils.createCell(row, colIndex++, line.getNtnGao(), style, sheet);

			//XUẤT TRONG NĂM-------------------------
			//Tổng số quy thóc
			ExcelUtils.createCell(row, colIndex++, line.getXtnTongSoQuyThoc(), style, sheet);

			//Thóc
			ExcelUtils.createCell(row, colIndex++, line.getXtnThoc(), style, sheet);

			//Nhập Thóc
			for (VatTuNhapRes vatTuNhapRes : line.getXtnThoc()) {
				ExcelUtils.createCell(row, colIndex++, vatTuNhapRes.getSoLuong(), style, sheet);
			}

			//Gạo
			ExcelUtils.createCell(row, colIndex++, line.getXtnGao(), style, sheet);

			//Nhập Gạo
			for (VatTuNhapRes vatTuNhapRes : line.getXtnGao()) {
				ExcelUtils.createCell(row, colIndex++, vatTuNhapRes.getSoLuong(), style, sheet);
			}

			//TỒN KHO CUỐI NĂM-------------------------
			//Tổng số quy thóc
			ExcelUtils.createCell(row, colIndex++, line.getTkcnTongSoQuyThoc(), style, sheet);

			//Thóc
			ExcelUtils.createCell(row, colIndex++, line.getTkcnTongThoc(), style, sheet);

			//Gạo
			ExcelUtils.createCell(row, colIndex++, line.getTkcnTongGao(), style, sheet);
		}
	}
}
