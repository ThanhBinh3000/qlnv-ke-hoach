package com.tcdt.qlnvkhoach.enums;

public enum PhuongAnGiaEnum {
	KET_QUA_KHAO_SAT_GIA_THI_TRUONG("00"), //Kết quả khảo sát thị trường
	KET_QUA_THAM_DINH_GIA("01"),//Kết quả thẩm định giá
	THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU("03");//Thông tin giá của hàng hóa tương tự
	private final String value;

	PhuongAnGiaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
