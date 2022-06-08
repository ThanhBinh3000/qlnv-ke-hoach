package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import org.springframework.data.domain.Page;

public interface ChiTieuKeHoachNamRepositoryCustom {
	Page<ChiTieuKeHoachNamRes> search (SearchChiTieuKeHoachNamReq req);

	int countCtkhn(SearchChiTieuKeHoachNamReq req);
}
