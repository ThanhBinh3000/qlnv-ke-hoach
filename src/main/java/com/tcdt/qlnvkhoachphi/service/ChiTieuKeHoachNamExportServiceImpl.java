package com.tcdt.qlnvkhoachphi.service;

import com.tcdt.qlnvkhoachphi.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoachphi.request.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
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

	@Autowired
	private ChiTieuKeHoachNamService chiTieuKeHoachNamSv;

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
	public Page<ChiTieuKeHoachNamRes> search(SearchChiTieuKeHoachNamReq req, Pageable pageable) throws Exception {

		UserInfo userInfo = SecurityContextService.getUser();
		if (userInfo == null)
			throw new Exception("Bad request");

		req.setDonViId(userInfo.getDvql());
		return chiTieuKeHoachNamRepo.search(req, pageable);
	}

}
