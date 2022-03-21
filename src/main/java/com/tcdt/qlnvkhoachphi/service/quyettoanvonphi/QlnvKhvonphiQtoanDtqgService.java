package com.tcdt.qlnvkhoachphi.service.quyettoanvonphi;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.tcdt.qlnvkhoachphi.request.object.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqgReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqgSearchReq;
import com.tcdt.qlnvkhoachphi.response.quyettoanvonphi.QlnvKhvonphiQtoanDtqgResp;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqg;

public interface QlnvKhvonphiQtoanDtqgService {
	
	public QlnvKhvonphiQtoanDtqgResp create(QlnvKhvonphiQtoanDtqgReq objReq) throws Exception;

	public QlnvKhvonphiQtoanDtqgResp update(QlnvKhvonphiQtoanDtqgReq objReq) throws JsonMappingException, Exception;

	public void delete(String ids) throws Exception;

	public QlnvKhvonphiQtoanDtqgResp detail(String ids);

	public Page<QlnvKhvonphiQtoanDtqg> colection(QlnvKhvonphiQtoanDtqgSearchReq objReq);
}
