package com.tcdt.qlnvkhoach.repository.thongtriduyetydutoan;

import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.request.thongtriduyetydutoan.TtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhSearchResponse;
import com.tcdt.qlnvkhoach.response.thongtriduyetydutoan.TtDuyetYDuToanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TtDuyetYDuToanRepositoryCustom {
	List<TtDuyetYDuToanResponse> search(TtDuyetYDuToanSearchRequest req);
}
