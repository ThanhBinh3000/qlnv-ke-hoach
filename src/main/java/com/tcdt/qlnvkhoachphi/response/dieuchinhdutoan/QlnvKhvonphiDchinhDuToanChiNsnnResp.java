package com.tcdt.qlnvkhoachphi.response.dieuchinhdutoan;

import java.util.Date;
import java.util.List;

import com.tcdt.qlnvkhoachphi.table.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnnCtiet;

import lombok.Data;
@Data
public class QlnvKhvonphiDchinhDuToanChiNsnnResp {
	Long id;
	String soQd;
	Long maDvi;
	Long namHienHanh;
	Date ngayQuyetDinh;
	String phanBo;
	String trangThai;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String lyDoTuChoi;

	List<QlnvKhvonphiDchinhDuToanChiNsnnCtiet> listCtiet;
	Object lstFile;
}
