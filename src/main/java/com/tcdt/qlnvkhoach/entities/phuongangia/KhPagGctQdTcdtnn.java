package com.tcdt.qlnvkhoach.entities.phuongangia;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = KhPagGctQdTcdtnn.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhPagGctQdTcdtnn extends BaseEntity implements Serializable {
    public static final String TABLE_NAME = "KH_PAG_GCT_QD_TCDTNN";

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_PAG_GCT_QD_TCDTNN_SEQ")
    @SequenceGenerator(sequenceName = "KH_PAG_GCT_QD_TCDTNN_SEQ", allocationSize = 1, name = "KH_PAG_GCT_QD_TCDTNN_SEQ")
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Column(name="TRANG_THAI")
    private String trangThai;

    @Column(name="NAM_KE_HOACH")
    private Long namKeHoach;

    @Column(name = "SO_QD")
    private String soQd;

    @Column(name = "NGAY_KY")
    private LocalDate ngayKy;

    @Column(name = "NGAY_HIEU_LUC")
    private LocalDate ngayHieuLuc;

    @Column(name="SO_TO_TRINH")
    private String soToTrinh;

    @Column(name = "LOAI_VTHH")
    private String loaiVthh;

    @Column(name = "CLOAI_VTHH")
    private String cloaiVthh;

    @Column(name = "LOAI_GIA")
    private String loaiGia;

    @Column(name = "TIEU_CHUAN_CL")
    private String tchuanCluong;

    @Column(name = "TRICH_YEU")
    private String trichYeu;

    @Column(name = "GHI_CHU")
    private String ghiChu;

    @Column(name = "CAP_DVI")
    private String capDvi;

    @Column(name="GIA_MTDBTT_BTC")
    private BigDecimal giaMtdbttBtc;

    @Column(name="GIA_MTDBTT_VAT_BTC")
    private BigDecimal giaMtdbttVatBtc;

    @Column(name="NGUOI_PDUYET ")
    private String nguoiPduyet;

    @Column(name="NGAY_PDUYET ")
    private Date ngayPduyet;

    @Column(name="LASTEST")
    private Integer lastest;

    @Transient
    String tenTrangThai;

    @Transient
    String tenLoaiVthh;

    @Transient
    private String tenCloaiVthh;

    @Transient
    private String tenLoaiGia;

    @Transient
    String tenDvi;

    @Transient
    private List<KhPagQdTcdtnnCtiet> thongTinGia;

    @Column(name = "MO_TA_HANG_HOA")
    private String moTaHangHoa;
}
