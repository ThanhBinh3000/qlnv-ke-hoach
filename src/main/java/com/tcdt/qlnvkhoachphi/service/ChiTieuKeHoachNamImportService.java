package com.tcdt.qlnvkhoachphi.service;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ListKeHoachRes;
import org.springframework.web.multipart.MultipartFile;

public interface ChiTieuKeHoachNamImportService {
    ListKeHoachRes importKeHoach(MultipartFile file);
}
