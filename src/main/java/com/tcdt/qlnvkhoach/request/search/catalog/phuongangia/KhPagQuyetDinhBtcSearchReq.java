package com.tcdt.qlnvkhoach.request.search.catalog.phuongangia;


import com.tcdt.qlnvkhoach.jwt.CustomUserDetails;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class KhPagQuyetDinhBtcSearchReq extends BaseRequest {
  String namKeHoach;
  String soQd;
  LocalDate ngayKyTu;
  LocalDate ngayKyDen;
  String trichYeu;
  String loaiVthh;
  String pagType = "";
  String pagTypeLT;
  String pagTypeVT;
  String dvql;

  public String getPagType() {
    if (pagType.equals("LT")) {
      pagTypeLT = "1";
    }
    if (pagType.equals("VT")) {
      pagTypeVT = "1";
    }
    return pagType;
  }
}
