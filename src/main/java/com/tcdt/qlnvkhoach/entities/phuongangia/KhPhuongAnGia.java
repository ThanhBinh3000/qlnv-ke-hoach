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
@Table(name = KhPhuongAnGia.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class KhPhuongAnGia extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8183308525284487273L;
    public static final String TABLE_NAME = "KH_PHUONG_AN_GIA";
    public static final String CAN_CU = "CAN_CU";

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_LT_PHUONG_AN_GIA_SEQ")
    @SequenceGenerator(sequenceName = "KH_LT_PHUONG_AN_GIA_SEQ", allocationSize = 1, name = "KH_LT_PHUONG_AN_GIA_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Column(name = "CAP_DVI")
    private String capDvi;

    @Column(name = "TRANG_THAI")
    private String trangThai;

    @Column(name = "TRANG_THAI_TH")
    private String trangThaiTh;

    @Column(name = "NAM_KE_HOACH")
    private Long namKeHoach;

    @Column(name = "LOAI_VTHH")
    private String loaiVthh;

    @Column(name = "SO_DE_XUAT",unique = true)
    private String soDeXuat;

    @Column(name = "NGAY_KY")
    private LocalDate ngayKy;

    @Column(name = "TRICH_YEU")
    private String trichYeu;

    @Column(name = "SO_CAN_CU")
    private String soCanCu;

    @Column(name = "LOAI_GIA")
    private String loaiGia;

    @Transient
    private String tenLoaiGia;

    @Column(name = "GHI_CHU")
    private String ghiChu;

    /**
     * Thông tin chung
     */
    @Column(name = "CLOAI_VTHH")
    private String cloaiVthh;

    @Transient
    private String tenCloaiVthh;

    @Column(name = "TIEU_CHUAN_CL")
    private String tchuanCluong;

    @Column(name = "SO_LUONG")
    private Long soLuong;


    @Transient
    private List<KhPagDiaDiemDeHang> diaDiemDeHangs = new ArrayList<>();

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
    private List<KhPagCcPhapLy> canCuPhapLy = new ArrayList<>();

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

    @Column(name = "CHI_PHI_PBO")
    private BigDecimal chiPhiPbo;

    @Column(name = "TONG_CHI_PHI")
    private BigDecimal tongChiPhi;
    /**
     * Thông tin khảo sát giá
     * TTGHHTT: Thông tin giá của hàng hóa tương tự
     */
    @Transient
    private List<KhPagKetQua> ketQuaKhaoSatGiaThiTruong = new ArrayList<>();

    @Transient
    private List<KhPagKetQua> ketQuaThamDinhGia = new ArrayList<>();

    @Column(name = "TTGHHTT_LOAI_HH")
    private String ttghhttLoaiHh;

    @Column(name = "TTGHHTT_CHUNG_LOAI_HH")
    private String ttghhttChungLoaiHh;

    @Column(name = "TTGHHTT_MO_TA_TC_TUONG_TU")
    private String ttghhttMoTaTcTuongTu; //Mô tả tính chất tương tự

    @Transient
    private List<KhPagKetQua> thongTinGiaHangHoaTuongTu = new ArrayList<>();
    /**
     * Phân tích, dự báo biến động giá
     * BDG: Biến động giá
     */
    @Column(name = "NOI_DUNG")
    private String noiDung; //Nội dung: Biến động giá

    @Transient
    String tenTrangThai;

    @Transient
    String tenTrangThaiTh;

    @Transient
    String tenLoaiVthh;

    @Transient
    String tenDvi;

    @Transient
    List<FileDinhKemChung> listFileCCs;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "MO_TA")
    private String moTa;

    @Column(name = "VAT")
    private BigDecimal vat;

    @Column(name = "MA_PPHAP_XDG")
    private String maPphapXdg;

    @Column(name = "LOAI_HANG_XDG")
    private String loaiHangXdg;

    @Column(name = "NGUOI_GUI_DUYET")
    private Long nguoiGuiDuyet;

    @Column(name = "NGUOI_PHE_DUYET")
    private Long nguoiPheDuyet;
}
