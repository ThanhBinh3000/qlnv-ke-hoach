package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import org.springframework.web.multipart.MultipartFile;

import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiResp;

public interface QlnvKhvonphiLapThamDinhDuToanImportService {
	QlnvKhvonphiResp importExcel(MultipartFile file, String maLoaiBcao);
}
