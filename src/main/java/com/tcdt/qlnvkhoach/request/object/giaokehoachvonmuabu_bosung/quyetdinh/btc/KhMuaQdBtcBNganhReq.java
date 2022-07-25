package com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.btc;

import lombok.Data;

import java.util.List;

@Data
public class KhMuaQdBtcBNganhReq {
    private Long id;

    String maBoNganh;

    Long tongTien;

    Long idMuaQdBtc;

    Long ttMuaBu;

    Long ttMuaBsung;

    List<KhMuaQdBtcBNganhCtietReq> muaBuList;

    List<KhMuaQdBtcBNganhCtietReq> muaBsungList;


}
