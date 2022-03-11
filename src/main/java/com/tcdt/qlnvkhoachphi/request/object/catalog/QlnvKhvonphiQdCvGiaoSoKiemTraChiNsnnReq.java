package com.tcdt.qlnvkhoachphi.request.object.catalog;

import java.util.ArrayList;

import lombok.Data;

@Data
public class QlnvKhvonphiQdCvGiaoSoKiemTraChiNsnnReq {
	String maPa;
	String maDvi;
	String maDviNhan;
	String soQd;
	String soCv;
	String namGiao;
	String ngayQd;
	String ngayCv;
	String nguoiTao;
	
	
	String listIdFiles;
	
	ArrayList<FileDinhKemReq> fileDinhKems;
}
