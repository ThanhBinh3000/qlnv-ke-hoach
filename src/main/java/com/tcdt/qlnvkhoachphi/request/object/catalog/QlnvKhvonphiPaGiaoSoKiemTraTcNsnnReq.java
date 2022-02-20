package com.tcdt.qlnvkhoachphi.request.object.catalog;

import java.util.ArrayList;

import lombok.Data;

@Data
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnReq {
	
	Long id;
	String maPa;
	String maDvi;
	Long namPa;
	Long namHienHanh;
	String trangThai;
	String maDviTien;
	
	String ListIdDeletes;
	
	ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietReq> lstPaGiaoSoKiemTraTcNsnnCtiet;
}
