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
	Long namHienHanh;
	String trangThai;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String lyDoTuChoi;

	Object lstCTietBCao;
	Object lstFile;
}
