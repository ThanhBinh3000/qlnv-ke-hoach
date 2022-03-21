package com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.service.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgExportServiceImpl;
import com.tcdt.qlnvkhoachphi.service.quyettoanvonphi.QlnvKhvonphiQtoanDtqgService;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqg;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqg;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.exporter.ExportFactory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiQdGiaoDtChiNsnnExportServiceImpl implements
	QlnvKhvonphiQdGiaoDtChiNsnnExportService{

	@Autowired
	private QlnvKhvonphiQdGiaoDtChiNsnnService qlnvKhvonphiQdGiaoDtChiNsnnService;

	@Autowired
	private ExportFactory exportFactory;

	@Override
	public Boolean exportToExcel(HttpServletResponse response, List<String> types,
			QlnvKhvonphiQdGiaoDtChiNsnnSearchReq objReq) throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			Page<QlnvKhvonphiQdGiaoDtChiNsnn> data = qlnvKhvonphiQdGiaoDtChiNsnnService.colection(objReq);

			if (CollectionUtils.isEmpty(types)) {
				types = new LinkedList<>();
				types.add(Constants.ExportDataType.QD_GIAO_DTOAN_CHI);
			}

			for (String type : types) {

				exportFactory.getQdGiaoDtChiExportService(type).export(workbook, data);
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
