package com.tcdt.qlnvkhoachphi.response.lapthamdinhdutoan;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnResp {
	Long id;
	String maGiao;
	String maPa;
	
	String maDviTao;
	String tenDviTao;
	String maDviNhan;
	String tenDviNhan;
	Long namGiao;
	String trangThai;
	String tenTrangThai;
	Date ngayTao;
	String nguoiTao;
	String maDviTien;
	
	ArrayList<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnCtietResp> lstCTiet;
}
