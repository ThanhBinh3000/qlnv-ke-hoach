package com.tcdt.qlnvkhoachphi.request.object.catalog.quyettoanvonphi;

import java.util.ArrayList;
import java.util.Date;

import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiDchinhSauQtoanDtqgCtiet;

import lombok.Data;

@Data
public class QlnvKhvonphiDchinhSauQtoanDtqgReq {
	Long id;
	String maDvi;
	
	String soQd;
	Date ngayQuyetDinh;
	String trangThai;
	
	String listIdDeletes;

	String listIdFiles;
	ArrayList<FileDinhKemReq> fileDinhKems;
	
	ArrayList<QlnvKhvonphiDchinhSauQtoanDtqgCtiet> lstCtiet;
}
