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
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcaoThinhSdungDtoanPl3;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QlnvKhvonphiBcaoThinhSdungDtoanPl3Exporter implements BCaoExportService{@Override
	public void exportBCao(XSSFWorkbook workbook, QlnvKhvonphiBcaoResp data) {
	if (data.getLstCTietBCao() == null) {
		log.info("Không có data báo cáo kết quả giải ngân dự toán chi NSNN định kỳ tháng/năm");
		throw new IllegalArgumentException(
				"Không có data báo cáo kết quả giải ngân dự toán chi NSNN định kỳ tháng/năm");
	}
	XSSFSheet sheet = workbook.createSheet(Constants.ChiTieuKeHoachNamExport.SHEET_QLNV_KHVONPHI_BAO_CAO);

	writeHeaderLine(workbook, sheet, data);
	writeDataLines(sheet, workbook, data);

	}

private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet, QlnvKhvonphiBcaoResp data) {
	// STYLE
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
	Row row2 = sheet.createRow(2);

	Row row4 = sheet.createRow(4);


	List<MergeCellObj> mergeCellHeaderRow = new LinkedList<>();

	// STT
	mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, Constants.ExcelHeader.STT, 0, 7, 0, 0));

	// Danh mục dự án
	mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Đơn vị-Nội dung", 0, 7, 1, 1));

	// Địa điểm xây dựng
    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Đơn vị-Nội dung", 0, 7, 2, 2));




    // Quyết định đầu tư
    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Đơn vị-Nội dung", 0, 1, 3, 5));
         // Số quyết định - ngày tháng năm ban hành
         mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Số quyết định - ngày tháng năm ban hành", 2, 7, 3, 3));

         // TMDT
         mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "TMDT", 2 ,3, 4, 5));

             // Tổng số(Tất cả các nguồn vốn)
             mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Tổng số(Tất cả các nguồn vốn)",4, 7, 4, 4));

             // Trong đó: vốn NSNN
             mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Trong đó: vốn NSNN", 4, 7, 5, 5));


    // Lũy kế vốn đã bố trí đến hết năm trước năm kế hoạch
    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Lũy kế vốn đã bố trí đến hết năm trước năm kế hoạch", 0, 1, 6, 7));
         // Tổng số(tất cả các nguồn vốn)
         mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng số(tất cả các nguồn vốn)", 2, 7, 6, 6));

         // Trong đó:vốn NSNN
         mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó:vốn NSNN", 2 ,7, 7, 7));

    // Lũy kế giải ngân vốn đã bố trí đến hết năm trước năm kế hoạch
	mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0,
			"Lũy kế giải ngân vốn đã bố trí đến hết năm trước năm kế hoạch", 0, 1, 8, 10));
	     // Tổng số(tất cả các nguồn vốn)
         mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng số(tất cả các nguồn vốn)", 2, 7, 8, 8));

         // Trong đó:vốn NSNN
         mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó:vốn NSNN", 2 ,3, 9, 10));
            // Tổng số
            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Tổng số", 4 ,7, 9, 9));

            // Trong đó:KH năm trước
            mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Trong đó:KH năm trước", 4 ,7, 10, 10));


    // Kế hoạch vốn đầu tư phát triển nguồn NSNN năm trước được cấp có thẩm quyền cho phép kéo dài sang năm KH(nếu có)
	mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0,
			"Kế hoạch vốn đầu tư phát triển nguồn NSNN năm trước được cấp có thẩm quyền cho phép kéo dài sang năm KH(nếu có)",
			0, 7, 11, 11));

	// Kế hoạch năm kế hoạch
    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Kế hoạch năm kế hoạch", 0, 1, 12, 13));
        // Tổng số(tất cả các nguồn vốn)
        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng số(tất cả các nguồn vốn)", 2, 7, 12, 12));

        // Trong đó:vốn NSNN
        mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó:vốn NSNN", 2 ,7, 13, 13));


    // Khối lượng thực hiện từ đầu năm đến ngày 20 của tháng báo cáo
    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Khối lượng thực hiện từ đầu năm đến ngày 20 của tháng báo cáo", 0, 7, 14, 14));

	// Giải ngân tháng báo cáo
    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Tình hình thực hiện dự án", 0, 3, 15, 18));
       // Tổng số(tất cả các nguồn vốn)
       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng số(tất cả các nguồn vốn)", 4, 5, 15, 16));
          // Số tiền
          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Số tiền", 6, 7, 15, 15));

          // Tỷ lệ(%)
          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tỷ lệ(%)", 6, 7, 16, 16));

       // Trong đó:vốn NSNN
       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó:vốn NSNN", 4 ,5, 17, 18));
          // Số tiền
          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Số tiền", 6, 7, 17, 17));

          // Tỷ lệ(%)
          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tỷ lệ(%)", 6, 7, 18, 18));

	// Lũy kế giải ngân từ đầu năm đến tháng báo cáo đối với báo cáo tháng (đến hết
	// thời gian chỉnh lý quyết toán ngân sách-ngày 31/1 năm sau đối với báo cáo
	// năm)
	mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0,
			"Lũy kế giải ngân từ đầu năm đến tháng báo cáo đối với báo cáo tháng (đến hết thời gian chỉnh lý quyết toán ngân sách-ngày 31/1 năm sau đối với báo cáo năm)",
			0, 3, 19, 22));
	   // Tổng số(tất cả các nguồn vốn)
       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tổng số(tất cả các nguồn vốn)", 4, 5, 19, 20));
          // Số tiền
          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Số tiền", 6, 7, 19, 19));

          // Tỷ lệ(%)
          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tỷ lệ(%)", 6, 7, 20, 20));

       // Trong đó:vốn NSNN
       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Trong đó:vốn NSNN", 4 ,5, 21, 22));
          // Số tiền
          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Số tiền", 6, 7, 21, 21));

          // Tỷ lệ(%)
          mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row2, "Tỷ lệ(%)", 6, 7, 22, 22));

	// Tình hình thực hiện dự án
    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Tình hình thực hiện dự án", 0, 3, 23, 25));
       // Nội dung các công việc chủ yếu đã hoàn thành đến cuối tháng báo cáo
       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nội dung các công việc chủ yếu đã hoàn thành đến cuối tháng báo cáo", 4, 7, 23, 23));

       // Nội dung các công việc chủ yếu đang thực hiện dở dang
       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Nội dung các công việc chủ yếu đang thực hiện dở dang", 4, 7, 24, 24));

       // Kế hoạch thực hiện các nội dung công việc chủ yếu các tháng còn lại của năm
       mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row4, "Kế hoạch thực hiện các nội dung công việc chủ yếu các tháng còn lại của năm", 4, 7, 25, 25));

    mergeCellHeaderRow.add(ExcelUtils.buildMergeCell(row0, "Ghi chú", 0, 7, 26, 26));



	for (MergeCellObj mergeCellObj : mergeCellHeaderRow) {
		CellRangeAddress cellRangeAddress = mergeCellObj.getCellAddresses();
		sheet.addMergedRegion(cellRangeAddress);
		ExcelUtils.createCell(mergeCellObj.getRow(), cellRangeAddress.getFirstColumn(), mergeCellObj.getValue(),
				style, sheet);
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
	int startRowIndex = 8;
	ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl3> lstCTietBCao = (ArrayList<QlnvKhvonphiBcaoThinhSdungDtoanPl3>) data
			.getLstCTietBCao();

	for (QlnvKhvonphiBcaoThinhSdungDtoanPl3 line : lstCTietBCao) {
		row = sheet.createRow(startRowIndex++);
		int colIndex = 0;
		// stt
		ExcelUtils.createCell(row, colIndex++, line.getStt().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getMaDan().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getDdiemXdung().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getQddtSoQdinh().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getQddtTmdtTso().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getQddtTmdtNsnn().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getLuyKeVonTso().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getLuyKeVonNsnn().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganHetNamTso().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganHetNamNsnnTso().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganHetNamNsnnKhNamTruoc().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getKhoachVonDtu().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getKhoachTso().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getKhoachNsnn().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getKluongThien().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getGiaiNganTso().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getGiaiNganTsoTle().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getGiaiNganNsnn().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getGiaiNganNsnnTle().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganDauNamNsnnTso().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganDauNamNsnnTsoTle().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganDauNamNsnnNsnn().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getLuyKeGiaiNganDauNamNsnnNsnnTle().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getNdungCviecHthanhCuoiThang().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getNdungCviecDangThien().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getKhoachThienNdungCviecThangConLaiNam().toString(), style, sheet);
		ExcelUtils.createCell(row, colIndex++, line.getGhiChu().toString(), style, sheet);

	}


}

}
