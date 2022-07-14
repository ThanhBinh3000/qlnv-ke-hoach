package com.tcdt.qlnvkhoach.table.ttcp;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "KH_QD_TTCP_BO_NGANH")
@Data
public class KhQdTtcpBoNganh implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_QD_TTCP_BO_NGANH_SEQ")
    @SequenceGenerator(sequenceName = "KH_QD_TTCP_BO_NGANH_SEQ",allocationSize = 1,name = "KH_QD_TTCP_BO_NGANH_SEQ")
    private Long id;
    String maBoNganh;
    Long soLuong;
    Integer namKhoach;
    Long idQdTtcp;
    Long tongTien;
    Long ltThocXuat;
    Long ltGaoXuat;
    Long ltThocMua;
    Long ltGaoMua;
    Long ttMuaTang;
    Long ttXuatGiam;
    Long ttXuatBan;

    @Transient
    List<KhQdTtcpBoNganhCTiet> listCtiet;
}
