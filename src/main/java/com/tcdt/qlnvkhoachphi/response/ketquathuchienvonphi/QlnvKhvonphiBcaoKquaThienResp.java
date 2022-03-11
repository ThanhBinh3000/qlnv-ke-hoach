package com.tcdt.qlnvkhoachphi.response.ketquathuchienvonphi;

import java.util.Date;

import lombok.Data;
@Data
public class QlnvKhvonphiBcaoKquaThienResp {
	Long id;
	String maDvi;
	String maBcao;
	String maLoaiBcao;
	Long namBcao;
	Long dotBcao;
	String trangThai;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String lyDoTuChoi;

	Object lstCTietBCao;
	Object lstFile;
}
