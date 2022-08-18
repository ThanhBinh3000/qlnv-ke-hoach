package com.tcdt.qlnvkhoach.entities.phuongangia;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.BaseEntity;
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
@Table(name = KhPagTongHop.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhPagTongHop extends BaseEntity implements Serializable {
    public static final String TABLE_NAME = "KH_PAG_TONG_HOP";

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_LT_PAG_TONG_HOP_SEQ")
    @SequenceGenerator(sequenceName = "KH_LT_PAG_TONG_HOP_SEQ", allocationSize = 1, name = "KH_LT_PAG_TONG_HOP_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SO_TT")
    private String soTT;

    @Column(name="TRICH_YEU")
    private String trichYeu;

    @Column(name = "NGAY_TONG_HOP")
    private LocalDate ngayTongHop;

    @Column(name = "NOI_DUNG")
    private String noiDung;

    @Column(name="NAM_TONG_HOP")
    private Long namTongHop;

    @Column(name = "LOAI_VTHH")
    private String loaiVthh;

    @Transient
    private String tenloaiVthh;

    @Column(name="CLOAI_VTHH")
    private String cloaiVthh;

    @Transient
    private String tencloaiVthh;

    @Column(name = "LOAI_GIA")
    private String loaiGia;

    @Transient
    private String tenloaiGia;

    @Column(name="TRANG_THAI")
    private String trangThai;

    @Transient
    private String tentrangThai;

    @Column(name="TRANG_THAI_TH")
    private String trangThaiTH;

    @Transient
    private String tentrangThaiTH;

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
    private List<KhPagTongHopCTiet> pagChitiets = new ArrayList<>();

    @Column(name = "MA_DVI")
    private String maDvi;

    @Column(name = "CAP_DVI")
    private String capDvi;

    @Column(name = "SO_TO_TRINH",unique = true)
    private String soToTrinh;

    /**
     * trạng thái tờ trình
     */
    @Column(name = "TT_TO_TRINH")
    private String ttToTrinh;

    /**
     * thông tin tờ trình
     */
    @Column(name = "TT_GIA_DN")
    private BigDecimal ttGiaDn;

    @Column(name = "TT_GIA_DN_VAT")
    private BigDecimal ttGiaDnVat;

    @Column(name = "TYPE")
    private String type;


}
