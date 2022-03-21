package com.tcdt.qlnvkhoachphi.request.search.catalog.quyettoanvonphi;

import com.tcdt.qlnvkhoachphi.request.BaseRequest;

import lombok.Data;

@Data
public class QlnvKhvonphiQtoanDtqgSearchReq extends BaseRequest{
	String soQd;
	String ngayTaoTu;
	String ngayTaoDen;
	String maDvi;
	String trangThai;
}
