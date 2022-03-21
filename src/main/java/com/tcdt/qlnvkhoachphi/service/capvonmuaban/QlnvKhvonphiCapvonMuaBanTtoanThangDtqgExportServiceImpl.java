package com.tcdt.qlnvkhoachphi.service.capvonmuaban;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tcdt.qlnvkhoachphi.controller.BaseController;
import com.tcdt.qlnvkhoachphi.repository.catalog.FileDinhKemRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository;
import com.tcdt.qlnvkhoachphi.repository.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository;
import com.tcdt.qlnvkhoachphi.response.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp;
import com.tcdt.qlnvkhoachphi.service.QlnvDmService;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.exporter.ExportFactory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgExportServiceImpl extends BaseController
		implements QlnvKhvonphiCapvonMuaBanTtoanThangDtqgExportService {

	@Autowired
	private QlnvDmService qlnvDmService;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;
	
	@Autowired
	private QlnvKhvonphiCapvonMuaBanTtoanThangDtqgService qlnvKhvonphiCapvonMuaBanTtoanThangDtqgService;

	@Autowired
	private QlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository qlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository;

	@Autowired
	private QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository qlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository;

	@Autowired
	private ExportFactory exportFactory;
	
	@Override
	public Boolean exportToExcel(HttpServletResponse response, List<String> types, Long id) throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp data = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgService.getDetail(id);

			if (data == null)
				return false;

			if (CollectionUtils.isEmpty(types)) {
				types = new LinkedList<>();
				types.add(Constants.ExportDataType.CHI_TIEU_LUONG_THUC);
				types.add(Constants.ExportDataType.CHI_TIEU_MUOI);
				types.add(Constants.ExportDataType.CHI_TIEU_VAT_TU);
			}

			for (String type : types) {
				exportFactory.getKHoachVonExportService(type).export(workbook, data);
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
