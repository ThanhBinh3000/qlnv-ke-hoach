package com.tcdt.qlnvkhoachphi.request.object.catalog;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class QlnvKhvonphiDchinhDuToanChiNsnnReq {

	Long id;
	String soQd;
	String maDvi;
	Long namHienHanh;
	Long duToanDc;
	Long kinhPhiTc;
	Long kinhPhiKhongTc;
//	String ngayQdReq;

	List<QlnvKhvonphiDchinhDuToanChiNsnnCtietReq> chiNsnnCtietReqs;

}
