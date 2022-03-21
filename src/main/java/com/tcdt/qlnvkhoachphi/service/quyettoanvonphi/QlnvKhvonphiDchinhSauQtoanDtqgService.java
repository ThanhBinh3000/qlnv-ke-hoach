package com.tcdt.qlnvkhoachphi.service.quyettoanvonphi;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgResp;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqg;

public interface QlnvKhvonphiDchinhSauQtoanDtqgService {
	
	public QlnvKhvonphiDchinhSauQtoanDtqgResp create(QlnvKhvonphiDchinhSauQtoanDtqgReq objReq) throws Exception;

	public QlnvKhvonphiDchinhSauQtoanDtqgResp update(QlnvKhvonphiDchinhSauQtoanDtqgReq objReq) throws JsonMappingException, Exception;

	public void delete(String ids) throws Exception;

	public QlnvKhvonphiDchinhSauQtoanDtqgResp detail(Long ids);

	public Page<QlnvKhvonphiDchinhSauQtoanDtqg> colection(QlnvKhvonphiDchinhSauQtoanDtqgSearchReq objReq);
}
