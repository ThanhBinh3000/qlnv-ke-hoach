package com.tcdt.qlnvkhoachphi.service.dieuchinhdutoan;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.TongHopDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.QlnvKhvonphiDchinhDuToanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnResp;
import com.tcdt.qlnvkhoachphi.table.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnn;

public interface IQlnvKhvonphiDchinhDuToanChiNsnnService {
	public QlnvKhvonphiDchinhDuToanChiNsnnResp getDetail(Long id) throws Exception;

	public Page<QlnvKhvonphiDchinhDuToanChiNsnn> getList(QlnvKhvonphiDchinhDuToanChiNsnnSearchReq objReq)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public QlnvKhvonphiDchinhDuToanChiNsnnReq add(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq) throws Exception;

	public Object synthetic(TongHopDchinhDuToanChiNsnnReq objReq) throws Exception;

	public QlnvKhvonphiDchinhDuToanChiNsnn delete(String ids) throws Exception;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public QlnvKhvonphiDchinhDuToanChiNsnnReq update(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq) throws Exception;

	public Resp validate(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq) throws Exception;

	public QlnvKhvonphiDchinhDuToanChiNsnn function(NhomNutChucNangReq objReq) throws Exception;
}
