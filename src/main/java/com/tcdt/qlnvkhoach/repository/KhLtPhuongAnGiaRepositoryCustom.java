package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.request.phuongangia.SearchKhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;
import org.springframework.data.domain.Page;

public interface KhLtPhuongAnGiaRepositoryCustom {
	Page<KhLtPhuongAnGiaRes> search(SearchKhLtPhuongAnGiaReq req, String userCapDvi);

}
