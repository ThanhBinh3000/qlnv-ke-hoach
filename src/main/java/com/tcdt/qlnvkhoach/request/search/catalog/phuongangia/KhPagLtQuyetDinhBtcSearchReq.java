package com.tcdt.qlnvkhoach.request.search.catalog.phuongangia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.util.Contains;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class KhPagLtQuyetDinhBtcSearchReq extends BaseRequest {
  String namKeHoach;
  String soQd;
  LocalDate ngayKyTu;
  LocalDate ngayKyDen;
  String trichYeu;
}
