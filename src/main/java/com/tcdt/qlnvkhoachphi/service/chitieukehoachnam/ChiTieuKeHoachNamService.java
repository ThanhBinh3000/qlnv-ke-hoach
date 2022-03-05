package com.tcdt.qlnvkhoachphi.service.chitieukehoachnam;


import com.tcdt.qlnvkhoachphi.request.StatusReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;

public interface ChiTieuKeHoachNamService {
	ChiTieuKeHoachNamRes create(ChiTieuKeHoachNamReq req) throws Exception;
	ChiTieuKeHoachNamRes update(ChiTieuKeHoachNamReq req) throws Exception;
	boolean delete(Long id) throws Exception;
	ChiTieuKeHoachNamRes detail(Long id) throws Exception;
	boolean updateStatus(StatusReq req) throws Exception;
}
