package com.tcdt.qlnvkhoachphi.service.dieuchinhdutoan;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tcdt.qlnvkhoachphi.response.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnResp;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.exporter.ExportFactory;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class QlnvKhvonphiDchinhDuToanChiNsnnExportServiceImlp implements QlnvKhvonphiDchinhDuToanChiNsnnExportService{

	@Autowired
	private ExportFactory exportFactory;

	@Autowired
	private IQlnvKhvonphiDchinhDuToanChiNsnnService qlnvKhvonphiDchinhDuToanChiNsnnService;

	@Override
	public Boolean exportToExcel(HttpServletResponse response, List<String> types, Long id) throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			QlnvKhvonphiDchinhDuToanChiNsnnResp data = qlnvKhvonphiDchinhDuToanChiNsnnService.getDetail(id);

			if (data == null)
				return false;

			if (CollectionUtils.isEmpty(types)) {
				types = new LinkedList<>();
				types.add(Constants.ExportDataType.QLNV_KHVONPHI_DC_DU_TOAN_CHI);
			}

			for (String type : types) {
				exportFactory.getDchinhDuToanChiExportService(type).exportDchinhDuToanChi(workbook, data);
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

}
