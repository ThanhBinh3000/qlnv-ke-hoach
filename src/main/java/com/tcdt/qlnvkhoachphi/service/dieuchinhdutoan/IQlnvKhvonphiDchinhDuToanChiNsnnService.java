package com.tcdt.qlnvkhoachphi.service.dieuchinhdutoan;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.TongHopDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDchinhDuToanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.table.UserInfo;

public interface IQlnvKhvonphiDchinhDuToanChiNsnnService {
	public Resp getDetail(String ids);

	public Resp getList(QlnvKhvonphiDchinhDuToanChiNsnnSearchReq objReq);

	public Resp add(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq, UserInfo userInfo);

	public Resp synthetic(TongHopDchinhDuToanChiNsnnReq objReq);

	public Resp remove(String ids, UserInfo userInfo);

	public Resp update(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq, UserInfo userInfo);

	public Resp validate(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq);

	public Resp function(NhomNutChucNangReq objReq,UserInfo userInfo);
}
