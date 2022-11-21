package com.tcdt.qlnvkhoach.table.btcgiaotcdt;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KH_QD_BTC_TCDT")
@Data
public class KhQdBtcTcdt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_QD_BTC_TCDT_SEQ")
    @SequenceGenerator(sequenceName = "KH_QD_BTC_TCDT_SEQ",allocationSize = 1,name = "KH_QD_BTC_TCDT_SEQ")
    private Long id;
    Integer namQd;
    String soQd;
    @Temporal(TemporalType.DATE)
    Date ngayQd;
    String trichYeu;
    Date ngayTao;
    String nguoiTao;
    Date ngaySua;
    String nguoiSua;
    String trangThai;
    @Transient
    String tenTrangThai;
    String nguoiPduyet;
    Date ngayPduyet;
    String ghiChu;

    @Transient
    KhQdBtcTcdtCtiet keHoachNhapXuat;

    @Transient
    List<KhQdBtcTcdtCtiet> muaTangList;

    @Transient
    List<KhQdBtcTcdtCtiet> xuatGiamList;

    @Transient
    List<KhQdBtcTcdtCtiet> xuatBanList;

    @Transient
    List<KhQdBtcTcdtCtiet> luanPhienList;

    @Transient
    private List<FileDinhKemChung> fileDinhkems =new ArrayList<>();
}
