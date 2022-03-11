package com.tcdt.qlnvkhoachphi.response.quyetdinhdutoanchi;

import java.util.Date;
import java.util.List;

import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnCtiet;

import lombok.Data;

@Data
public class QlnvKhvonphiQdGiaoDtChiNsnnResp {
	Long id;
	Long maDvi;
	String noiQd;
	String soQd;
	Date ngayQd;
	String trangThai;
	String vanBan;
	
	List<QlnvKhvonphiQdGiaoDtChiNsnnCtiet> lstCtiet;
}
