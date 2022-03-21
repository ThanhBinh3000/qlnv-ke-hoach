package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import org.springframework.web.multipart.MultipartFile;

import com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp;

public interface QlnvKhvonphiPaGiaoSoKiemTraTcNsnnImportService {
	public QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp importExcel(MultipartFile file);
}
