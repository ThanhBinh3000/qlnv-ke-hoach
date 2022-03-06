package com.tcdt.qlnvkhoachphi.service.chitieukehoachnam;


import com.tcdt.qlnvkhoachphi.request.StatusReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.ChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam.QdDcChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.QdDcChiTieuKeHoachRes;

import javax.transaction.Transactional;

public interface ChiTieuKeHoachNamService {
	@Transactional(rollbackOn = Exception.class)
	ChiTieuKeHoachNamRes createQd(ChiTieuKeHoachNamReq req) throws Exception;

	@Transactional(rollbackOn = Exception.class)
	QdDcChiTieuKeHoachRes createQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception;

	@Transactional(rollbackOn = Exception.class)
	ChiTieuKeHoachNamRes updateQd(ChiTieuKeHoachNamReq req) throws Exception;

	@Transactional(rollbackOn = Exception.class)
	QdDcChiTieuKeHoachRes updateQdDc(QdDcChiTieuKeHoachNamReq req) throws Exception;

	boolean delete(Long id) throws Exception;

	ChiTieuKeHoachNamRes detailQd(Long id) throws Exception;

	QdDcChiTieuKeHoachRes detailQdDc(Long id) throws Exception;

	boolean updateStatus(StatusReq req) throws Exception;
}
