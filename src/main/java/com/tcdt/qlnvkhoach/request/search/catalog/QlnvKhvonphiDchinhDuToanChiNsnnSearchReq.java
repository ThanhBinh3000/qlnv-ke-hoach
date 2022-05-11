package com.tcdt.qlnvkhoach.request.search.catalog;

import com.tcdt.qlnvkhoach.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhvonphiDchinhDuToanChiNsnnSearchReq extends BaseRequest{
	String ngayTaoTu;
	String ngayTaoDen;
	Long maDvi;
	Long namHienHanh;
	String trangThai;
	String soQd;
}
