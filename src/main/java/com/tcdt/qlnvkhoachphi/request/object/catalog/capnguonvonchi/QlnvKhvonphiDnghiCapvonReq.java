package com.tcdt.qlnvkhoachphi.request.object.catalog.capnguonvonchi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi.QlnvKhvonphiDnghiCapvonCtiet;

import lombok.Data;
@Data
public class QlnvKhvonphiDnghiCapvonReq {
	Long id;
	String maDvi;
	String maCanCu;
	String maHdong;
	String soCv;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	Date ngay;
	String trangThai;

	String listIdDeletes;

	String listIdFiles;

	ArrayList<FileDinhKemReq> fileDinhKems;
	List<QlnvKhvonphiDnghiCapvonCtiet> chiNsnnCtietReqs;
}
