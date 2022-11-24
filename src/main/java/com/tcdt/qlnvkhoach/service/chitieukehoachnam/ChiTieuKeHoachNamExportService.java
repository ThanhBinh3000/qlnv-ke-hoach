package com.tcdt.qlnvkhoach.service.chitieukehoachnam;

import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ChiTieuKeHoachNamExportService {
	Boolean exportToExcel (HttpServletResponse response, List<String> type, Long id) throws Exception;

	Boolean exportListQdToExcel(HttpServletResponse response, SearchChiTieuKeHoachNamReq req) throws Exception;

	Boolean exportListQdDcToExcel(HttpServletResponse response, SearchChiTieuKeHoachNamReq req) throws Exception;
}
