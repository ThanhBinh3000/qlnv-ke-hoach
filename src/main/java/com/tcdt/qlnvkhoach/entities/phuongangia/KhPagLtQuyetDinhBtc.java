package com.tcdt.qlnvkhoach.entities.phuongangia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tcdt.qlnvkhoach.entities.BaseEntity;
import com.tcdt.qlnvkhoach.enums.KhPagLtQuyetDinhBtcEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = KhPagLtQuyetDinhBtc.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//kế hoạch - phương án giá - lương thực - quyết định giá của bộ tài chính
public class KhPagLtQuyetDinhBtc extends BaseEntity implements Serializable {
  private static final long serialVersionUID = -8183308525284487273L;
  public static final String TABLE_NAME = "KH_PAG_LT_QD_BTC";

  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KH_PAG_LT_QD_BTC_SEQ")
  @SequenceGenerator(sequenceName = "KH_PAG_LT_QD_BTC_SEQ", allocationSize = 1, name = "KH_PAG_LT_QD_BTC_SEQ")
  @Id
  private Long id;
  private String maDvi;
  private String trangThai;
  private Integer namKeHoach;
  private String soQd;
  private LocalDate ngayKy;
  private LocalDate ngayHieuLuc;
  private String soTtDeXuat;
  private String loaiVthh;
  private String cloaiVthh;
  private String loaiGia;
  private String tieuChuanCl;
  private String trichYeu;
  private String ghiChu;
  private String capDvi;
  @Transient
  private String tenTrangThai;

  public String getTenTrangThai() {
    return KhPagLtQuyetDinhBtcEnum.getLabelById(trangThai);
  }
}