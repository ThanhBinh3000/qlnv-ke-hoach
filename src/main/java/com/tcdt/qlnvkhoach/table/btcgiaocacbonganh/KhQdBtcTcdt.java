package com.tcdt.qlnvkhoach.table.btcgiaocacbonganh;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "KH_QD_BTC_TCDT")
@Data
public class KhQdBtcTcdt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_QD_BTC_TCDT_SEQ")
    @SequenceGenerator(sequenceName = "KH_QD_BTC_TCDT_SEQ",allocationSize = 1,name = "KH_QD_BTC_TCDT_SEQ")
    private Long id;
    Integer namKhoach;
    String soQd;
    Date ngayQd;
    String trichYeu;
    Date ngayTao;
    String nguoiTao;
    Date ngaySua;
    String nguoiSua;
}
