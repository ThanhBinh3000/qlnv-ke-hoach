package com.tcdt.qlnvkhoachphi.request;

import lombok.Data;

@Data
public class BaseRequest {
	PaggingReq paggingReq;
	String trangThai;
	String str;
}