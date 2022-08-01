package com.tcdt.qlnvkhoach.table.btcgiaotcdt;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "KH_QD_BTC_TCDT_CTIET")
@Data
public class KhQdBtcTcdtCtiet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_QD_BTC_TCDT_CTIET_SEQ")
    @SequenceGenerator(sequenceName = "KH_QD_BTC_TCDT_CTIET_SEQ",allocationSize = 1,name = "KH_QD_BTC_TCDT_CTIET_SEQ")
    private Long id;

    Long idDanhMuc;

    String loaiVthh;

    @Transient
    String tenVthh;

    String cloaiVthh;

    @Transient
    String tenCloaiVthh;

    Long soLuong;

    Long donGia;

    Long tongTien;

    String type;

    Long idQdBtcTcdt;

    Long soLuongMuaThoc ;

    Long donGiaMuaThoc;

    Long soLuongMuaGaoLpdh;

    Long donGiaMuaGaoLqdh;

    Long soLuongMuaGaoXcht;

    Long donGiaMuaGaoXcht;

    Long soLuongBanThoc;

    Long donGiaBanThoc;

    Long soLuongBanGao;

    Long donGiaBanGao;

    Long soLuongGaoCtro;

    Long donGiaGaoCtro;

    Long tongTienVonNsnn;

    Long tongTienVonTx;

}
