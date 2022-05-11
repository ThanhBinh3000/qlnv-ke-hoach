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
	public static final String QLNV_KHVONPHI_TC_KHOACH_DTAO_BOI_DUONG_GD3N = "32";

	// 3.2.8 Loại Bcao thực hiện dự toán chi
	public static final String QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL1 = "50";
	public static final String QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL2 = "51";
	public static final String QLNV_KHVONPHI_BCAO_THINH_SDUNG_DTOAN_PL3 = "52";

	// 3.2.9 Loại Bcao kết quả thực hiện vốn phí
	public static final String QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG = "90";
	public static final String QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG = "91";
	public static final String QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_NHAP_MUA = "92";
	public static final String QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_PHI_XUAT_HANG = "93";
	public static final String QLNV_KHVONPHI_BCAO_KQUA_CTIET_THIEN_BQUAN_LAN_DAU = "94";
	// 3.2.6.10
	public static final String QLNV_KHVONPHI_PA_GIAO_SO_KT = "60";

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

	// Trạng thái giao số
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

	public static final String QLNV_KHVONPHI_TD_DU_TOAN = "QLNV_KHVONPHI_TD_DU_TOAN";
	public static final String QLNV_KHVONPHI_DC_DU_TOAN_CHI = "QLNV_KHVONPHI_DC_DU_TOAN_CHI";
	public static final String QLNV_KHVONPHI_GIAO_DU_TOAN_CHI = "QLNV_KHVONPHI_GIAO_DU_TOAN_CHI";
	public static final String QLNV_KHVONPHI_CVMB_TTTH = "QLNV_KHVONPHI_CVMB_TTTH";
	public static final String QLNV_KHVONPHI_BC = "QLNV_KHVONPHI_BC";
	public static final String QLNV_KHVONPHI_PHANBO_DTOAN_CHI = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI";
	public static final String QLNV_KHVONPHI_QD_GIAO_DTOAN_CHI = "QLNV_KHVONPHI_PHANBO_DTOAN_CHI";
	public static final String QLNV_KHVONPHI_CAP_NGUON_VON_CHI = "QLNV_KHVONPHI_CAP_NGUON_VON_CHI";


	public static final String QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG = "QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG";

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

		// ke hoach von phi
		public static final String SHEET_CAPVON_MUA_BAN_TTOAN_THANG_DTQG = "Quản lý cấp vốn mua, bán, thanh toán tiền hàng DTQG";
		public static final String SHEET_QLNV_KHVONPHI_DC_DU_TOAN_CHI = "Quản lý điều chỉnh dự toán chi NSNN tại Tổng cục DTNN";
		public static final String SHEET_QLNV_KHVONPHI_BAO_CAO = "Quy trình báo cáo tại Tổng cục DTNN ";
		public static final String SHEET_QLNV_KHVONPHI_CAP_NGUON_VON_CHI = "Quản lý tình hình cấp vốn, phí hàng DTQG ";

		// lap tham dinh
		public static final String SHEET_DM_VONDT_XDCBGD3N = "Kế hoạch danh mục, vốn đầu tư XDCB giai đoạn 03 năm";//01
		public static final String SHEET_NXUAT_DTQG_HNAM_VATTU = "Nhu cầu nhập xuất hàng DTQG hàng năm";//02
		public static final String SHEET_KHOACH_BQUAN_HNAM_MAT_HANG = "Kế hoạch bảo quản hàng năm";//03
		public static final String SHEET_NCAU_XUAT_DTQG_VTRO_HNAM = "Nhu cầu xuất hàng DTQG viện trợ cứu trợ hàng năm";// 04
		public static final String SHEET_KHOACH_QUY_TIEN_LUONG_GD3N = "Kế hoạch quỹ tiền lương giai đoạn 03 năm";// 05
		public static final String SHEET_KHOACH_QUY_TIEN_LUONG_HNAM = "Kế hoạch quỹ tiền lương hàng năm";// 06
		public static final String SHEET_CHI_DTAI_DAN_NCKH_GD3N = "Thuyết minh chi các đề tài, dự án nghiên cứu khoa học giai đoạn 03 năm";// 07
		public static final String SHEET_VBAN_QPHAM_PLUAT_DTQG_GD3N = "Kế hoạch xây dựng văn bản quy phạm pháp luật dự trữ quốc gia giai đoạn 03 năm";// 08
		public static final String SHEET_CHI_UDUNG_CNTT_GD3N = "Dự toán chi ứng dụng CNTT giai đoạn 03 năm";// 09
		public static final String SHEET_DTOAN_CHI_MUASAM_MAYMOC_TBI_GD3N = "Xây dựng dự toán chi mua sắm máy móc thiết bị chuyên dùng 03 năm";// 10
		public static final String SHEET_NCAU_CHI_NSNN_GD3N = "Nhu cầu chi ngân sách nhà nước giai đoạn 03 năm";// 11
		public static final String SHEET_CHI_TX_GD3N = "Chi thường xuyên giai đoạn 03 năm";// 12
		public static final String SHEET_NCAU_PHI_NHAP_XUAT_GD3N = "Nhu cầu phí nhập, xuất theo các năm của giai đoạn 03 năm";// 13
		public static final String SHEET_KHOACH_CTAO_SCHUA_GD3N = "Kế hoạch cải tạo và sửa chữa lớn 03 năm";// 14
		public static final String SHEET_KHOACH_DTAO_BOI_DUONG_GD3N = "Kế hoạch đào tạo bồi dưỡng giai đoạn 03 năm";// 15
		// Loai bao cao Tổng cục
		public static final String SHEET_TC_NCAU_KHOACH_DTXD_GD3N = "Nhu cầu kế hoạch ĐTXD 03 năm";// 16
		public static final String SHEET_TC_THOP_DTOAN_CHI_TX_HNAM = "Tổng hợp Dự toán chi thường xuyên hàng năm";// 17
		public static final String SHEET_TC_DTOAN_PHI_NXUAT_DTQG_THOC_GAO_HNAM = "Dự toán phí nhập xuất hàng DTQG hàng năm";// 18
		public static final String SHEET_TC_KHOACH_BQUAN_THOC_GAO_HNAM = "Kế hoạch bảo quản hàng năm ";// 19
		public static final String SHEET_TC_DTOAN_PHI_XUAT_DTQG_VTRO_CTRO_HNAM = "Dự toán phí xuất hàng DTQG viện trợ cứu trợ hàng năm";// 20
		public static final String SHEET_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N = "Kế hoạch dự toán cải tạo sửa chữa hệ thống kho tàng 03 năm";// 21
		public static final String SHEET_TC_KHOACHC_QUY_LUONG_N1 = "Kế hoạch quỹ tiền lương năm N+1";// 22
		public static final String SHEET_TC_DTOAN_CHI_DTQG_GD3N = "Dự toán chi dự trữ quốc gia giai đoạn 03 năm";// 23
		public static final String SHEET_TC_TMINH_CHI_CAC_DTAI_DAN_NCKH_GD3N = "Thuyết minh chi các đề tài, dự án nghiên cứu khoa học giai đoạn 03 năm";// 24
		public static final String SHEET_TC_KHOACH_XDUNG_VBAN_QPHAM_PLUAT_DTQG_GD3N = "Xây dựng văn bản quy phạm pháp luật dự trữ quốc gia giai đoạn 03 năm";// 25
		public static final String SHEET_TC_DTOAN_CHI_UDUNG_CNTT_GD3N = "Dự toán chi ứng dụng CNTT giai đoạn 03 năm";// 26
		public static final String SHEET_TC_DTOAN_CHI_MSAM_MMOC_TBI_CHUYEN_DUNG_GD3N = "Dự toán chi mua sắm máy móc thiết bị chuyên dùng 03 năm";// 27
		public static final String SHEET_TC_THOP_NCAU_CHI_NSNN_GD3N = "Tổng hợp nhu cầu chi ngân sách nhà nước giai đoạn 03 năm";// 28
		public static final String SHEET_TC_THOP_NNCAU_CHI_TX_GD3N = "Tổng hợp nhu cầu chi thường xuyên giai đoạn 03 năm";// 29
		public static final String SHEET_TC_CTIET_NCAU_CHI_TX_GD3N = "Chi tiết nhu cầu chi thường xuyên giai đoạn 03 năm";// 30
		public static final String SHEET_TC_THOP_MTIEU_NVU_CYEU_NCAU_CHI_MOI_GD3N = "Tổng hợp mục tiêu nhiệm vụ chủ yếu và nhu cầu chi mới giai đoạn 03 năm";// 31
		public static final String SHEET_TC_KHOACH_DTAO_BOI_DUONG_GD3N = "Xây dựng Kế hoạch đào tạo bồi dưỡng giai đoạn 03 năm";// 32

		// quyet dinh von phi
		public static final String SHEET_DCHINH_SAU_QTOAN = "Điều chỉnh sau quyết toán";
		public static final String SHEET_QTOAN_DTQG = "Quyết toán dự trữ quốc gia";
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

		// ke hoach von phi
		public static final String CAP_VON_MUA_BAN = "CAP_VON_MUA_BAN";
		public static final String QLNV_KHVONPHI_DC_DU_TOAN_CHI = "QLNV_KHVONPHI_DC_DU_TOAN_CHI";
		public static final String QLNV_KHVONPHI_BC = "QLNV_KHVONPHI_BC";
		public static final String QLNV_KHVONPHI_CAP_NGUON_VON_CHI = "QLNV_KHVONPHI_CAP_NGUON_VON_CHI";

		// quyet toan von phi
		public static final String DCHINH_SAU_QTOAN = "DCHINH_SAU_QTOAN";
		public static final String QTOAN_DTQG = "QTOAN_DTQG";

		// quyet dinh giao du toan chi
		public static final String PHANBO_GIAO_DTOAN_CHI = "PHANBO_GIAO_DTOAN_CHI";
		public static final String QD_GIAO_DTOAN_CHI = "QD_GIAO_DTOAN_CHI";
	}
	

	// mã loại danh mục dùng chung QLNV_DM_KHOACHVON

	public static final String DM_LOAI_KE_HOACH = "KH";//Khối dự án
	public static final String DM_KHOI_DAN = "KDA";//Khối dự án
	public static final String DM_DIA_DIEM_XD = "DDXD";//Địa điểm xây dựng
	public static final String DM_MA_NGANH_KT = "MNKT";//Mã ngành kinh tế
	public static final String DM_TBI_VTU = "DMTVT";//Danh mục tên vật tư
	public static final String DM_NHOM = "DMN";//Danh mục nhóm
	public static final String DM_DVI_TINH = "DMDVT";//Danh đơn vị tính
	public static final String DM_NOI_DUNG = "DMNDC";

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
