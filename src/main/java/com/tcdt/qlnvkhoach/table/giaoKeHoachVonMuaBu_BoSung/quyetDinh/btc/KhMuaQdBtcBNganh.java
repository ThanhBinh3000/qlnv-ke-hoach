package com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "KH_MUA_QD_BTC_BNGANH")
@Data
public class KhMuaQdBtcBNganh implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_MUA_QD_BTC_BNGANH_SEQ")
    @SequenceGenerator(sequenceName = "KH_MUA_QD_BTC_BNGANH_SEQ",allocationSize = 1,name = "KH_MUA_QD_BTC_BNGANH_SEQ")
    private Long id;

    String maBoNganh;

    Long tongTien;

    Long idMuaQdBtc;

    Long ttMuaBu;

    Long ttMuaBsung;


    @Transient
    String tenBoNganh;
    @Transient
    List<KhMuaQdBtcBNganhCTiet> muaBuList;
    @Transient
    List<KhMuaQdBtcBNganhCTiet> muaBsungList;
}
