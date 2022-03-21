package com.tcdt.qlnvkhoachphi.service.capvonmuaban;

import org.springframework.data.domain.Page;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp;
import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqg;

public interface QlnvKhvonphiCapvonMuaBanTtoanThangDtqgService {
	public QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp getDetail(Long id);

	public Page<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> getList(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq objReq);

	public QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp create(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq)
			throws Exception;

	public void delete(String idso) throws Exception;

	public QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp update(QlnvKhvonphiCapvonMuaBanTtoanThangDtqgReq objReq)
			throws Exception;

	public QlnvKhvonphiCapvonMuaBanTtoanThangDtqg function(NhomNutChucNangReq objReq) throws Exception;

}
