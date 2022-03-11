package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiLapThamDinhDuToanReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.TongHopReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiLapThamDinhDuToanSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.table.UserInfo;

public interface QlnvKhvonphiLapThamDinhDuToanService {
	public Resp getDetail(String ids);

	public Resp getList(QlnvKhvonphiLapThamDinhDuToanSearchReq objReq);

	public Resp create(QlnvKhvonphiLapThamDinhDuToanReq objReq, UserInfo userInfo);

	public Resp synthetic(TongHopReq objReq);

	public Resp delete(String ids, UserInfo userInfo);

	public Resp update(QlnvKhvonphiLapThamDinhDuToanReq qlnvBaoCaoReq, UserInfo userInfo);

	public Resp validate(QlnvKhvonphiLapThamDinhDuToanReq qlnvBaoCaoReq);
}
