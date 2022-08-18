package com.tcdt.qlnvkhoach.service.phuongangia;

import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;

import java.util.List;

public interface KhLtPhuongAnGiaService {
	KhLtPhuongAnGiaRes create(KhLtPhuongAnGiaReq req) throws Exception;

	KhLtPhuongAnGiaRes update(KhLtPhuongAnGiaReq req) throws Exception;
	boolean deleteMultiple(List<Long> ids) throws Exception;


}
