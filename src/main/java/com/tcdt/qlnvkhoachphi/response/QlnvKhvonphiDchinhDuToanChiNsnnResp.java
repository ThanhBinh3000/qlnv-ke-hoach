package com.tcdt.qlnvkhoachphi.response;

import java.util.Date;
import java.util.Set;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiDchinhDuToanChiNsnnCtiet;

import lombok.Data;
@Data
public class QlnvKhvonphiDchinhDuToanChiNsnnResp {

	Long id;
	String soQd;
	String maDvi;
	Long namHienHanh;
	Long duToanDc;
	Long kinhPhiTc;
	Long kinhPhiKhongTc;
	Date ngayQd;
	String trangThai;
	String vanBan;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;

	Set<QlnvKhvonphiDchinhDuToanChiNsnnCtiet> chiNsnnCtiets;
	Object lstFile;

}
