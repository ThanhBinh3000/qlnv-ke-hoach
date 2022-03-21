package com.tcdt.qlnvkhoachphi.response.capnguonvonchi;

import java.util.Date;
import java.util.List;

import com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvonCtiet;

import lombok.Data;
@Data
public class QlnvKhvonphiDnghiCapvonResp {
	Long id;
	String maDvi;
	String maCanCu;
	String maHdong;
	String soCv;
	Date ngay;
	String trangThai;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String lyDoTuChoi;

	List<QlnvKhvonphiDnghiCapvonCtiet> listCtiet;
	Object lstFile;
}
