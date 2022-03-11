package com.tcdt.qlnvkhoachphi.service.ketquathuchienvonphi;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.baocaodutoanchi.QlnvKhvonphiBcaoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.ketquathuchienvonphi.TongHopBcaoKquaThienReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.table.UserInfo;

public interface IQlnvKhvonphiBcaoKquaThienService {
	public Resp getDetail(String ids);

	public Resp getList(QlnvKhvonphiBcaoKquaThienSearchReq objReq);

	public Resp add(QlnvKhvonphiBcaoReq objReq, UserInfo userInfo);

	public Resp synthetic(TongHopBcaoKquaThienReq objReq);

	public Resp remove(String ids, UserInfo userInfo);

	public Resp update(QlnvKhvonphiBcaoReq qlnvBaoCaoReq, UserInfo userInfo);

	public Resp validate(QlnvKhvonphiBcaoReq qlnvBaoCaoReq);

	public Resp genMaBcao();

	public Resp function(NhomNutChucNangReq objReq,UserInfo userInfo);
}
