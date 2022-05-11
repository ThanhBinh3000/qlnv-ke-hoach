package com.tcdt.qlnvkhoach.request.search.catalog.quyettoanvonphi;

import com.tcdt.qlnvkhoach.request.BaseRequest;

import lombok.Data;

@Data
public class QlnvKhvonphiDchinhSauQtoanDtqgSearchReq extends BaseRequest{
	String soQd;
	String ngayTaoTu;
	String ngayTaoDen;
	String maDvi;
	String trangThai;
}
