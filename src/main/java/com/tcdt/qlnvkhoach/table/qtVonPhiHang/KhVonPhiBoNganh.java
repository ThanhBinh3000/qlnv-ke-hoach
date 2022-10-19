package com.tcdt.qlnvkhoach.table.qtVonPhiHang;


import com.tcdt.qlnvkhoach.entities.BaseEntity;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganh;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KH_VP_BO_NGANH")
@Data
public class KhVonPhiBoNganh extends BaseEntity implements Serializable  {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_VP_BO_NGANH_SEQ")
    @SequenceGenerator(sequenceName = "KH_VP_BO_NGANH_SEQ",allocationSize = 1,name = "KH_VP_BO_NGANH_SEQ")
    private Long id;
    Integer namQt;
    @Temporal(TemporalType.DATE)
    Date ngayNhap;
    String trangThai;
    String trangThaiBtc;
    @Transient
    String tenTrangThai;
    @Transient
    String tenTrangThaiBtc;
    Integer nguoiPduyet;
    Date ngayPduyet;
    String lyDo;
    @Transient
    private List<KhVonPhiBoNganhCt> listNganSachChiDTQG;
    @Transient
    private List<KhVonPhiBoNganhCt> listNganSachChiNghiepVuDTQG;
}
