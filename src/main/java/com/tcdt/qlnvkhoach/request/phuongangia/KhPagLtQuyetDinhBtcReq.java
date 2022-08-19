package com.tcdt.qlnvkhoach.request.phuongangia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhPagLtQuyetDinhBtcReq {
  private Long id;
  private String maDonVi;
  private String trangThai;
  private Integer namKeHoach;
  private String soQd;
  private LocalDate ngayKy;
  private LocalDate ngayHieuLuc;
  private String soTtDeXuat;
  private String loaiHangHoa;
  private String chungLoaiHangHoa;
  private String loaiGia;
  private String tieuChuanCl;
  private String trichYeu;
  private String ghiChu;
  private String capDvi;
}
