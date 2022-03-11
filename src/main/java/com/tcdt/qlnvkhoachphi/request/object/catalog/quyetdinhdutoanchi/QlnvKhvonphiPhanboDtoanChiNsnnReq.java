package com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi;

import java.util.ArrayList;
import java.util.List;

import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnnCtiet;

import lombok.Data;

@Data
public class QlnvKhvonphiPhanboDtoanChiNsnnReq {
	Long id;
	Long maDvi;
	String noiQd;

//	SO_QD_BTC
	String soQdBtc;
	
//	SO_QD_TCDT
	String soQdTcdt;

	String phanBoTong;

	String trangThai;
	String vanBan;
	String lyDoTuChoi;
	String ghiChu;

	String listIdDeletes;

	String listIdFiles;
	ArrayList<FileDinhKemReq> fileDinhKems;
	List<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> lstCtiet;
}
