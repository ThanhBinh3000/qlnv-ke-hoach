package com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "KH_MUA_QD_BTC_BNGANH_CTIET")
@Data
public class KhMuaQdBtcBNganhCTiet {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_MUA_QD_BTC_BN_CT_SEQ")
    @SequenceGenerator(sequenceName = "KH_MUA_QD_BTC_BN_CT_SEQ",allocationSize = 1,name = "KH_MUA_QD_BTC_BN_CT_SEQ")
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
