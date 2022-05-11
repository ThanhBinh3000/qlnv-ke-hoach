package com.tcdt.qlnvkhoach.request.search.catalog;

import com.tcdt.qlnvkhoach.request.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhvonphiCapvonMuaBanTtoanThangDtqgSearchReq extends BaseRequest {
	String ngayTaoTu;
	String ngayTaoDen;
	String maLoaiGnhan;
	String maDviLap;
	String maDviCap;
	String ngayQuyetDinh;
	String soQd;
	String trangThai;
	
}
