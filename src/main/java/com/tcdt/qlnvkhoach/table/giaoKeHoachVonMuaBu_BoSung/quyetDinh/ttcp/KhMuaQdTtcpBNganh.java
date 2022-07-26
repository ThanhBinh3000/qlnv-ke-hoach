package com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "KH_MUA_QD_TTCP_BNGANH")
@Data
public class KhMuaQdTtcpBNganh implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_MUA_QD_TTCP_BN_SEQ")
    @SequenceGenerator(sequenceName = "KH_MUA_QD_TTCP_BN_SEQ",allocationSize = 1,name = "KH_MUA_QD_TTCP_BN_SEQ")
    private Long id;

    String maBoNganh;

    Long tongTien;

    Long idMuaQdTtcp;

    Long ttMuaBu;

    Long ttMuaBsung;

    @Transient
    String tenBoNganh;

    @Transient
    private List<KhMuaQdTtcpBNganhCTiet> muaBuList;

    @Transient
    private List<KhMuaQdTtcpBNganhCTiet> muaBsungList;




}
