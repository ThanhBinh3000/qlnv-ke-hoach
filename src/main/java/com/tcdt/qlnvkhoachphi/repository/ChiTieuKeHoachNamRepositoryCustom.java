package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChiTieuKeHoachNamRepositoryCustom {
	Page<ChiTieuKeHoachNamRes> search (SearchChiTieuKeHoachNamReq req, Pageable pageable);
}
