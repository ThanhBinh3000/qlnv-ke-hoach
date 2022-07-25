package com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp;

import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganh;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KH_MUA_QD_TTCP")
@Data
public class KhMuaQdTtcp implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_MUA_QD_TTCP_SEQ")
    @SequenceGenerator(sequenceName = "KH_MUA_QD_TTCP_SEQ",allocationSize = 1,name = "KH_MUA_QD_TTCP_SEQ")
    private Long id;

    Integer namQd;

    String soQd;

    @Temporal(TemporalType.DATE)
    Date ngayQd;

    String soQdUbtvqh;

    String trichYeu;

    Date ngayTao;

    String nguoiTao;

    Date ngaySua;

    String nguoiSua;

    String trangThai;

    String nguoiPduyet;

    Date ngayPduyet;

    @Transient
    private List<KhMuaQdTtcpBNganh> listBoNganh;






}
