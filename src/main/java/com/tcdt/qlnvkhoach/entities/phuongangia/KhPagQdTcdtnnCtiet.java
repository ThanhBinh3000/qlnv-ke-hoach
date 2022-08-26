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
public class KhPagQdTcdtnnCtiet {
    public static final String TABLE_NAME = "KH_PAG_QD_TCDTNN_CTIET";
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_PAG_QD_TCDTNN_CTIET_SEQ")
    @SequenceGenerator(sequenceName = "KH_PAG_QD_TCDTNN_CTIET_SEQ", allocationSize = 1, name = "KH_PAG_QD_TCDTNN_CTIET_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "GIA_QD_BTC")
    private BigDecimal giaQdBtc;

    @Column(name = "GIA_QD_VAT_BTC")
    private BigDecimal giaQdVatBtc;

    @Column(name = "QD_TCDTNN_ID")
    private Long qdBtcId;

    @Column(name = "SO_LUONG")
    private Long soLuong;

    @Column(name = "GIA_QD_TCDTNN")
    private BigDecimal giaDn;

    @Column(name = "GIA_QD_VAT_TCDTNN")
    private BigDecimal giaDnVat;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Column(name = "TEN_DVI")
    private String tenDvi;
}
