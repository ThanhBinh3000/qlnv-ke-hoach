package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
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
public class KhLtPhuongAnGiaReq {
	private Long id;

	private String maDonVi;

	private String trangThai;

	private Long namKeHoach;

	private String loaiHangHoa;

	private String soDeXuat;

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

	private BigDecimal giaDeNghiVat;

	private List<FileDinhKemReq> fileDinhKems;
	/**
	 * Căn cứ phương pháp xác định giá
	 */

	private List<KhLtPagCcPhapLyReq> canCuPhapLy = new ArrayList<>();

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

	private List<KhLtPagKetQuaReq> ketQuaKhaoSatGiaThiTruong = new ArrayList<>();

	private List<KhLtPagKetQuaReq> ketQuaThamDinhGia = new ArrayList<>();

	private String ttghhttLoaiHh;

	private String ttghhttChungLoaiHh;

	private String ttghhttMoTaTcTuongTu; //Mô tả tính chất tương tự

	private List<KhLtPagKetQuaReq> thongTinGiaHangHoaTuongTu = new ArrayList<>();
	/**
	 * Phân tích, dự báo biến động giá
	 * BDG: Biến động giá
	 */

	private String bdgNoiDung; //Nội dung: Biến động giá

	private String bdgGhiChu; //Ghi chú: Biến động giá
}
