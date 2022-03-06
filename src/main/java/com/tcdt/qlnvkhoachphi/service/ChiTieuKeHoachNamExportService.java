package com.tcdt.qlnvkhoachphi.service;

import com.tcdt.qlnvkhoachphi.request.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ChiTieuKeHoachNamExportService {
	Boolean exportToExcel (HttpServletResponse response, List<String> type, Long id) throws Exception;

	Page<ChiTieuKeHoachNamRes> search (SearchChiTieuKeHoachNamReq req, Pageable pageable) throws Exception;
}
