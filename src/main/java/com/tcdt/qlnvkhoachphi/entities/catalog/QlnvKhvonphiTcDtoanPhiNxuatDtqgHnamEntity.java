package com.tcdt.qlnvkhoachphi.entities.catalog;

import lombok.Data;

@Data
public class QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamEntity {
	Long id;
	Long qlnvKhvonphiId;
	String maCucDtnnKvuc;
	Long nxuatThocLuongXuat;
	Long nxuatThocLuongNhap;
	Long nxuatThocLuongDmucPhiXuat;
	Long nxuatThocLuongDmucPhiNhap;
	Long nxuatThocLuongTtien;
	Long nxuatGaoLuongXuat;
	Long nxuatGaoLuongNhap;
	Long nxuatGaoLuongDmucPhiXuat;
	Long nxuatGaoLuongDmucPhiNhap;
	Long nxuatGaoLuongTtien;

	Object lstTcDtoanPhiNxuatDtqgVtuTbiHnam;
}
