package com.tcdt.qlnvkhoach.entities.phuongangia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = KhPagTongHopCTiet.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhPagTongHopCTiet {
    public static final String TABLE_NAME = "KH_PAG_TONG_HOP_CTIET";
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_LT_PAG_TONG_HOP_CTIET_SEQ")
    @SequenceGenerator(sequenceName = "KH_LT_PAG_TONG_HOP_CTIET_SEQ", allocationSize = 1, name = "KH_LT_PAG_TONG_HOP_CTIET_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SO_DX")
    private String soDx;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Transient
    private String tenDvi;

    @Column(name = "SO_LUONG")
    private Long soLuong;

    @Column(name = "GIA_DN")
    private BigDecimal giaDn;

    @Column(name = "GIA_DN_VAT")
    private BigDecimal giaDnVat;

    @Column(name = "GIA_QD")
    private BigDecimal giaQd;

    @Column(name = "GIA_QD_VAT")
    private BigDecimal giaQdVat;

    @Column(name = "QD_BTC_ID")
    private Long QdBtcId;

    @Column(name = "QD_TCDTNN_ID")
    private Long qdTcdtnnId;

    @Column(name = "PAG_TH_ID")
    private Long pagThId;

    @Column(name = "PAG_ID")
    private Long pagId;

    @Column(name = "TRANG_THAI")
    private String trangThai;

    @Column(name = "GIA_QD_TCDTNN")
    private BigDecimal giaQdTcdtnn;

    @Column(name = "GIA_QD_VAT_TCDTNN")
    private BigDecimal giaQdVatTcdtnn;
    @Transient
    private String tenTrangThai;

    @Transient
    private BigDecimal giaTdttBtc;

    @Transient
    private BigDecimal giaTdttBtcVat;
    @Transient
    private Long phuongAnGiaId;
}
