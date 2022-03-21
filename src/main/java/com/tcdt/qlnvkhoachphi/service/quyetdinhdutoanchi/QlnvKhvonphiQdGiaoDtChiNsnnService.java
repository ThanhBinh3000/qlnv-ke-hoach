package com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.Resp;
import com.tcdt.qlnvkhoachphi.response.quyetdinhdutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnResp;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnn;

public interface QlnvKhvonphiQdGiaoDtChiNsnnService {
	
	public Resp detail(String ids);

	public Page<QlnvKhvonphiQdGiaoDtChiNsnn> colection(QlnvKhvonphiQdGiaoDtChiNsnnSearchReq objReq);
	
	public QlnvKhvonphiQdGiaoDtChiNsnnResp create(QlnvKhvonphiQdGiaoDtChiNsnnReq objReq) throws Exception;

	public void delete(String ids) throws Exception;

	public QlnvKhvonphiQdGiaoDtChiNsnnResp update(QlnvKhvonphiQdGiaoDtChiNsnnReq objReq) throws JsonMappingException, Exception;
}
