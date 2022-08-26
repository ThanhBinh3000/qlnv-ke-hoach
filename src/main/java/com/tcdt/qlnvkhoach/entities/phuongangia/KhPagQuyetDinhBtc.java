package com.tcdt.qlnvkhoach.entities.phuongangia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.BaseEntity;
import com.tcdt.qlnvkhoach.enums.KhPagQuyetDinhBtcEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = KhPagQuyetDinhBtc.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//kế hoạch - phương án giá - lương thực/vật tư - quyết định giá của bộ tài chính
public class  KhPagQuyetDinhBtc extends BaseEntity implements Serializable {
  private static final long serialVersionUID = -8183308525284487273L;
  public static final String TABLE_NAME = "KH_PAG_QD_BTC";

  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_PAG_QD_BTC_SEQ")
  @SequenceGenerator(sequenceName = "KH_PAG_QD_BTC_SEQ", allocationSize = 1, name = "KH_PAG_QD_BTC_SEQ")
  @Id
  private Long id;
  private String maDvi;
  private String trangThai;
  private Integer namKeHoach;
  private String soQd;
  private LocalDate ngayKy;
  private LocalDate ngayHieuLuc;
  private String soToTrinh;
  private String loaiVthh;
  private String cloaiVthh;
  private String loaiGia;
  private String tieuChuanCl;
  private String trichYeu;
  private String ghiChu;
  private String capDvi;
  @Transient
  private String tenTrangThai;
  @Transient
  private String tenLoaiVthh;
  @Transient
  private String tenCloaiVthh;
  @Transient
  private String tenLoaiGia;

  public String getTenTrangThai() {
    return KhPagQuyetDinhBtcEnum.getLabelById(trangThai);
  }
}