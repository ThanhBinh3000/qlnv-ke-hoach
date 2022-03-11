package com.tcdt.qlnvkhoachphi.response.baocaodutoanchi;

import java.util.Date;

import lombok.Data;
@Data
public class QlnvKhvonphiBcaoResp {
	Long id;
	String maBcao;
	Long namBcao;
	Long thangBcao;
	Long namHienHanh;
	String trangThai;
	String maLoaiBcao;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String maDviTien;
	Long maDvi;
	Long dotBcao;
	Long loaiBaoCao;
	String lyDoTuChoi;

	Object lstCTietBCao;
	Object lstFile;
}
