package com.tcdt.qlnvkhoachphi.service.capvonmuaban;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface QlnvKhvonphiCapvonMuaBanTtoanThangDtqgExportService {
	
	Boolean exportToExcel (HttpServletResponse response, List<String> type, Long id) throws Exception;

}
