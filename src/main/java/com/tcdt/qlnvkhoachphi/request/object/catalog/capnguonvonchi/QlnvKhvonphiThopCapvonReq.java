package com.tcdt.qlnvkhoachphi.request.object.catalog.capnguonvonchi;

import java.util.ArrayList;
import java.util.List;

import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi.QlnvKhvonphiThopCapvonCtiet;

import lombok.Data;
@Data
public class QlnvKhvonphiThopCapvonReq {
	Long id;
	String maDvi;
	String maDviThop;
	String trangThai;

	String listIdDeletes;

	String listIdFiles;

	ArrayList<FileDinhKemReq> fileDinhKems;
	List<QlnvKhvonphiThopCapvonCtiet> chiNsnnCtietReqs;
}
