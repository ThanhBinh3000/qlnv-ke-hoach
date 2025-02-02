package com.tcdt.qlnvkhoach.repository.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhDnCapVonBoNganhRepositoryCustom {
	Page<KhDnCapVonBoNganhSearchResponse> search(KhDnCapVonBoNganhSearchRequest req, Pageable pageable);


	List<KhDnCapVonBoNganhSearchResponse>  loadDataThTCDT(KhDnThCapVonSearchRequest req);
}
