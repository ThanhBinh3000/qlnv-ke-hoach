package com.tcdt.qlnvkhoach.table.btcgiaocacbonganh;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "KH_QD_BTC_BO_NGANH_CTIET")
@Data
public class KhQdBtcBoNganhCtiet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_QD_BTC_BO_NGANH_CTIET_SEQ")
    @SequenceGenerator(sequenceName = "KH_QD_BTC_BO_NGANH_CTIET_SEQ",allocationSize = 1,name = "KH_QD_BTC_BO_NGANH_CTIET_SEQ")
    private Long id;
    String loaiChi;
    @Transient
    String tenLoaiChi;
    Long sluongDtoan;
    String loaiVthh;
    String cloaiVthh;
    Long idQdBtcNganh;
    String type;

}
