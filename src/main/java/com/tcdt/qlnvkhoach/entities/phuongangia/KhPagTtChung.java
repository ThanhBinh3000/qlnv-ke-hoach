package com.tcdt.qlnvkhoach.entities.phuongangia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = KhPagTtChung.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KhPagTtChung implements Serializable {

    private static final long serialVersionUID = -9158383107212840699L;
    public static final String TABLE_NAME = "KH_PAG_TT_CHUNG";

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_PAG_TT_CHUNG_SEQ")
    @SequenceGenerator(sequenceName = "KH_PAG_TT_CHUNG_SEQ", allocationSize = 1, name = "KH_PAG_TT_CHUNG_SEQ")
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CLOAI_VTHH")
    private String cloaiVthh;

    @Column(name = "TIEU_CHUAN_CL")
    private String tchuanCluong;

    @Column(name = "SO_LUONG")
    private BigDecimal soLuong;

    @Column(name = "DON_VI_TINH")
    private String donViTinh;

    @Column(name = "GIA_DN")
    private BigDecimal giaDn;

    @Column(name = "GIA_DN_VAT")
    private BigDecimal giaDnVat;

    @Column(name = "GIA_QD")
    private BigDecimal giaQd;

    @Column(name = "GIA_QD_VAT")
    private BigDecimal giaQdVat;
    /**
     * {@link KhPhuongAnGia}
     */
    @Column(name = "PAG_ID")
    private Long phuongAnGiaId;

    @Column(name = "QD_BTC_ID")
    private Long qdBtcId;

    @Transient
    private String tenCloaiVthh;
}
