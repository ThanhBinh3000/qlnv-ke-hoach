package com.tcdt.qlnvkhoach.service.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhSearchRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhResponse;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhSearchResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface KhDnCapVonBoNganhService {
	KhDnCapVonBoNganhResponse create(KhDnCapVonBoNganhRequest req) throws Exception;

	KhDnCapVonBoNganhResponse update(KhDnCapVonBoNganhRequest req) throws Exception;

	boolean delete(Long id) throws Exception;

	boolean deleteMultiple(List<Long> ids) throws Exception;

	Page<KhDnCapVonBoNganhSearchResponse> search(KhDnCapVonBoNganhSearchRequest req) throws Exception;

	//code vao day
	List<KhDnCapVonBoNganhSearchResponse> loadDataThTCDT(KhDnThCapVonSearchRequest req) throws Exception;

	KhDnCapVonBoNganhResponse detail(Long id) throws Exception;

	KhDnCapVonBoNganhResponse updateTrangThai(Long id, String trangThaiId) throws Exception;

	boolean exportToExcel(KhDnCapVonBoNganhSearchRequest req, HttpServletResponse response) throws Exception;
}
