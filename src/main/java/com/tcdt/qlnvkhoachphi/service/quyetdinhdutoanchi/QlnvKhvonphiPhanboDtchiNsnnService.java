package com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi;

import javax.servlet.http.HttpServletRequest;

import com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;

public interface QlnvKhvonphiPhanboDtchiNsnnService {
	public Resp create(QlnvKhvonphiPhanboDtoanChiNsnnReq objReq,
			HttpServletRequest req);
	
	public Resp update(QlnvKhvonphiPhanboDtoanChiNsnnReq objReq,
			HttpServletRequest req);
	
	public Resp delete(String ids, HttpServletRequest req);
	
	public Resp detail(String ids);
	
	public Resp colection(QlnvKhvonphiPhanboDtoanChiNsnnSearchReq objReq,
			HttpServletRequest req);
	
	public Resp synthesis(String maDviThien, HttpServletRequest req);
}
