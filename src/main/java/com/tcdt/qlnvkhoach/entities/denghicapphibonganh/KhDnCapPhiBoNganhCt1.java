package com.tcdt.qlnvkhoach.entities.denghicapphibonganh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = KhDnCapPhiBoNganhCt1.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhDnCapPhiBoNganhCt1 implements Serializable {
    public static final String TABLE_NAME = "KH_DN_CAP_PHI_BO_NGHANH_CT1";
    private static final long serialVersionUID = 5239008888150962416L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_DN_CP_BN_CT1_SEQ")
    @SequenceGenerator(sequenceName = "KH_DN_CP_BN_CT1_SEQ", allocationSize = 1, name = "KH_DN_CP_BN_CT1_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TEN_DV_CUNG_CAP")
    private String tenDvCungCap;

    @Column(name = "SO_TAI_KHOAN")
    private Long soTaiKhoan;

    @Column(name = "NGAN_HANG")
    private String nganHang;

    @Column(name = "MA_VAT_TU_CHA")
    private String maVatTuCha;

    @Column(name = "MA_VAT_TU")
    private String maVatTu;

    @Column(name = "TEN_HANG_HOA")
    private String tenHangHoa;

    @Column(name = "DN_CAP_PHI_ID")
    private Long dnCapPhiId;

    @Column(name = "YC_CAP_THEM_PHI")
    private String ycCapThemPhi;
    @Transient
    private List<KhDnCapPhiBoNganhCt2> ct2List;
}
