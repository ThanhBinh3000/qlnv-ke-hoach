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

import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnResp;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnn;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.exporter.ExportFactory;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class QlnvKhvonphiPhanboDtchiNsnnExportServiceImpl implements
	QlnvKhvonphiPhanboDtchiNsnnExportService{

	@Autowired
	private QlnvKhvonphiPhanboDtchiNsnnService qlnvKhvonphiPhanboDtchiNsnnService;
	
	@Autowired
	private ExportFactory exportFactory;
	
	@Override
	public Boolean exportToExcel(HttpServletResponse response, List<String> types, QlnvKhvonphiPhanboDtoanChiNsnnSearchReq objReq) throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			Page<QlnvKhvonphiPhanboDtoanchiNsnn> data = qlnvKhvonphiPhanboDtchiNsnnService.colection(objReq);

			if (data == null) {
				workbook.close();
				return false;
			}
				
			if (CollectionUtils.isEmpty(types)) {
				types = new LinkedList<>();
				types.add(Constants.QLNV_KHVONPHI_DM_VONDT_XDCBGD3N);//01
				types.add(Constants.QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU);//02
			}

			for (String type : types) {
				exportFactory.getPhanboDtoanChiExportService(type).export(workbook, data);
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
