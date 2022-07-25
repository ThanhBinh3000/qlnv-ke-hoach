package com.tcdt.qlnvkhoach.table.ttcp;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "KH_QD_TTCP_BO_NGANH_CTIET")
@Data
public class KhQdTtcpBoNganhCTiet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_QD_TTCP_BO_NGANH_CTIET_SEQ")
    @SequenceGenerator(sequenceName = "KH_QD_TTCP_BO_NGANH_CTIET_SEQ",allocationSize = 1,name = "KH_QD_TTCP_BO_NGANH_CTIET_SEQ")
    private Long id;
    String loaiVthh;
    String cloaiVthh;
    String dviTinh;
    Long soLuong;
    private Long idBoNganh;
    String type;

    @Transient
    String tenVthh;

    @Transient
    String tenCloaiVthh;
}
