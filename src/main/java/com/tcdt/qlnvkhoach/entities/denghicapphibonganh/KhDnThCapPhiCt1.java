package com.tcdt.qlnvkhoach.entities.denghicapphibonganh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = KhDnThCapPhiCt1.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhDnThCapPhiCt1 implements Serializable {
    public static final String TABLE_NAME = "KH_DN_TH_CAP_PHI_CT1";
    private static final long serialVersionUID = 5239008888150962416L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_DN_TH_CAP_PHI_CT1_SEQ")
    @SequenceGenerator(sequenceName = "KH_DN_TH_CAP_PHI_CT1_SEQ", allocationSize = 1, name = "KH_DN_TH_CAP_PHI_CT1_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TEN_DV_CUNG_CAP")
    private String tenDvCungCap;

    @Column(name = "SO_TAI_KHOAN")
    private Long soTaiKhoan;

    @Column(name = "NGAN_HANG")
    private String nganHang;

    @Column(name = "KH_DN_TH_CAP_PHI_ID")
    private Long khDnThCapPhiId;

    @Column(name = "YC_CAP_THEM_PHI")
    private BigDecimal ycCapThemPhi;

    @Column(name = "KINH_PHI_DA_CAP")
    private BigDecimal kinhPhiDaCap;

    @Column(name = "TONG_TIEN")
    private BigDecimal tongTien;

    @Column(name = "LOAI")
    private String loai;// Đề xuất: 00, Phương Án: 01

    @Column(name = "MA_BO_NGANH")
    private String maBoNganh;

    @Transient
    private List<KhDnThCapPhiCt2> ct2s;
}
