package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;

import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KhDnCapPhiBoNganhRepositoryCustom {
	Page<KhDnCapPhiBoNganhSearchResponse> search(KhDnCapPhiBoNganhSearchRequest req, Pageable pageable);
}
