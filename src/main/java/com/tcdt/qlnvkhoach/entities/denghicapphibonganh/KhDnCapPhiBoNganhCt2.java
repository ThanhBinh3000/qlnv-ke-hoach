package com.tcdt.qlnvkhoach.entities.denghicapphibonganh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = KhDnCapPhiBoNganhCt2.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhDnCapPhiBoNganhCt2 implements Serializable {

    public static final String TABLE_NAME = "KH_DN_CAP_PHI_BO_NGANH_CT2";
    private static final long serialVersionUID = -3759121884935806124L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_DN_CAP_PHI_BN_CT2_SEQ")
    @SequenceGenerator(sequenceName = "KH_DN_CAP_PHI_BN_CT2_SEQ", allocationSize = 1, name = "KH_DN_CAP_PHI_BN_CT2_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOAI_CHI_PHI")
    private String loaiChiPhi;

    @Column(name = "NAM_PHAT_SINH")
    private Long namPhatSinh;

    @Column(name = "TONG_TIEN")
    private BigDecimal tongTien;

    @Column(name = "KINH_PHI_DA_CAP")
    private BigDecimal kinhPhiDaCap;

    @Column(name = "YEU_CAU_CAP_THEM")
    private BigDecimal yeuCauCapThem;

    @Column(name = "CAP_PHI_BO_NGHANH_CT1_ID")
    private Long capPhiBoNghanhCt1Id;
}
