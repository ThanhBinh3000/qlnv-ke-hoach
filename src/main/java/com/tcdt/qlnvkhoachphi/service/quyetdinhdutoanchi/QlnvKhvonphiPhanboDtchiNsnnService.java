package com.tcdt.qlnvkhoachphi.service.quyetdinhdutoanchi;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnReq;
import com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnSearchReq;
import com.tcdt.qlnvkhoachphi.response.quyetdinhdutoanchi.QlnvKhvonphiPhanboDtoanChiNsnnResp;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnn;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnnCtiet;

public interface QlnvKhvonphiPhanboDtchiNsnnService {
	
	public QlnvKhvonphiPhanboDtoanChiNsnnResp create(QlnvKhvonphiPhanboDtoanChiNsnnReq objReq) throws Exception;

	public QlnvKhvonphiPhanboDtoanChiNsnnResp update(QlnvKhvonphiPhanboDtoanChiNsnnReq objReq) throws Exception;

	public void delete(String ids) throws Exception;

	public QlnvKhvonphiPhanboDtoanChiNsnnResp detail(String ids);

	public Page<QlnvKhvonphiPhanboDtoanchiNsnn> colection(QlnvKhvonphiPhanboDtoanChiNsnnSearchReq objReq);

	public ArrayList<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> synthesis(String maDviThien);
}
