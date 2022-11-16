package com.tcdt.qlnvkhoach.entities.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.TrangThaiBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KH_DN_TH_CAP_VON_CT1")
@EqualsAndHashCode(callSuper = false)
public class KhDnThCapVonCt1 implements Serializable {

    private static final long serialVersionUID = 6532917948914821538L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_DN_TH_CAP_VON_CT1_SEQ")
    @SequenceGenerator(sequenceName = "KH_DN_TH_CAP_VON_CT1_SEQ", allocationSize = 1, name = "KH_DN_TH_CAP_VON_CT1_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "KH_DN_TH_ID")
    private Long khDnThId;

    @Column(name = "KH_DN_CAP_VON_ID")
    private Long khDnCapVonId;

    @Column(name = "TC_CAP_THEM")
    private BigDecimal tcCapThem;

    @Column(name = "TEN_BO_NGANH")
    private String tenBoNganh;

    @Column(name = "LOAI_BN")
    private String loaiBn;

    @Column(name = "LOAI_HANG")
    private String loaiHang;

    @Column(name = "MA_BN")
    private String maBn;

    @Column(name = "TONG_TIEN")
    private BigDecimal tongTien;

    @Column(name = "KINH_PHI_DA_CAP")
    private BigDecimal kinhPhiDaCap;

    @Column(name = "YC_CAP_THEM")
    private BigDecimal ycCapThem;

    @Column(name = "NAM")
    private Integer nam;

    @Column(name = "SO_DE_NGHI")
    private String soDeNghi;

    @Column(name = "NGAY_DE_NGHI")
    private LocalDate ngayDeNghi;

    @Transient
    private Boolean isSum;

    @Transient
    private String parentName;
}
