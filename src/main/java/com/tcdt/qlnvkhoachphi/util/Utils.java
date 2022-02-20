package com.tcdt.qlnvkhoachphi.util;

public class Utils {
	public static String getTenTrangThai(String maTrangthai) {
		switch (maTrangthai) {
		case Constants.TT_BC_1:
			return "Đang soạn";
		case Constants.TT_BC_2:
			return "Trình duyệt";
		case Constants.TT_BC_3:
			return "Trưởng BP từ chối";
		case Constants.TT_BC_4:
			return "Trưởng BP duyệt";
		case Constants.TT_BC_5:
			return "Lãnh đạo từ chối";
		case Constants.TT_BC_6:
			return "Lãnh đạo duyệt";
		case Constants.TT_BC_7:
			return "Gửi ĐV cấp trên";
		case Constants.TT_BC_8:
			return "ĐV cấp trên từ chối";
		case Constants.TT_BC_9:
			return "Đv cấp trên duyệt";
		default:
			return null;
		}
	}
}
