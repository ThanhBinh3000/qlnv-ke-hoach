package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhQdKhlcntHdrReq {
  private String maDvi;
  private String trangThai;
  private Integer namKeHoach;
  private String loaiVthh;
  private String cloaiVthh;
}
