package com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi;

import javax.servlet.http.HttpServletRequest;

import com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;

public interface QlnvKhvonphiQdGiaoDtChiNsnnService {
	public Resp detail(String ids);

	public Resp colectionPa(QlnvKhvonphiQdGiaoDtChiNsnnSearchReq objReq);

	public Resp create(QlnvKhvonphiQdGiaoDtChiNsnnReq objReq, HttpServletRequest req);

	public Resp delete(String ids, HttpServletRequest req);

	public Resp update(QlnvKhvonphiQdGiaoDtChiNsnnReq objReq, HttpServletRequest req);
}
