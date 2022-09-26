package com.tcdt.qlnvkhoach.service.denghicapphibonganh;

import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhRequest;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhResponse;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhSearchResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface KhDnCapPhiBoNganhService {
	KhDnCapPhiBoNganhResponse create(KhDnCapPhiBoNganhRequest req) throws Exception;

	KhDnCapPhiBoNganhResponse update(KhDnCapPhiBoNganhRequest req) throws Exception;

	boolean delete(Long id) throws Exception;

	boolean deleteMultiple(List<Long> ids) throws Exception;

	Page<KhDnCapPhiBoNganhSearchResponse> search(KhDnCapPhiBoNganhSearchRequest req) throws Exception;

	KhDnCapPhiBoNganhResponse detail(Long id) throws Exception;

	KhDnCapPhiBoNganhResponse updateTrangThai(Long id, String trangThaiId) throws Exception;

	boolean exportToExcel(KhDnCapPhiBoNganhSearchRequest req, HttpServletResponse response) throws Exception;
}
