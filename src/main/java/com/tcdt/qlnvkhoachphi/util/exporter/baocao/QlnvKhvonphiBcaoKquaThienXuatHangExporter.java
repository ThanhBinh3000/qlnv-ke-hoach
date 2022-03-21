package com.tcdt.qlnvkhoachphi.util.exporter.baocao;

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
import org.springframework.stereotype.Component;

import com.tcdt.qlnvkhoachphi.entities.MergeCellObj;
import com.tcdt.qlnvkhoachphi.response.baocao.QlnvKhvonphiBcaoResp;
import com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienXuatHang;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QlnvKhvonphiBcaoKquaThienXuatHangExporter implements BCaoExportService {
	@Override
	public void exportBCao(XSSFWorkbook workbook, QlnvKhvonphiBcaoResp data) {
		if (data.getLstCTietBCao()==null) {
			log.info("Không có data báo cáo kết quả thực hiện xuất hàng");
			throw new IllegalArgumentException("Không có data báo cáo kết quả thực hiện xuất hàng");
		}
		XSSFSheet sheet = workbook
				.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_QLNV_KHVONPHI_BAO_CAO);

		writeHeaderLine(workbook, sheet, data);
		writeDataLines(sheet, workbook, data);

	}

	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet, QlnvKhvonphiBcaoResp data) {
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
//				Row row2 = sheet.createRow(2);
//				Row row3 = sheet.createRow(4);
				List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

				//STT
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 3, 0, 0));

				//Tên vật tư,hàng hóa
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Tên vật tư,hàng hóa", 0, 3, 1, 1));

				//DVT
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "DVT", 0, 3, 2, 2));

				//Số lượng theo kế hoạch
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Số lượng theo kế hoạch", 0, 3, 3, 3));

				//Số lượng thực tế thực hiện
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Số lượng thực tế thực hiện", 0, 3, 4, 4));


				// Đơn giá
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Đơn giá", 0, 0, 5, 7));
				     // Giá kế hoạch
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Giá kế hoạch", 1, 3, 5, 5));

				     // Giá bán tối thiểu Tổng cục QĐ
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Giá bán tối thiểu Tổng cục QĐ", 1, 3, 6, 6));

				     // Giá bán thực tế
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Giá bán thực tế", 1, 3, 7, 7));

				// Thành tiền
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Thành tiền", 0, 0, 8, 10));
				     // Giá hoạch toán
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Giá hoạch toán", 1, 3, 8, 8));
				     // Giá bán thực tế
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Giá bán thực tế", 1, 3, 9, 9));

				     // Chênh lệch giữa giá bán thực tế và giá hạch toán
                     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Chênh lệch giữa giá bán thực tế và giá hạch toán", 1, 3, 10, 10));

				// Ghi chú
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Ghi chú", 0, 3, 11, 11));

				for (MergeCellObj mergeCellObj : mergeCellHeaderRow) {
					CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();
					sheet.addMergedRegion(cellRangeAddress);
					ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(), style, sheet);
				}
			}


			@SuppressWarnings("unchecked")
			private void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, QlnvKhvonphiBcaoResp data) {

				CellStyle style = workbook.createCellStyle();
				XSSFFont font = workbook.createFont();
				font.setFontHeight(14);
				style.setFont(font);
				style.setBorderBottom(BorderStyle.THIN);
		        style.setBorderRight(BorderStyle.THIN);
		        style.setBorderLeft(BorderStyle.THIN);
		        style.setBorderTop(BorderStyle.THIN);

				Row row;
				int startRowIndex = 4;
				ArrayList<QlnvKhvonphiBcaoKquaThienXuatHang> lstCTietBCao = (ArrayList<QlnvKhvonphiBcaoKquaThienXuatHang>) data.getLstCTietBCao();

				for (QlnvKhvonphiBcaoKquaThienXuatHang line : lstCTietBCao) {
					row = sheet.createRow(startRowIndex++);
					int colIndex = 0;
					// stt
					ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getMaVtu().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getMaDviTinh().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getSoLuongKhoach().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getSoLuongTte().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getDgGiaKhoach().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getDgGiaBanTthieu().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getDgGiaBanTte().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getTtGiaHtoan().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getTtGiaBanTte().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getTtClechGiaTteVaGiaHtoan().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getGhiChu().toString(), style, sheet);
				}
			}

}
