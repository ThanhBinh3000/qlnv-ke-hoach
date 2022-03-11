package com.tcdt.qlnvkhoachphi.response;

import java.util.ArrayList;

import lombok.Data;

@Data
public class QlnvKhvonphiNcauXuatDtqgVtroHnamResp {
	Long id;
	Long qlnvKhvonphiId;
	Long luongXuatGaoVtro;
	Long luongXuatThocVtro;

	ArrayList<Object> lstCTiet;
}
