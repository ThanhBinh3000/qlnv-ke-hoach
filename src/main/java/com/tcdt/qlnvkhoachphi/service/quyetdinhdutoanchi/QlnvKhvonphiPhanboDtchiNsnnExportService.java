package com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnSearchReq;

public interface QlnvKhvonphiPhanboDtchiNsnnExportService {
	Boolean exportToExcel (HttpServletResponse response, List<String> type, QlnvKhvonphiPhanboDtoanChiNsnnSearchReq data) throws Exception;
}
