package com.tcdt.qlnvkhoachphi.request.search.catalog;

import com.tcdt.qlnvkhoachphi.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhvonphiDchinhDuToanChiNsnnSearchReq extends BaseRequest{

	String ngayTaoTu;
	String ngayTaoDen;
	String maDvi;
	Long namHienHanh;
	String trangThai;

}
