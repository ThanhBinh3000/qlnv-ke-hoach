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
@Table(name = KhPagQdDcTcdtnnCTiet.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhPagQdDcTcdtnnCTiet {
    public static final String TABLE_NAME = "KH_PAG_GCT_QDDC_TCDTNN_CTIET";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Column(name = "TEN_DVI")
    private String tenDvi;

    @Column(name = "SO_LUONG")
    private Long soLuong;

    @Column(name = "DON_GIA")
    private BigDecimal donGia;

    @Column(name = "DON_GIA_VAT")
    private BigDecimal donGiaVat;

    @Column(name = "QD_DC_TCDTNN_ID")
    private Long qdDcTcdtnnId;

}
