package com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan;

import java.util.ArrayList;

import lombok.Data;

@Data
public class QlnvKhvonphiPaGiaoSoKiemTraTcNsnnResp {
	Long id;
	String maPa;
	String maDvi;
	Long namPa;
	Long namHienHanh;
	String trangThai;
	String maDviTien;
	
	String ListIdDeletes;
	
	ArrayList<QlnvKhvonphiPaGiaoSoKiemTraTcNsnnCtietResp> listCtiet;
}
