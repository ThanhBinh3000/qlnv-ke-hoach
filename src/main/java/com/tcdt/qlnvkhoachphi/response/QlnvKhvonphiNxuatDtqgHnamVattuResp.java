package com.tcdt.qlnvkhoachphi.response;

import java.util.ArrayList;

import lombok.Data;

@Data
public class QlnvKhvonphiNxuatDtqgHnamVattuResp {

	Long id;
	Long qlnvKhvonphiId;

	Long luongThocXuat;
	Long luongThocNhap;
	Long luongGaoXuat;
	Long luongGaoNhap;

	ArrayList<Object>  lstCTiet;
}
