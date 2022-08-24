package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHop;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHopCTiet;
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
public class KhPagQuyetDinhBtcReq {
  private Long id;
  private String maDonVi;
  private String trangThai;
  private Integer namKeHoach;
  private String soQd;
  private LocalDate ngayKy;
  private LocalDate ngayHieuLuc;
  private Long soToTrinh;
  private String loaiVthh;
  private String cloaiVthh;
  private String loaiGia;
  private String tieuChuanCl;
  private String trichYeu;
  private String ghiChu;
  private String capDvi;
  private KhPagTongHop soTtDeXuat;
  private List<Object> thongTinGia;
  private String pagType;
}
