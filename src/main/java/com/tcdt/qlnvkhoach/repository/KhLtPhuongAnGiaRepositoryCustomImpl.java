package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.request.phuongangia.SearchKhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;
import org.springframework.data.domain.Page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class KhLtPhuongAnGiaRepositoryCustomImpl implements KhLtPhuongAnGiaRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<KhLtPhuongAnGiaRes> search(SearchKhLtPhuongAnGiaReq req, String userCapDvi) {
		return null;
	}
}
