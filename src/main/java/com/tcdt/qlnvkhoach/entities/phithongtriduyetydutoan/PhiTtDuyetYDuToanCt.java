package com.tcdt.qlnvkhoach.entities.phithongtriduyetydutoan;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = PhiTtDuyetYDuToanCt.TABLE_NAME)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PhiTtDuyetYDuToanCt implements Serializable {
    public static final String TABLE_NAME = "KH_PHI_TT_DY_DTOAN_CT";
    private static final long serialVersionUID = 6242326510650973156L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_PHI_TT_DY_DTOAN_CT_SEQ")
    @SequenceGenerator(sequenceName = "KH_PHI_TT_DY_DTOAN_CT_SEQ", allocationSize = 1, name = "KH_PHI_TT_DY_DTOAN_CT_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MUC")
    private String muc;

    @Column(name = "TIEU_MUC")
    private String tieuMuc;

    @Column(name = "SO_TIEN")
    private BigDecimal soTien;

    @Column(name = "CHU_THICH")
    private String chuThich;

    @Column(name = "ID_TTDYDT")
    private Long idTtdydt;
}
