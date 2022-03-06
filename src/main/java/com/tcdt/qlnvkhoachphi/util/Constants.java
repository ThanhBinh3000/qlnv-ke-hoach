package com.tcdt.qlnvkhoachphi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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

	// Loai bao cao
	// 3.2.4.3.1
	public static final String QLNV_KHVONPHI_DM_VONDT_XDCBGD3N = "01";

	// 3.2.4.3.2
	public static final String QLNV_KHVONPHI_NXUAT_DTQG_HNAM_VATTU = "02";

	// 3.2.4.3.3
	public static final String QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG = "03";

	// 3.2.4.3.4
	public static final String QLNV_KHVONPHI_NCAU_XUAT_DTQG_VTRO_HNAM = "04";

	// 3.2.4.3.5
	public static final String QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_GD3N = "05";

	// 3.2.4.3.6
	public static final String QLNV_KHVONPHI_KHOACH_QUY_TIEN_LUONG_HNAM = "06";

	// 3.2.4.3.7
	public static final String QLNV_KHVONPHI_CHI_DTAI_DAN_NCKH_GD3N = "07";

	// 3.2.4.3.8
	public static final String QLNV_KHVONPHI_VBAN_QPHAM_PLUAT_DTQG_GD3N = "08";

	// 3.2.4.3.9
	public static final String QLNV_KHVONPHI_CHI_UDUNG_CNTT_GD3N = "09";

	// 3.2.4.3.10
	public static final String QLNV_KHVONPHI_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N = "10";

	// 3.2.4.3.11
	public static final String QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N = "11";

	// 3.2.4.3.12
	public static final String QLNV_KHVONPHI_CHI_TX_GD3N = "12";

	// 3.2.4.3.13
	public static final String QLNV_KHVONPHI_NCAU_PHI_NHAP_XUAT_GD3N = "13";

	// 3.2.4.3.14
	public static final String QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N = "14";

	// 3.2.4.3.15
	public static final String QLNV_KHVONPHI_KHOACH_DTAO_BOI_DUONG_GD3N = "15";

	// Loai bao cao Tổng cục
	public static final String QLNV_KHVONPHI_TC_NCAU_KHOACH_DTXD_GD3N = "16";
	public static final String QLNV_KHVONPHI_TC_THOP_DTOAN_CHI_TX_HNAM = "17";
	public static final String QLNV_KHVONPHI_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM = "18";
	public static final String QLNV_KHVONPHI_TC_KHOACH_BQUAN_THOC_GAO_HNAM = "19";
	public static final String QLNV_KHVONPHI_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM = "20";
	public static final String QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N = "21";
	public static final String QLNV_KHVONPHI_TC_KHOACHC_QUY_LUONG_N1 = "22";
	public static final String QLNV_KHVONPHI_TC_DTOAN_CHI_DTQG_GD3N = "23";
	public static final String QLNV_KHVONPHI_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N = "24";
	public static final String QLNV_KHVONPHI_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N = "25";
	public static final String QLNV_KHVONPHI_TC_DTOAN_CHI_UDUNG_CNTT_GD3N = "26";
	public static final String QLNV_KHVONPHI_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N = "27";
	public static final String QLNV_KHVONPHI_TC_THOP_NCAU_CHI_NSNN_GD3N = "28";
	public static final String QLNV_KHVONPHI_TC_THOP_NNCAU_CHI_TX_GD3N = "29";
	public static final String QLNV_KHVONPHI_TC_CTIET_NCAU_CHI_TX_GD3N = "30";
	public static final String QLNV_KHVONPHI_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N = "31";

	// Trang thái báo cáo
	public static final String TT_BC_1 = "1"; // Đang soạn,
	public static final String TT_BC_2 = "2"; // Trình duyệt,
	public static final String TT_BC_3 = "3"; // Trưởng BP từ chối,
	public static final String TT_BC_4 = "4"; // Trưởng BP duyệt,
	public static final String TT_BC_5 = "5"; // Lãnh đạo từ chối,
	public static final String TT_BC_6 = "6"; // Lãnh đạo duyệt,
	public static final String TT_BC_7 = "7"; // Gửi ĐV cấp trên,
	public static final String TT_BC_8 = "8"; // ĐV cấp trên từ chối,
	public static final String TT_BC_9 = "9"; // Đv cấp trên duyệt"

	//Trạng thái giao số
	public static final String TT_DA_GIAO = "0"; // Đv cấp trên duyệt"
	public static final String TT_GIAO_LAI = "1"; // Đv cấp trên duyệt"

	// Danh sach quyen
	public static final Long LANH_DAO = 1l;// "Lãnh Đạo";
	public static final Long TRUONG_BO_PHAN = 2l;// "Trưởng Bộ Phận";
	public static final Long NHAN_VIEN = 3l;// "Nhân Viên";

	// Cap don vi
	public static final String CHI_CUC = "3";
	public static final String CUC_KHU_VUC = "2";
	public static final String TONG_CUC = "1";

	// file dinh kem
	public static final String QLNV_KH_VON_PHI = "QLNV_KH_VON_PHI";

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public final class ChiTieuKeHoachNamExport {
		public static final String SHEET_KE_HOACH_LUONG_THUC_DTNN = "Kế hoạch lương thực DTNN";
		public static final String SHEET_KE_HOACH_MUOI_DTNN = "Kế hoạch muối DTNN";
		public static final String SHEET_KE_HOACH_NHAP_VT_TB = "Kế hoạch nhập VT_TB";
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public final class ExcelHeader {
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
	public final class ExportDataType {
		public static final String CHI_TIEU_LUONG_THUC = "LUONG_THUC";
		public static final String CHI_TIEU_MUOI = "MUOI";
		public static final String CHI_TIEU_VAT_TU = "VAT_TU";
	}

}
