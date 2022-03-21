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
import com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienNhapMuaHang;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class QlnvKhvonphiBCaoKquaThienNhapMuaHangExporter implements BCaoExportService{

	@Override
	public void exportBCao(XSSFWorkbook workbook, QlnvKhvonphiBcaoResp data) {

		if (data.getLstCTietBCao()==null) {
			log.info("Không có data báo cáo kết quả thực hiện nhập mua hàng");
			throw new IllegalArgumentException("Không có data báo cáo kết quả thực hiện nhập mua hàng");
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

				  //I
//				  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "I", 4, 5, 0, 0));

				//Tên vật tư,hàng hóa
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Tên vật tư,hàng hóa", 0, 3, 1, 1));

				  //Đơn vị mua
//				  mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Đơn vị mua", 4, 5, 1, 1));

				//DVT
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "DVT", 0, 3, 2, 2));


				// Kế hoạch
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Kế hoạch", 0, 0, 3, 6));
				     // Quyết định số,ngày
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Quyết định số,ngày", 1, 3, 3, 3));
				        // 1
//				        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "1", 4, 5, 3, 3));
				     // Số lượng
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Số lượng", 1, 3, 4, 4));
//				        // 2
//				        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "2",2, 3, 4, 4));
				     // Giá mua tối đa Tổng cục QĐ
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Giá mua tối đa Tổng cục QĐ", 1, 3, 5, 5));
//				        //3
//				        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "3",2, 3, 5, 5));
				     // Thành tiền(Đã bao gồm thuế GTGT)
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Thành tiền(Đã bao gồm thuế GTGT)", 1, 3, 6, 6));
//				        //4=2*3
//				        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "4=2*3",2, 3, 6, 6));
//
				// Thực hiện
				mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Thực hiện", 0, 0, 7, 9));
				     // Số lượng
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Số lượng", 1, 3, 7, 7));
//				        //5
//				        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "5",2, 3, 7, 7));
				     // Tối đa Tổng cục QĐ
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Tối đa Tổng cục QĐ", 1, 3 ,8, 8));
//				        //6
//				        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "6",2, 2 ,8, 8));
				     // Thành tiền(Đã bao gồm thuế GTGT)
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Thành tiền(Đã bao gồm thuế GTGT)", 1, 3 ,9, 9));
//				        //7=5*6
//				        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "7=5*6 ", 2, 2, 9, 9));
				     // Ghi chú
				     mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row1, "Ghi chú", 1, 3, 10, 10));
//				        //D
//				        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "D",  2, 2, 10, 10));


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
				int startRowIndex = 5;
//				row = sheet.createRow(4);
//				int colIndex = 0;
//				ExcelUtils.createCell(row, colIndex++, "I".toString(), style, sheet);
//				ExcelUtils.createCell(row, colIndex++, "Đơn vị mua", style, sheet);
				ArrayList<QlnvKhvonphiBcaoKquaThienNhapMuaHang> lstCTietBCao = (ArrayList<QlnvKhvonphiBcaoKquaThienNhapMuaHang>) data.getLstCTietBCao();
				for (QlnvKhvonphiBcaoKquaThienNhapMuaHang line : lstCTietBCao) {

					row = sheet.createRow(startRowIndex++);
					int colIndex = 0;
					// stt
					ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getMaVtu().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getMaDviTinh().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getSoQd().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getKhSoLuong().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getKhGiaMuaTd().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getKhTtien().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getThSoLuong().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getThGiaMuaTd().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getThTtien().toString(), style, sheet);
					ExcelUtils.createCell(row, colIndex++, line.getGhiChu().toString(), style, sheet);
				}
			}

}
