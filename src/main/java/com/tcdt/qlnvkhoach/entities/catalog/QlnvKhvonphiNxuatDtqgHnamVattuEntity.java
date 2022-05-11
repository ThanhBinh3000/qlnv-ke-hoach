package com.tcdt.qlnvkhoach.entities.catalog;

import lombok.Data;

@Data
public class QlnvKhvonphiNxuatDtqgHnamVattuEntity {

	Long id;
	Long qlnvKhvonphiId;

	Long luongThocXuat;
	Long luongThocNhap;
	Long luongGaoXuat;
	Long luongGaoNhap;

	Object  lstCTiet;
}
