package com.tcdt.qlnvkhoach.response.phuongangia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhLtPhuongAnGiaRes {
	private Long id;

	private String maDonVi;

	private String trangThai;

	private Long namKeHoach;

	private String loaiHangHoa;

	private Long soDeXuat;

	private LocalDate ngayKy;

	private String trichYeu;

	private Long canCuId;

	private String loaiGia;

	private String ghiChu;

	private String ttcChungLoaiHh;

	private String tieuChuanCl;

	private Long soLuong;

	private String diaDiemDeHang;

	private BigDecimal giaDeNghi;
	/**
	 * Căn cứ phương pháp xác định giá
	 */

	private List<KhLtPagCcPhapLyRes> canCuPhapLy = new ArrayList<>();

	private Long phuongPhapId;

	private Boolean hangSxTrongNuoc;

	private Boolean hangNhapKhau;

	private BigDecimal giaVonNk;

	private BigDecimal chiPhiChung;

	private BigDecimal tongChiPhi;
	/**
	 * Thông tin khảo sát giá
	 * TTGHHTT: Thông tin giá của hàng hóa tương tự
	 */

	private List<KhLtPagKetQuaRes> ketQuaKhaoSatGiaThiTruong = new ArrayList<>();

	private List<KhLtPagKetQuaRes> ketQuaThamDinhGia = new ArrayList<>();

	private String ttghhttLoaiHh;

	private String ttghhttChungLoaiHh;

	private String ttghhttMoTaTcTuongTu; //Mô tả tính chất tương tự

	private List<KhLtPagKetQuaRes> thongTinGiaHangHoaTuongTu = new ArrayList<>();
	/**
	 * Phân tích, dự báo biến động giá
	 * BDG: Biến động giá
	 */

	private String bdgNoiDung; //Nội dung: Biến động giá

	private String bdgGhiChu; //Ghi chú: Biến động giá
}
