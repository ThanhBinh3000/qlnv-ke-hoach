package com.tcdt.qlnvkhoachphi.service;

import com.tcdt.qlnvkhoachphi.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoachphi.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoachphi.enums.ChiTieuKeHoachNamStatus;
import com.tcdt.qlnvkhoachphi.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.ExcelUtils;
import com.tcdt.qlnvkhoachphi.util.LocalDateTimeUtils;
import com.tcdt.qlnvkhoachphi.util.exporter.ExportFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ChiTieuKeHoachNamExportServiceImpl implements ChiTieuKeHoachNamExportService {

	private static final String SHEET_CHI_TIEU_KE_HOACH_NAM = "Chỉ tiêu kế hoạch năm";
	private static final String STT = "STT";
	private static final String SO_QUYET_DINH = "Số Quyết Định";
	private static final String NGAY_KY = "Ngày ký";
	private static final String NAM_KE_HOACH = "Năm Kế Hoạch";
	private static final String TRICH_YEU = "Trích Yếu";
	private static final String TRANG_THAI = "Trạng Thái";

	@Autowired
	private ExportFactory exportFactory;

	@Autowired
	private ChiTieuKeHoachNamService chiTieuKeHoachNamSv;

	@Autowired
	private ChiTieuKeHoachNamRepository chiTieuKeHoachNamRepository;

	@Override
	public Boolean exportToExcel(HttpServletResponse response, List<String> types, Long id) throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			ChiTieuKeHoachNamRes data = chiTieuKeHoachNamSv.detailQd(id);

			if (data == null) return false;

			if (CollectionUtils.isEmpty(types)) {
				types = new LinkedList<>();
				types.add(Constants.ExportDataType.CHI_TIEU_LUONG_THUC);
				types.add(Constants.ExportDataType.CHI_TIEU_MUOI);
				types.add(Constants.ExportDataType.CHI_TIEU_VAT_TU);
			}

			for (String type : types) {
				exportFactory.getExportService(type).export(workbook, data);
			}

			ServletOutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (IOException e) {
			log.error("Error export", e);
			return false;
		}
		return true;
	}

	@Override
	public Boolean exportListQdToExcel(HttpServletResponse response) {
		List<ChiTieuKeHoachNam> list = chiTieuKeHoachNamRepository.findAllByLoaiQuyetDinh(ChiTieuKeHoachEnum.QD.getValue());
		return exportListToExcel(response, list);
	}

	@Override
	public Boolean exportListQdDcToExcel(HttpServletResponse response) {
		List<ChiTieuKeHoachNam> list = chiTieuKeHoachNamRepository.findAllByLoaiQuyetDinh(ChiTieuKeHoachEnum.QD_DC.getValue());
		return exportListToExcel(response, list);
	}

	public Boolean exportListToExcel(HttpServletResponse response, List<ChiTieuKeHoachNam> list) {

		if (CollectionUtils.isEmpty(list))
			return true;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			//STYLE
			CellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setFontHeight(11);
			font.setBold(true);
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			XSSFSheet sheet = workbook.createSheet(SHEET_CHI_TIEU_KE_HOACH_NAM);
			Row row0 = sheet.createRow(0);
			//STT

			ExcelUtils.createCell(row0, 0, STT, style, sheet);
			ExcelUtils.createCell(row0, 1, SO_QUYET_DINH, style, sheet);
			ExcelUtils.createCell(row0, 2, NGAY_KY, style, sheet);
			ExcelUtils.createCell(row0, 3, NAM_KE_HOACH, style, sheet);
			ExcelUtils.createCell(row0, 4, TRICH_YEU, style, sheet);
			ExcelUtils.createCell(row0, 5, TRANG_THAI, style, sheet);

			style = workbook.createCellStyle();
			font = workbook.createFont();
			font.setFontHeight(11);
			style.setFont(font);

			Row row;
			int startRowIndex = 1;

			for (ChiTieuKeHoachNam item : list) {
				row = sheet.createRow(startRowIndex);
				ExcelUtils.createCell(row, 0, startRowIndex, style, sheet);
				ExcelUtils.createCell(row, 1, item.getSoQuyetDinh(), style, sheet);
				ExcelUtils.createCell(row, 2, LocalDateTimeUtils.localDateToString(item.getNgayKy()), style, sheet);
				ExcelUtils.createCell(row, 3, item.getNamKeHoach(), style, sheet);
				ExcelUtils.createCell(row, 4, item.getTrichYeu(), style, sheet);
				ExcelUtils.createCell(row, 5, ChiTieuKeHoachNamStatus.getTenById(item.getTrangThai()), style, sheet);
				startRowIndex++;
			}

			ServletOutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (Exception e) {
			log.error("Error export", e);
			return false;
		}
		return null;
	}
}
