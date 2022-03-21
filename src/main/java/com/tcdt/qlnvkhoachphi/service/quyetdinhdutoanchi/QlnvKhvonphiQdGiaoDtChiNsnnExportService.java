package com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnSearchReq;

public interface QlnvKhvonphiQdGiaoDtChiNsnnExportService {
	Boolean exportToExcel(HttpServletResponse response, List<String> type, QlnvKhvonphiQdGiaoDtChiNsnnSearchReq objReq) throws Exception;
}
