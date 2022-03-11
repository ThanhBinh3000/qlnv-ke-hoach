package com.tcdt.qlnvkhoachphi.service.baocaodutoanchi;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.baocaodutoanchi.QlnvKhvonphiBcaoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.baocaodutoanchi.TongHopBcaoReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.baocaodutoanchi.QlnvKhvonphiBcaoSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.table.UserInfo;

public interface IQlnvKhvonphiBCaoService {
	public Resp getDetail(String ids);

	public Resp getList(QlnvKhvonphiBcaoSearchReq objReq);

	public Resp add(QlnvKhvonphiBcaoReq objReq, UserInfo userInfo);

	public Resp synthetic(TongHopBcaoReq objReq);

	public Resp remove(String ids, UserInfo userInfo);

	public Resp update(QlnvKhvonphiBcaoReq qlnvBaoCaoReq, UserInfo userInfo);

	public Resp validate(QlnvKhvonphiBcaoReq qlnvBaoCaoReq);

	public Resp genMaBcao();

	public Resp function(NhomNutChucNangReq objReq,UserInfo userInfo);
}
