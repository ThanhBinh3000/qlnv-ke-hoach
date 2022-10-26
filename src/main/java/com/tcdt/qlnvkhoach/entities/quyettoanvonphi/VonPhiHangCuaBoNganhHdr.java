package com.tcdt.qlnvkhoach.entities.quyettoanvonphi;

import com.tcdt.qlnvkhoach.entities.BaseEntity;
import com.tcdt.qlnvkhoach.enums.TrangThaiAllEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = VonPhiHangCuaBoNganhHdr.TABLE_NAME)
@Data
public class VonPhiHangCuaBoNganhHdr extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  public static final String TABLE_NAME = "QT_VON_PHI_BO_NGANH_HDR";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = VonPhiHangCuaBoNganhHdr.TABLE_NAME + "_SEQ")
  @SequenceGenerator(sequenceName = VonPhiHangCuaBoNganhHdr.TABLE_NAME
      + "_SEQ", allocationSize = 1, name = VonPhiHangCuaBoNganhHdr.TABLE_NAME + "_SEQ")
  private Long id;
  private int nam;
  private String maDvi;
  private String dvql;
  private String trangThai;
  private String trangThaiBc;
  private String lyDoTuChoi;

  @Transient
  private String tenDvi;
  @Transient
  private String tenLoaiVthh;
  @Transient
  private String tenTrangThai;

  public String getTenTrangThai() {
    return TrangThaiAllEnum.getLabelById(trangThai);
  }
}