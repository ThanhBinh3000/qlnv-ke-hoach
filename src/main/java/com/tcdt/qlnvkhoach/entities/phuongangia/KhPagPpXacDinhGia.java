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

@Entity
@Table(name = KhPagPpXacDinhGia.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KhPagPpXacDinhGia implements Serializable {

    private static final long serialVersionUID = -9158383107212840699L;
    public static final String TABLE_NAME = "KH_PAG_PP_XAC_DINH_GIA";

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_PAG_PP_XAC_DINH_GIA_SEQ")
    @SequenceGenerator(sequenceName = "KH_PAG_PP_XAC_DINH_GIA_SEQ", allocationSize = 1, name = "KH_PAG_PP_XAC_DINH_GIA_SEQ")
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CLOAI_VTHH")
    private String cloaiVthh;

    @Column(name = "GIA_VON_NK")
    private BigDecimal giaVonNk;

    @Column(name = "CHI_PHI_CHUNG")
    private BigDecimal chiPhiChung;

    @Column(name = "TONG_CHI_PHI")
    private BigDecimal tongChiPhi;
    /**
     * {@link KhPhuongAnGia}
     */
    @Column(name = "PAG_ID")
    private Long phuongAnGiaId;
}
