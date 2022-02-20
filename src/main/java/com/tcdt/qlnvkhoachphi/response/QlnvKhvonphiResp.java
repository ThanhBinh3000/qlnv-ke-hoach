package com.tcdt.qlnvkhoachphi.response;

import java.util.Date;

import lombok.Data;

@Data
public class QlnvKhvonphiResp {
	Long id;
	String maBcao;
	Long maDvi;
	String maLoaiBcao;
	Long namBcao;
	String trangThai;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;

	Object lstCTietBCao;
	Object lstFile;
}
