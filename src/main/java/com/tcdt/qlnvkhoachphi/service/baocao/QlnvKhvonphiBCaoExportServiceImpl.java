package com.tcdt.qlnvkhoachphi.service.baocao;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tcdt.qlnvkhoachphi.response.baocao.QlnvKhvonphiBcaoResp;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.exporter.ExportFactory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiBCaoExportServiceImpl implements QlnvKhvonphiBCaoExportService{

	@Autowired
	private IQlnvKhvonphiBCaoService qlnvKhvonphiBCaoService;

	@Autowired
	private ExportFactory exportFactory;

	@Override
	public Boolean exportToExcel(HttpServletResponse response, List<String> types, Long id) throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			QlnvKhvonphiBcaoResp data = qlnvKhvonphiBCaoService.getDetail(id);

			if (data == null)
				return false;

//			if (CollectionUtils.isEmpty(types)) {
//				types = new LinkedList<>();
//				types.add(Constants.ExportDataType.QLNV_KHVONPHI_BC);
//			}

			for (String type : types) {
				exportFactory.getBCaoExportService(type).exportBCao(workbook, data);
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
