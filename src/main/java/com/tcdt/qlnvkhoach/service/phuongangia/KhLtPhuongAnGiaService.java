package com.tcdt.qlnvkhoach.service.phuongangia;

import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;

public interface KhLtPhuongAnGiaService {
	KhLtPhuongAnGiaRes create(KhLtPhuongAnGiaReq req) throws Exception;

	KhLtPhuongAnGiaRes update(KhLtPhuongAnGiaReq req) throws Exception;
//
//	Page<KhLtPhuongAnGiaRes> search(SearchKhLtPhuongAnGiaReq req) throws Exception;
//
//	void delete(List<Long> ids);
}
