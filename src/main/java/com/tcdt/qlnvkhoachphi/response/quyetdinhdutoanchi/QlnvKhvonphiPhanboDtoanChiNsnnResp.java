package com.tcdt.qlnvkhoachphi.response.quyetdinhdutoanchi;

import java.util.List;

import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnnCtiet;

import lombok.Data;

@Data
public class QlnvKhvonphiPhanboDtoanChiNsnnResp {
	Long id;
	Long maDvi;
	String noiQd;
	
//	SO_QD_BTC
	String soQdBtc;
	
//	SO_QD_TCDT
	String soQdTcdt;
	
	String phanBoTong;
	
	String trangThai;
	
	String ghiChu;
	String vanBan;
	
	List<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> lstCtiet;
}
