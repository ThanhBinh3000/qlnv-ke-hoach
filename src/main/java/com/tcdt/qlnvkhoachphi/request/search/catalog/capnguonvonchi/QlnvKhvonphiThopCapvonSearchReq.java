package com.tcdt.qlnvkhoachphi.request.search.catalog.capnguonvonchi;

import com.tcdt.qlnvkhoachphi.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhvonphiThopCapvonSearchReq extends BaseRequest {
	String ngayTaoTu;
	String ngayTaoDen;
	String maDvi;
	String trangThai ;
}
