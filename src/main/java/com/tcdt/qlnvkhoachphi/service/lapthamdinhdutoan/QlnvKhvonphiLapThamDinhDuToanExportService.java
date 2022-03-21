package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface QlnvKhvonphiLapThamDinhDuToanExportService {
	
	Boolean exportToExcel (HttpServletResponse response, List<String> type, Long id) throws Exception;

}


