package com.tcdt.qlnvkhoach.entities.denghicapphibonganh;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.TrangThaiBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = KhDnCapPhiBoNganh.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhDnCapPhiBoNganh extends TrangThaiBaseEntity implements Serializable {

    public static final String TABLE_NAME = "KH_DN_CAP_PHI_BO_NGANH";
    private static final long serialVersionUID = 1963436555088361827L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_DN_CAP_PHI_BO_NGANH_SEQ")
    @SequenceGenerator(sequenceName = "KH_DN_CAP_VON_BO_NGANH_SEQ", allocationSize = 1, name = "KH_DN_CAP_PHI_BO_NGANH_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAM")
    private Integer nam;

    @Column(name = "MA_BO_NGANH")
    private String maBoNganh;

    @Column(name = "SO_DE_NGHI")
    private String soDeNghi;

    @Column(name = "NGAY_DE_NGHI")
    private LocalDate ngayDeNghi;

    @Column(name = "GHI_CHU")
    private String ghiChu;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Column(name = "CAP_DVI")
    private String capDvi;

    @Transient
    private List<FileDinhKemChung> fileDinhKems;

    @Transient
    private List<KhDnCapPhiBoNganhCt1> ct1List;

}
