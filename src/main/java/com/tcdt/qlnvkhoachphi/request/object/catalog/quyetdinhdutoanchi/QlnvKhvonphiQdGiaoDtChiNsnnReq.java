package com.tcdt.qlnvkhoachphi.request.object.catalog.quyetdinhdutoanchi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnnCtiet;

import lombok.Data;

@Data
public class QlnvKhvonphiQdGiaoDtChiNsnnReq {
	Long id;
	Long maDvi;
	String noiQd;
	String soQd;
	Date ngayQd;
	String trangThai;
	String vanBan;

	String listIdDeletes;

	String listIdFiles;
	ArrayList<FileDinhKemReq> fileDinhKems;
	List<QlnvKhvonphiQdGiaoDtChiNsnnCtiet> lstCtiet;
}
