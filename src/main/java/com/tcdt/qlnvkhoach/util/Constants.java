package com.tcdt.qlnvkhoach.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class Constants {
	public static final String FORMAT_DATE_STR = "dd/MM/yyyy";
	public static final String FORMAT_DATE_TIME_STR = "dd/MM/yyyy HH:mm:ss";

	// Trang thai response
	public static final int RESP_SUCC = 0;
	public static final int RESP_FAIL = 1;

	public static final String TYPE_USER_BACKEND = "BE";
	public static final String TYPE_USER_FRONTEND = "FE";

	// Trang thai
	public static final String MOI_TAO = "00";
	public static final String HOAT_DONG = "01";
	public static final String NGUNG_HOAT_DONG = "02";
	public static final String TAM_KHOA = "03";

	// Trang thái báo cáo
	public static final String TT_BC_0 = "0"; // Đã xóa,
	public static final String TT_BC_1 = "1"; // Đang soạn,
	public static final String TT_BC_2 = "2"; // Trình duyệt,
	public static final String TT_BC_3 = "3"; // Trưởng BP từ chối,
	public static final String TT_BC_4 = "4"; // Trưởng BP duyệt,
	public static final String TT_BC_5 = "5"; // Lãnh đạo từ chối,
	public static final String TT_BC_6 = "6"; // Lãnh đạo duyệt,
	public static final String TT_BC_7 = "7"; // Gửi ĐV cấp trên,
	public static final String TT_BC_8 = "8"; // ĐV cấp trên từ chối,
	public static final String TT_BC_9 = "9"; // Đv cấp trên duyệt"
	public static final String TT_BC_10 = "10"; // Lãnh đạo yêu cầu điều chỉnh"

	// Danh sach quyen
	public static final Long LANH_DAO = 1l;// "Lãnh Đạo";
	public static final Long TRUONG_BO_PHAN = 2l;// "Trưởng Bộ Phận";
	public static final Long NHAN_VIEN = 3l;// "Nhân Viên";

	// Cap don vi
	public static final String CHI_CUC = "3";
	public static final String CUC_KHU_VUC = "2";
	public static final String TONG_CUC = "1";
	public static final String BO_NGANH = "0";

	// Loai vat tu hang hoa
	public static final String LOAI_VTHH_LUONG_THUC = "00";
	public static final String LOAI_VTHH_GAO = "0102";
	public static final String LOAI_VTHH_THOC = "0101";
	public static final String LOAI_VTHH_MUOI = "04";
	public static final String LOAI_VTHH_VATTU = "02";

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class ActLapThamDinhDuToan {
		public static String START = "startLapThamDinh";
		public static String ID = "id";
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class ChiTieuKeHoachNamExport {
		public static final String SHEET_KE_HOACH_LUONG_THUC_DTNN = "Kế hoạch lương thực DTNN";
		public static final String SHEET_KE_HOACH_MUOI_DTNN = "Kế hoạch muối DTNN";
		public static final String SHEET_KE_HOACH_NHAP_VT_TB = "Kế hoạch nhập VT_TB";
		public static final int SO_NAM_LUU_KHO_MUOI = 3;
		public static final int SO_NAM_LUU_KHO_THOC = 3;
		public static final int SO_NAM_LUU_KHO_GAO = 2;
		public static final int SO_NAM_LUU_KHO_VAT_TU = 3;
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class ExcelHeader {
		public static final String STT = "STT";
		public static final String CUC_DTNN_KHU_VUC = "Cục DTNN khu vuc";
		public static final String TON_KHO_DAU_NAM = "Tồn kho đầu năm";
		public static final String TONG_SO_QUY_THOC = "Tổng số\n(quy thóc)";
		public static final String TONG = "Tổng";
		public static final String NAM_NHAP = "Nhập\n%s";
		public static final String THOC = "Thóc";
		public static final String GAO = "Gạo";
		public static final String TRONG_DO = "Trong đó";
		public static final String NHAP_TRONG_NAM = "Nhập trong năm";
		public static final String XUAT_TRONG_NAM = "Xuất trong năm";
		public static final String TON_KHO_CUOI_NAM = "Tồn kho cuối năm";
		public static final String TONG_SO = "Tổng số";
		public static final String MA_HANG = "Mã hàng";
		public static final String MAT_HANG = "Mặt hàng";
		public static final String DON_VI_TINH = "Đơn vị tính";
		public static final String CHI_TIEU_NHAP_CAC_NAM_KHAC_CHUYEN_SANG = "Chỉ tiêu nhập các năm khác chuyển sang";
		public static final String KE_HOACH_NAM = "Kế hoạch\n%s";
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class ExportDataType {
		public static final String CHI_TIEU_LUONG_THUC = "LUONG_THUC";
		public static final String CHI_TIEU_MUOI = "MUOI";
		public static final String CHI_TIEU_VAT_TU = "VAT_TU";
	}
	


	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class LuongThucMuoiConst {
		public static final Long THOC_ID = 2L;
		public static final String THOC_MA_VT = "0101";
		public static final Long GAO_ID = 6L;
		public static final String GAO_MA_VT = "0102";
		public static final Long MUOI_ID = 78L;
		public static final String MUOI_MA_VT = "04";
		public static final List<Long> THOC_GAO_MUOI_IDS = Arrays.asList(THOC_ID, GAO_ID, MUOI_ID);
	}
}
