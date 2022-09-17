package com.tcdt.qlnvkhoach.request;

import lombok.Data;

@Data
public class QlnvDmDonviSearchReq {
	String maDvi;
	String tenDvi;
	String maTinh;
	String maQuan;
	String maPhuong;
	String capDvi;
	String kieuDvi;
	String loaiDvi;
	String trangThai;
	String maDviCha;
	PaggingReq paggingReq;
}
