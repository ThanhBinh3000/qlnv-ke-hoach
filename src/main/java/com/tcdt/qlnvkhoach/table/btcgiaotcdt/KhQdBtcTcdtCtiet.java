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
    Long soLuong;
    Long sluongDtoan;
    String type;
    Long idQdBtcTcdt;


}
