package com.tcdt.qlnvkhoachphi.service;

import com.tcdt.qlnvkhoachphi.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoachphi.request.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.util.Constants;
import com.tcdt.qlnvkhoachphi.util.exporter.ExportFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	@Autowired
	private ExportFactory exportFactory;

	@Autowired
	private ChiTieuKeHoachNamRepository chiTieuKeHoachNamRepo;

	@Override
	public Boolean exportToExcel(HttpServletResponse response, List<String> types) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			if (CollectionUtils.isEmpty(types)) {
				types = new LinkedList<>();
				types.add(Constants.ExportDataType.CHI_TIEU_LUONG_THUC);
				types.add(Constants.ExportDataType.CHI_TIEU_MUOI);
				types.add(Constants.ExportDataType.CHI_TIEU_VAT_TU);
			}

			for (String type : types) {
				exportFactory.getExportService(type).export(workbook);
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
	public Page<ChiTieuKeHoachNamRes> search(SearchChiTieuKeHoachNamReq req, Pageable pageable) {

		return chiTieuKeHoachNamRepo.search(req, pageable);
	}

}
