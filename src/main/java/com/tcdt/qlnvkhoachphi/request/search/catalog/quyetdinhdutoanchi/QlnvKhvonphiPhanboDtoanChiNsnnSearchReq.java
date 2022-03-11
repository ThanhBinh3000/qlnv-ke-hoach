package com.tcdt.qlnvkhoachphi.request.search.catalog.quyetdinhdutoanchi;

import com.tcdt.qlnvkhoachphi.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhvonphiPhanboDtoanChiNsnnSearchReq extends BaseRequest {
	String soQd;
	String ngayTaoTu;
	String ngayTaoDen;
	String maDvi;
	String trangThai;
}
