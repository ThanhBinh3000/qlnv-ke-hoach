package com.tcdt.qlnvkhoachphi.response;

import java.util.ArrayList;

import lombok.Data;
@Data
public class QlnvKhvonphiTcDtoanPhiNxuatDtqgHnamResp {
	Long id;
	Long qlnvKhvonphiId;
	Long stt;
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

	ArrayList<Object> lstTcDtoanPhiNxuatDtqgVtuTbiHnams;
}
