package com.tcdt.qlnvkhoach.table.qtVonPhiHang;


import com.tcdt.qlnvkhoach.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "KH_VP_BO_NGANH_CT")
@Data
public class KhVonPhiBoNganhCt extends BaseEntity implements Serializable  {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_VP_BO_NGANH_CT_SEQ")
    @SequenceGenerator(sequenceName = "KH_VP_BO_NGANH_CT_SEQ",allocationSize = 1,name = "KH_VP_BO_NGANH_CT_SEQ")
    private Long id;
    Long khVpBnId;
    String boNganh;
    @Transient
    String tenBoNganh;
    Long soDnQt;
    Long soTdQt;
    Long soHuyDt;
    Long soTuPhaiNop;
    Long soDuNamSau;
    Long soTuNamSau;
    String type;
}
