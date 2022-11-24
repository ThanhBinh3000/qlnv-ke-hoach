package com.tcdt.qlnvkhoach.service.chitieukehoachnam;

import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ListKeHoachRes;
import org.springframework.web.multipart.MultipartFile;

public interface ChiTieuKeHoachNamImportService {
    ListKeHoachRes importKeHoach(MultipartFile file);
}
