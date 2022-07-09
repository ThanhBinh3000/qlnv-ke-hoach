package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import org.springframework.data.domain.Page;

public interface KhLtPhuongAnGiaRepositoryCustom {
	Page<ChiTieuKeHoachNamRes> search(SearchChiTieuKeHoachNamReq req, String userCapDvi);

	int countCtkhn(SearchChiTieuKeHoachNamReq req);
}
