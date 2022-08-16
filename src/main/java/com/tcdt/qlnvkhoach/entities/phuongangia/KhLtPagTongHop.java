package com.tcdt.qlnvkhoach.entities.phuongangia;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = KhLtPagKetQua.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhLtPagTongHop implements Serializable {
    public static final String TABLE_NAME = "KH_LT_PAG_TONG_HOP";

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_LT_PAG_TONG_HOP_SEQ")
    @SequenceGenerator(sequenceName = "KH_LT_PAG_TONG_HOP_SEQ", allocationSize = 1, name = "KH_LT_PAG_TONG_HOP_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SO_TT")
    private String soTT;

    @Column(name = "NGAY_TONG_HOP")
    private LocalDate ngayTongHop;

    @Column(name = "NOI_DUNG")
    private String noiDung;

    @Column(name="NAM_TONG_HOP")
    private Long namTongHop;

    @Column(name = "LOAI_HANG_HOA")
    private String loaiHangHoa;

    @Column(name="CHUNG_LOAI_HH")
    private String chungLoaiHh;

    @Column(name = "LOAI_GIA")
    private String loaiGia;

    @Column(name="TRANG_THAI")
    private String trangThai;

    @Column(name="TRANG_THAI_TH")
    private String trangThaiTH;

    @Column(name="GIA_KS_TT_TU")
    private BigDecimal giaKsTtTu;

    @Column(name="GIA_KS_TT_DEN")
    private BigDecimal giaKsTtDen;

    @Column(name="GIA_KS_TT_VAT_TU")
    private BigDecimal giaKsTtVatTu;

    @Column(name="GIA_KS_TT_VAT_DEN")
    private BigDecimal giaKsTtVatDen;

    @Column(name="GIA_TD_TU")
    private BigDecimal giaTdTu;

    @Column(name="GIA_TD_DEN")
    private BigDecimal giaTdDen;

    @Column(name="GIA_TD_VAT_TU")
    private BigDecimal giaTdVatTu;

    @Column(name="GIA_TD_VAT_DEN")
    private BigDecimal giaTdVatDen;

    @Column(name="GIA_DE_NGHI_TU")
    private BigDecimal giaDnTu;

    @Column(name="GIA_DE_NGHI_DEN")
    private BigDecimal giaDnDen;

    @Column(name="GIA_DE_NGHI_VAT_TU")
    private BigDecimal giaDnVatTu;

    @Column(name="GIA_DE_NGHI_VAT_DEN")
    private BigDecimal giaDnVatDen;

    @Column(name="GHI_CHU")
    private String ghiChu;

    @Transient
    private List<KhLtPhuongAnGia> phuongAnGias = new ArrayList<>();
}
