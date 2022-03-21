package com.tcdt.qlnvkhoachphi.response.capvonmuaban;

import java.util.Date;
import java.util.List;

import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet;

import lombok.Data;

@Data
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp {
	Long id;
	String maLoaiGnhan;
	String maDviLap;
	String tenDviLap;
	String maDviCap;
	String tenDviCap;
	String maDviNhan;
	String tenDviNhan;
	String ngayQuyetDinh;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String soQd;
	String trangThai;
	String tenTrangThai;
	
	Object lstFile;
	List<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> lstCtiet;
}
