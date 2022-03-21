package com.tcdt.qlnvkhoachphi.service.baocao;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.baocaodutoanchi.QlnvKhvonphiBcaoReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.baocaodutoanchi.TongHopBcaoReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.baocaodutoanchi.QlnvKhvonphiBcaoSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.baocao.QlnvKhvonphiBcaoResp;
import com.tcdt.qlnvkhoachphi.table.catalog.baocaodutoanchi.QlnvKhvonphiBcao;

public interface IQlnvKhvonphiBCaoService {
	public QlnvKhvonphiBcaoResp getDetail(Long id) throws Exception;

	public Page<QlnvKhvonphiBcao> getList(QlnvKhvonphiBcaoSearchReq objReq) throws Exception;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public QlnvKhvonphiBcaoReq add(QlnvKhvonphiBcaoReq objReq) throws Exception;

	public Object synthetic(TongHopBcaoReq objReq) throws Exception;

	public QlnvKhvonphiBcao delete(String ids) throws Exception;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public QlnvKhvonphiBcaoReq update(QlnvKhvonphiBcaoReq objReq) throws Exception;

	public Resp validate(QlnvKhvonphiBcaoReq objReq) throws Exception;

	public Resp genMaBcao() throws Exception;

	public QlnvKhvonphiBcao function(NhomNutChucNangReq objReq) throws Exception;
}
