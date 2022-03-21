package com.tcdt.qlnvkhoachphi.service.quyettoanvonphi;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.tcdt.qlnvkhoachphi.request.search.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqgSearchReq;

public interface QlnvKhvonphiQtoanDtqgExportService {
	Boolean exportToExcel (HttpServletResponse response, List<String> type, QlnvKhvonphiQtoanDtqgSearchReq objReq) throws Exception;
}
