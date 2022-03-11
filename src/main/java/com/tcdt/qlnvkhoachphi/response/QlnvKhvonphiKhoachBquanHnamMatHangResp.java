package com.tcdt.qlnvkhoachphi.response;

import java.util.ArrayList;

import lombok.Data;

@Data
public class QlnvKhvonphiKhoachBquanHnamMatHangResp {

	Long id;
	Long qlnvKhvonphiId;
	Long stt;
	Long KphiBquanThocTx;
	Long KphiBquanThocLd;
	Long KphiBquanGaoTx;
	Long KphiBquanGaoLd;

	ArrayList<Object> lstCTiet;
}
