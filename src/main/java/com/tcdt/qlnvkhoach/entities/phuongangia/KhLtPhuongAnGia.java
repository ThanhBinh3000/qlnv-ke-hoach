package com.tcdt.qlnvkhoach.entities.phuongangia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.BaseEntity;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = KhLtPhuongAnGia.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class KhLtPhuongAnGia extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -8183308525284487273L;
	public static final String TABLE_NAME = "KH_LT_PHUONG_AN_GIA";
	public static final String CAN_CU = "CAN_CU";

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_LT_PHUONG_AN_GIA_SEQ")
	@SequenceGenerator(sequenceName = "KH_LT_PHUONG_AN_GIA_SEQ", allocationSize = 1, name = "KH_LT_PHUONG_AN_GIA_SEQ")
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "MA_DON_VI")
	private String maDonVi;

	@Column(name = "CAP_DVI")
	private String capDvi;


	@Column(name = "TRANG_THAI")
	private String trangThai;

	@Column(name = "NAM_KE_HOACH")
	private Long namKeHoach;

	@Column(name = "LOAI_HANG_HOA")
	private String loaiHangHoa;

	@Column(name = "SO_DE_XUAT")
	private String soDeXuat;

	@Column(name = "NGAY_KY")
	private LocalDate ngayKy;

	@Column(name = "TRICH_YEU")
	private String trichYeu;

	@Column(name = "CAN_CU_ID")
	private Long canCuId;

	@Column(name = "LOAI_GIA")
	private String loaiGia;

	@Column(name = "GHI_CHU")
	private String ghiChu;

	/**
	 * Thông tin chung
	 */
	@Column(name = "TTC_CHUNG_LOAI_HH")
	private String ttcChungLoaiHh;

	@Column(name = "TIEU_CHUAN_CL")
	private String tieuChuanCl;

	@Column(name = "SO_LUONG")
	private Long soLuong;

	@Column(name = "DIA_DIEM_DE_HANG")
	private String diaDiemDeHang;

	@Column(name = "GIA_DE_NGHI")
	private BigDecimal giaDeNghi;

	@Column(name = "GIA_DE_NGHI_VAT")
	private BigDecimal giaDeNghiVat;
	/**
	 * Căn cứ phương pháp xác định giá
	 */
	@Transient
	private List<KhLtPagCcPhapLy> canCuPhapLy = new ArrayList<>();

	@Column(name = "PHUONG_PHAP_ID")
	private Long phuongPhapId;

	@Column(name = "HANG_SX_TRONG_NUOC")
	private Boolean hangSxTrongNuoc;

	@Column(name = "HANG_NHAP_KHAU")
	private Boolean hangNhapKhau;

	@Column(name = "GIA_VON_NK")
	private BigDecimal giaVonNk;

	@Column(name = "CHI_PHI_CHUNG")
	private BigDecimal chiPhiChung;

	@Column(name = "TONG_CHI_PHI")
	private BigDecimal tongChiPhi;
	/**
	 * Thông tin khảo sát giá
	 * TTGHHTT: Thông tin giá của hàng hóa tương tự
	 */
	@Transient
	private List<KhLtPagKetQua> ketQuaKhaoSatGiaThiTruong = new ArrayList<>();

	@Transient
	private List<KhLtPagKetQua> ketQuaThamDinhGia = new ArrayList<>();

	@Column(name = "TTGHHTT_LOAI_HH")
	private String ttghhttLoaiHh;

	@Column(name = "TTGHHTT_CHUNG_LOAI_HH")
	private String ttghhttChungLoaiHh;

	@Column(name = "TTGHHTT_MO_TA_TC_TUONG_TU")
	private String ttghhttMoTaTcTuongTu; //Mô tả tính chất tương tự

	@Transient
	private List<KhLtPagKetQua> thongTinGiaHangHoaTuongTu = new ArrayList<>();
	/**
	 * Phân tích, dự báo biến động giá
	 * BDG: Biến động giá
	 */
	@Column(name = "BDG_NOI_DUNG")
	private String bdgNoiDung; //Nội dung: Biến động giá

	@Column(name = "BDG_GHI_CHU")
	private String bdgGhiChu; //Ghi chú: Biến động giá

	@Transient
	String tenTrangThai;

	@Transient
	String tenLoaiHh;

	@Transient
	String tenDvi;

	@Transient
	List<FileDinhKemChung> listFileCCs;
}
