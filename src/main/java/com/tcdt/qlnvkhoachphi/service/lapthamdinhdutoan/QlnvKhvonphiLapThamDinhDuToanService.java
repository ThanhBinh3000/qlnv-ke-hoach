package com.tcdt.qlnvkhoachphi.service.lapthamdinhdutoan;

import org.springframework.data.domain.Page;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.QlnvKhvonphiLapThamDinhDuToanReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.TongHopReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiLapThamDinhDuToanSearchReq;
import com.tcdt.qlnvkhoachphi.response.QlnvKhvonphiResp;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiLapThamDinhDuToan;

public interface QlnvKhvonphiLapThamDinhDuToanService {
	public QlnvKhvonphiResp getDetail(Long ids);

	public Page<QlnvKhvonphiLapThamDinhDuToan> getList(QlnvKhvonphiLapThamDinhDuToanSearchReq objReq) throws Exception;

	public QlnvKhvonphiResp create(QlnvKhvonphiLapThamDinhDuToanReq objReq) throws Exception;

	public Object synthetic(TongHopReq objReq);

	public void delete(String ids) throws Exception;

	public QlnvKhvonphiResp update(QlnvKhvonphiLapThamDinhDuToanReq objReq) throws Exception;

	public QlnvKhvonphiLapThamDinhDuToan function(NhomNutChucNangReq objReq) throws Exception;

	public Resp validate(QlnvKhvonphiLapThamDinhDuToanReq objReq);
}
