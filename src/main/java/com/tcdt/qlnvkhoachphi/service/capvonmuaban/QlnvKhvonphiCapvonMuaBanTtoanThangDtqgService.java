package com.tcdt.qlnvkhoachphi.service.capvonmuaban;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.table.UserInfo;

public interface QlnvKhvonphiCapvonMuaBanTtoanThangDtqgService {
	public Resp getDetail(String ids);

	public Resp getList(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq objReq);

	public Resp create(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq, UserInfo userInfo);

	public Resp delete(String ids, UserInfo userInfo);

	public Resp update(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq, UserInfo userInfo);
	
	public Resp function(NhomNutChucNangReq objReq, UserInfo userInfo);
	
}
