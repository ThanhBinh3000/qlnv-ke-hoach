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
@Table(name = KhPagQdTcdtnnCtiet.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhPagQdTcdtnnCtiet {
    public static final String TABLE_NAME = "KH_PAG_QD_TCDTNN_CTIET";

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GIA_QD_BTC")
    private BigDecimal giaQdBtc;

    @Column(name = "GIA_QD_VAT_BTC")
    private BigDecimal giaQdVatBtc;

    @Column(name = "QD_TCDTNN_ID")
    private Long qdTcdtnnId;

    @Column(name = "SO_LUONG")
    private Long soLuong;

    @Column(name = "GIA_QD_TCDTNN")
    private BigDecimal giaQdTcdtnn;

    @Column(name = "GIA_QD_VAT_TCDTNN")
    private BigDecimal giaQdVatTcdtnn;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Column(name = "TEN_DVI")
    private String tenDvi;
}
