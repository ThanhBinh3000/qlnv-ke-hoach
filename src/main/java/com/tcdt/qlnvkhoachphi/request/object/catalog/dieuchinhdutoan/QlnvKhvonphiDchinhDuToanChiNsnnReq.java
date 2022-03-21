package com.tcdt.qlnvkhoachphi.request.object.catalog.dieuchinhdutoan;


import java.util.ArrayList;
import java.util.List;

import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;

import lombok.Data;

@Data
public class QlnvKhvonphiDchinhDuToanChiNsnnReq {
	Long id;
	String soQd;
	Long maDvi;
	Long namHienHanh;
	String ngayqd;
	String phanBo;
	String trangThai;

	String listIdDeletes;

	String listIdFiles;

	ArrayList<FileDinhKemReq> fileDinhKems;
	List<QlnvKhvonphiDchinhDuToanChiNsnnCtietReq> chiNsnnCtietReqs;

}
