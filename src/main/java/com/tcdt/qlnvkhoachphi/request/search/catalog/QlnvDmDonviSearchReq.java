package com.tcdt.qlnvkhoachphi.request.search.catalog;

import com.tcdt.qlnvkhoachphi.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDmDonviSearchReq extends BaseRequest {
	String maDvi;
	String tenDvi;
	String maTinh;
	String maQuan;
	String maPhuong;
	String capDvi;
	String kieuDvi;
	String loaiDvi;
}
