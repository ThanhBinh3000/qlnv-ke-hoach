package com.tcdt.qlnvkhoachphi.service;

import com.tcdt.qlnvkhoachphi.util.exporter.CtkhnKeHoachLuongThucExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.CtkhnKeHoachMuoiExporter;
import com.tcdt.qlnvkhoachphi.util.exporter.CtkhnKeHoachVatTuExporter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class ChiTieuKeHoachNamExportServiceImpl implements ChiTieuKeHoachNamExportService {
	@Autowired
	private CtkhnKeHoachLuongThucExporter ctkhnKeHoachLuongThucExporter;

	@Autowired
	private CtkhnKeHoachMuoiExporter ctkhnKeHoachMuoiExporter;

	@Autowired
	private CtkhnKeHoachVatTuExporter ctkhnKeHoachVatTuExporter;

	@Override
	public Boolean exportToExcel(HttpServletResponse response) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			ctkhnKeHoachLuongThucExporter.export(workbook);
			ctkhnKeHoachMuoiExporter.export(workbook);
			ctkhnKeHoachVatTuExporter.export(workbook);

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
}
