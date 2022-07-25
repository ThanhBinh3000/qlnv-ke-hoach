package com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "KH_MUA_QD_TTCP_BNGANH_CTIET")
@Data
public class KhMuaQdTtcpBNganhCTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_MUA_QD_TTCP_BN_CT_SEQ")
    @SequenceGenerator(sequenceName = "KH_MUA_QD_TTCP_BN_CT_SEQ",allocationSize = 1,name = "KH_MUA_QD_TTCP_BN_CT_SEQ")
    private Long id;

    String loaiVthh;

    String cloaiVthh;

    String dviTinh;

    Long soLuong;

    Long idBoNganh;

    String type;

    @Transient
    String tenVthh;

    @Transient
    String tenCloaiVthh;
}
