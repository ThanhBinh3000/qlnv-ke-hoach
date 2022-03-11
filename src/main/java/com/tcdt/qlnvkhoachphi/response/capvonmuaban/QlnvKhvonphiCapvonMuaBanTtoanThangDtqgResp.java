package com.tcdt.qlnvkhoachphi.response.capvonmuaban;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet;

import lombok.Data;

@Data
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgResp {
	Long id;
	String maLoaiGnhan;
	String maDviLap;
	String tenDviLap;
	String tenDviCap;
	String maDviCap;
	String ngayQuyetDinh;
	Date ngayTao;
	String nguoiTao;
	String soQd;
	String trangThai;
	String tenTrangThai;
	
	Object lstFile;
	List<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> lstCtiet;
}
