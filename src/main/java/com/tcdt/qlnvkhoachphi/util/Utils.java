package com.tcdt.qlnvkhoachphi.util;

import com.tcdt.qlnvkhoachphi.entities.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoachphi.table.Role;
import com.tcdt.qlnvkhoachphi.table.UserInfo;

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

	public static String function(String trangthai, QlnvDmDonvi qlnvDmDonvi, UserInfo userInfo, String maChucNang) {

		// nếu là tổng cục set type
		boolean isTongCuc = false;
		if (qlnvDmDonvi.getCapDvi().equals("1")) {
			isTongCuc = true;
		}

		if (trangthai.equals(Constants.TT_BC_6) && isTongCuc) {
			// trạng thái cuối của Tổng cục
			throw new UnsupportedOperationException("Báo cáo đã ở trạng thái: Lãnh đạo duyệt!");
		} else if (trangthai.equals(Constants.TT_BC_9) && !isTongCuc) {
			// trạng thái cuối của chi cục và cục khu vực
			throw new UnsupportedOperationException("Báo cáo đã ở trạng thái: Đơn vị cấp trên duyệt!");
		}

		// check user thuộc đơn vị hoặc đơn vị cha
		// user cùng đơn vị
		if (qlnvDmDonvi.getId().equals(userInfo.getDvql())) {
			// check người dùng có quyền thao tác với action tương ứng hay ko
			for (Role role : userInfo.getRoles()) {
				if (role.getId().equals(Constants.NHAN_VIEN)) {
					if (!maChucNang.equals(Constants.TT_BC_1)
							&& !maChucNang.equals(Constants.TT_BC_2)) {
						throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
					}
				} else if (role.getId().equals(Constants.TRUONG_BO_PHAN)) {
					if (!maChucNang.equals(Constants.TT_BC_3)
							&& !maChucNang.equals(Constants.TT_BC_4)) {
						throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
					}
				} else if (role.getId().equals(Constants.LANH_DAO)) {
					if (!maChucNang.equals(Constants.TT_BC_5)
							&& !maChucNang.equals(Constants.TT_BC_6)) {
						throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
					}
				}
			}
		} else if (qlnvDmDonvi.getParent().getId().equals(userInfo.getDvql()) && !isTongCuc) {
			// user đơn vị cấp trên
			for (Role role : userInfo.getRoles()) {
				if (!role.getId().equals(Constants.NHAN_VIEN)) {
					throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
				} else {
					if (!maChucNang.equals(Constants.TT_BC_8)
							&& !maChucNang.equals(Constants.TT_BC_9)) {
						throw new UnsupportedOperationException("Người dùng không có quyền thao tác!");
					}
				}
			}
		} else {
			throw new UnsupportedOperationException("Người dùng không hợp lệ!");
		}

		// check trạng thái hiện tại của báo cáo, xem action có đúng là trạng thái tiếp
		// theo hoặc tiếp sau
		int stt = Integer.parseInt(trangthai);
		int sttNext = stt + 1;
		int sttPrevious = stt - 1;
		int maChucNangInt = Integer.parseInt(maChucNang);

		if (sttPrevious == 0) {
			if (sttNext != maChucNangInt) {
				throw new UnsupportedOperationException("Mã chức năng: " + maChucNang
						+ " không khớp với trạng thái: " + trangthai + " của Báo cáo!");
			}
		} else if ((sttNext != maChucNangInt && sttPrevious != maChucNangInt)) {
			throw new UnsupportedOperationException("Mã chức năng: " + maChucNang
					+ " không khớp với trạng thái: " + trangthai + " của Báo cáo!");
		}

		switch (maChucNang) {
		case Constants.TT_BC_2:// Trình duyệt
			return(Constants.TT_BC_2);
		case Constants.TT_BC_3:// Trưởng BP từ chối
			return(Constants.TT_BC_3);
		case Constants.TT_BC_4:// Trưởng BP duyệt
			return(Constants.TT_BC_4);
		case Constants.TT_BC_5:// Lãnh đạo từ chối
			return(Constants.TT_BC_5);
		case Constants.TT_BC_6:// Lãnh đạo duyệt
			return(Constants.TT_BC_6);
		case Constants.TT_BC_7:// Gửi ĐV cấp trên
			return(Constants.TT_BC_7);
		case Constants.TT_BC_8:// ĐV cấp trên từ chối
			return(Constants.TT_BC_8);
		case Constants.TT_BC_9:// Đv cấp trên duyệt
			return(Constants.TT_BC_9);
		default:
			return "";
		}
	}
}
