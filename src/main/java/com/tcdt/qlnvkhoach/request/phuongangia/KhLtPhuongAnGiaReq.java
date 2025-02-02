package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagPpXacDinhGia;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTtChung;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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

	private String maDvi;

	private String trangThai;

	private Long namKeHoach;

	private String loaiVthh;

	private String soDeXuat;

	private LocalDate ngayKy;

	private LocalDate tgianNhang;


	private String trichYeu;

	private Long canCuId;

	private String loaiGia;

	private String ghiChu;

	private String cloaiVthh;

	private String tchuanCluong;

	private Long soLuong;

	private String diaDiemDeHang;

	private BigDecimal giaDeNghi;

	private BigDecimal giaDeNghiVat;

	private List<FileDinhKemReq> fileDinhKems;
	/**
	 * Căn cứ phương pháp xác định giá
	 */

	private List<KhLtPagCcPhapLyReq> canCuPhapLy = new ArrayList<>();

	private Boolean hangSxTrongNuoc;

	private Boolean hangNhapKhau;

	private BigDecimal giaVonNk;

	private BigDecimal chiPhiChung;

	private BigDecimal chiPhiPbo;

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

	private String noiDung; //Nội dung: Biến động giá

	/**
	 * Địa điêm để hàng
	 */
	private List<KhLtPagDiaDiemDeHangReq> diaDiemDeHangs = new ArrayList<>();

	private String moTa;

	private BigDecimal vat;

	private String maPphapXdg;

	private String loaiHangXdg;

	private String soCanCu;

	private String  type;

	private String pagType;

	/**
	 * Các field dành riêng cho vật tư
	 */
	private String nguoiKy;

	private String qdCtKhNam;

	private List<KhPagTtChung> pagTtChungs;

	private List<KhPagPpXacDinhGia> pagPpXacDinhGias;

	private String moTaHangHoa;
}
