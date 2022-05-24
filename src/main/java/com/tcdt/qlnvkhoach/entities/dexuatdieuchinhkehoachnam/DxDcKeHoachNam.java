package com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = DxDcKeHoachNam.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DxDcKeHoachNam implements Serializable {

    private static final long serialVersionUID = 2408090111932694415L;

    public static final String TABLE_NAME = "DX_DC_KE_HOACH_NAM";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DX_DC_KE_HOACH_NAM_SEQ")
    @SequenceGenerator(sequenceName = "DX_DC_KE_HOACH_NAM_SEQ", allocationSize = 1, name = "DX_DC_KE_HOACH_NAM_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "SO_VAN_BAN")
    private String soVanBan;

    @Column(name = "NGAY_KY")
    private LocalDate ngayKy;

    @Column(name = "NGAY_HIEU_LUC")
    private LocalDate ngayHieuLuc;

    @Column(name = "NAM_KE_HOACH")
    private Integer namKeHoach;

    @Column(name = "TRICH_YEU")
    private String trichYeu;

    @Column(name = "TRANG_THAI")
    private String trangThai;

    @Column(name = "TRANG_THAI_TONG_CUC")
    private String trangThaiTongCuc;

    @Column(name = "NGAY_TAO")
    private LocalDate ngayTao;

    @Column(name = "NGUOI_TAO_ID")
    private Long nguoiTaoId;

    @Column(name = "NGAY_SUA")
    private LocalDate ngaySua;

    @Column(name = "NGUOI_SUA_ID")
    private Long nguoiSuaId;

    @Column(name = "NGAY_GUI_DUYET")
    private LocalDate ngayGuiDuyet;

    @Column(name = "NGUOI_GUI_DUYET_ID")
    private Long nguoiGuiDuyetId;

    @Column(name = "NGAY_PHE_DUYET")
    private LocalDate ngayPheDuyet;

    @Column(name = "NGUOI_PHE_DUYET_ID")
    private Long nguoiPheDuyetId;

    @Column(name = "LY_DO_TU_CHOI")
    private String lyDoTuChoi;

    @Column(name = "NGUYEN_NHAN")
    private String nguyenNhan;

    @Column(name = "NOI_DUNG")
    private String noiDung;

    @Column(name = "MA_DVI")
    private String maDvi;

    @Column(name = "CAP_DVI")
    private String capDvi;

    @Column(name = "KE_HOACH_NAM_ID")
    private Long keHoachNamId; // CHI_TIEU_KE_HOACH_NAM

    @Transient
    private List<DxDcLtVt> dxDcLtVtList = new ArrayList<>();

    @Transient
    private List<FileDinhKemChung> fileDinhKems = new ArrayList<>();

    @Transient
    private ChiTieuKeHoachNam keHoachNam;

    @Transient
    private QlnvDmDonvi donVi;
}
