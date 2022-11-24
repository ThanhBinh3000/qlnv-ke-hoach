package com.tcdt.qlnvkhoach.entities.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.TrangThaiBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = KhDnThCapVon.TABLE_NAME)
@EqualsAndHashCode(callSuper = false)
public class KhDnThCapVon extends TrangThaiBaseEntity implements Serializable {

    public static final String TABLE_NAME = "KH_DN_TH_CAP_VON";
    private static final long serialVersionUID = 6532917948914821538L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_DN_TH_CAP_VON_SEQ")
    @SequenceGenerator(sequenceName = "KH_DN_TH_CAP_VON_SEQ", allocationSize = 1, name = "KH_DN_TH_CAP_VON_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MA_TONG_HOP")
    private String maTongHop;

    @Column(name = "NAM")
    private Integer nam;

    @Column(name = "NGUON_TONG_HOP")
    private String nguonTongHop;

    @Column(name = "NGAY_TONG_HOP")
    private LocalDate ngayTongHop;

    @Column(name = "NOI_DUNG")
    private String noiDung;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Column(name = "CAP_DVI")
    private String capDvi;

    @Column(name = "TONG_TIEN")
    private BigDecimal tongTien;

    @Column(name = "KINH_PHI_DA_CAP")
    private BigDecimal kinhPhiDaCap;

    @Column(name = "YC_CAP_THEM")
    private BigDecimal ycCapThem;

    @Column(name = "TC_CAP_THEM")
    private BigDecimal tcCapThem;

    @Column(name = "MA_TO_TRINH")
    private String maToTrinh;

    @Transient
    private List<KhDnCapVonBoNganh> cts = new ArrayList<>();

    @Transient
    private List<KhDnThCapVonCt1> ct1s = new ArrayList<>();

    @Transient
    private FileDinhKemChung fileDinhKem;
}