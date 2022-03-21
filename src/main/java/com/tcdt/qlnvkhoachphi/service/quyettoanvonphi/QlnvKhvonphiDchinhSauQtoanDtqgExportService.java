package com.tcdt.qlnvkhoachphi.service.quyettoanvonphi;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;

import com.tcdt.qlnvkhoachphi.request.search.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqg;

public interface QlnvKhvonphiDchinhSauQtoanDtqgExportService {
	Boolean exportToExcel(HttpServletResponse response, List<String> type, QlnvKhvonphiDchinhSauQtoanDtqgSearchReq objReq) throws Exception;
}
