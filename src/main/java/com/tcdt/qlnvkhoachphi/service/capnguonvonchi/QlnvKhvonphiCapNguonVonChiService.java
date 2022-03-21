package com.tcdt.qlnvkhoachphi.service.capnguonvonchi;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.tcdt.qlnvkhoachphi.request.object.catalog.NhomNutChucNangReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvonReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.capnguonvonchi.QlnvKhvonphiThopCapvonReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan.TongHopDchinhDuToanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvonSearchReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.capnguonvonchi.QlnvKhvonphiThopCapvonSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.capnguonvonchi.QlnvKhvonphiDnghiCapvonResp;
import com.tcdt.qlnvkhoachphi.response.capnguonvonchi.QlnvKhvonphiThopCapvonResp;
import com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvon;
import com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi.QlnvKhvonphiThopCapvon;

public interface QlnvKhvonphiCapNguonVonChiService {
	public QlnvKhvonphiDnghiCapvonResp detailDncv(Long id) throws Exception;

	public QlnvKhvonphiThopCapvonResp detailThcv(Long id) throws Exception;

	public Page<QlnvKhvonphiDnghiCapvon> searchDncv(QlnvKhvonphiDnghiCapvonSearchReq objReq) throws Exception;

	public Page<QlnvKhvonphiThopCapvon> searchThcv(QlnvKhvonphiThopCapvonSearchReq objReq) throws Exception;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public QlnvKhvonphiDnghiCapvonReq createDncv(QlnvKhvonphiDnghiCapvonReq objReq) throws Exception;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public QlnvKhvonphiThopCapvonReq createThcv(QlnvKhvonphiThopCapvonReq objReq) throws Exception;

	public Resp syntheticDncv(TongHopDchinhDuToanChiNsnnReq objReq) throws Exception;

	public Resp syntheticThcv(TongHopDchinhDuToanChiNsnnReq objReq) throws Exception;

	public QlnvKhvonphiDnghiCapvon deleteDncv(String ids) throws Exception;

	public QlnvKhvonphiThopCapvon deleteThcv(String ids) throws Exception;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public QlnvKhvonphiDnghiCapvonReq updateDncv(QlnvKhvonphiDnghiCapvonReq objReq) throws Exception;

	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public QlnvKhvonphiThopCapvonReq updateThcv(QlnvKhvonphiThopCapvonReq objReq) throws Exception;

	public Resp validate(QlnvKhvonphiDchinhDuToanChiNsnnReq objReq) throws Exception;

	public Resp function(NhomNutChucNangReq objReq) throws Exception;
}
