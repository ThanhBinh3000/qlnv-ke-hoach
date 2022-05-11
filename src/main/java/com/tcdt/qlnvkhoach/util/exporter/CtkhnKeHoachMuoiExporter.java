package com.tcdt.qlnvkhoach.util.exporter;

import com.tcdt.qlnvkhoach.entities.MergeCellObj;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.VatTuNhapRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CtkhnKeHoachMuoiExporter implements ExportService {

	@Override
	public void export(XSSFWorkbook workbook, ChiTieuKeHoachNamRes data) {
		if (CollectionUtils.isEmpty(data.getKhMuoiDuTru())) {
			log.info("Không có data chỉ tiêu kế hoạch muối");
			throw new IllegalArgumentException("Không có data chỉ tiêu kế hoạch muối");
		}

		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_KE_HOACH_MUOI_DTNN);

		writeHeaderLine(workbook, sheet, data);
		writeDataLines(sheet, workbook, data);
	}

	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet, ChiTieuKeHoachNamRes data) {

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

		//--------------TỒN KHO ĐẦU NĂM
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.TON_KHO_DAU_NAM, 0, 1, 2, 5));

		//TỒN KHO ĐẦU NĂM: Tổng số
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO, 2, 5, 2, 2));

		//TỒN KHO ĐẦU NĂM: Trong đó
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2, 2, 3, 5));

		List<Integer> tkdnn = data.getKhMuoiDuTru().get(0).getTkdnMuoi().stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());


		//TỒN KHO ĐẦU NĂM: Nhập 2019
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, tkdnn.get(0)), 3, 5, 3, 3));

		//TỒN KHO ĐẦU NĂM: Nhập 2020
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, tkdnn.get(1)), 3, 5, 4, 4));

		//TỒN KHO ĐẦU NĂM: Nhập 2021
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, tkdnn.get(2)), 3, 5, 5, 5));


		//-------------------------NHẬP TRONG NĂM
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.NHAP_TRONG_NAM, 0, 1, 6, 8));

		//NHẬP TRONG NĂM: Tổng số
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO, 2, 5, 6, 8));


		//--------------XUẤT TRONG NĂM
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.XUAT_TRONG_NAM, 0, 1, 9, 12));

		//XUẤT TRONG NĂM: Tổng số
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO, 2, 5, 9, 9));

		//XUẤT TRONG NĂM: Trong đó
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TRONG_DO, 2, 2, 10, 12));

		List<Integer> xtn = data.getKhMuoiDuTru().get(0).getXtnMuoi().stream().map(VatTuNhapRes::getNam).collect(Collectors.toList());


		//XUẤT TRONG NĂM: nhập 2019
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, xtn.get(0)), 3, 5, 10, 10));

		//XUẤT TRONG NĂM: nhập 2020
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, xtn.get(1)), 3, 5, 11, 11));

		//XUẤT TRONG NĂM: nhập 2021
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row3, String.format(Constants.ExcelHeader.NAM_NHAP, xtn.get(2)), 3, 5, 12, 12));

		//TỒN KHO CUỐI NĂM------------------
		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.TON_KHO_CUOI_NAM, 0, 1, 13, 15));

		mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, Constants.ExcelHeader.TONG_SO, 2, 5, 13, 15));


		for (MergeCellObj mergeCellObj : mergeCellHeaderRow) {
			CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();
			sheet.addMergedRegion(cellRangeAddress);
			ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(), style, sheet);
		}
	}

	private int mergeCellData(Row row, XSSFSheet sheet, String data, CellStyle style, int colIndex,
							  int firstRow, int lastRow, int firstCol, int lastCol) {
		try {
			lastRow = firstRow;
			firstCol = colIndex;
			lastCol = firstCol + 2;

			MergeCellObj mergeCellObj = ExcelUtils.buildMergeCell(row, data, firstRow, lastRow, firstCol, lastCol);

			CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();

			sheet.addMergedRegion(cellRangeAddress);

			ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(), style, sheet);

			firstRow = lastRow + 1;
		} catch (Exception e) {
			log.error("abc", e);
		}

		return lastCol;
	}

	private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, ChiTieuKeHoachNamRes data) {
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		Row row;

		int startRowIndex = 6;
		int firstRow = 6;
		int lastRow = 0;
		int firstCol = 0;
		int lastCol = 0;

		for (KeHoachMuoiDuTruRes line : data.getKhMuoiDuTru()) {
			row = sheet.createRow(startRowIndex++);
			int colIndex = 0;
			// stt
			ExcelUtils.createCell(row, colIndex, line.getStt(), style, sheet);

			//cuc DTTNN khu vuc
			colIndex++;
			ExcelUtils.createCell(row, colIndex, line.getTenDonVi(), style, sheet);

			//TỒN KHO ĐẦU NĂM-------------------------
			//Tổng số
			colIndex++;
			ExcelUtils.createCell(row, colIndex, line.getTkdnTongSoMuoi().toString(), style, sheet);

			//Nhập
			for (VatTuNhapRes vatTuNhapRes : line.getTkdnMuoi()) {
				colIndex++;
				ExcelUtils.createCell(row, colIndex, vatTuNhapRes.getSoLuong().toString(), style, sheet);
			}

			//NHẬP TRONG NĂM-------------------------
			//Tổng số
			colIndex++;
			colIndex = this.mergeCellData(row, sheet, line.getNtnTongSoMuoi().toString(), style, colIndex,
					firstRow, lastRow, firstCol, lastCol);

			//XUẤT TRONG NĂM-------------------------
			//Tổng số
			colIndex++;
			ExcelUtils.createCell(row, colIndex, line.getXtnTongSoMuoi().toString(), style, sheet);

			//Nhập
			for (VatTuNhapRes vatTuNhapRes : line.getXtnMuoi()) {
				colIndex++;
				ExcelUtils.createCell(row, colIndex, vatTuNhapRes.getSoLuong().toString(), style, sheet);
			}

			//TỒN KHO CUỐI NĂM-------------------------
			//Tổng số
			colIndex++;
			this.mergeCellData(row, sheet, line.getTkcnTongSoMuoi().toString(), style, colIndex,
					firstRow, lastRow, firstCol, lastCol);
		}
	}
}
