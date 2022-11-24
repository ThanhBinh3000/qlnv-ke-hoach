package com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.ttcp;

import lombok.Data;

import java.util.List;
@Data
public class KhMuaQdTtcpBNganhReq {
    private Long id;

    String maBoNganh;

    Long tongTien;

    Long idMuaQdTtcp;

    Long ttMuaBu;

    long soQdUbtvqh;

    Long ttMuaBsung;

    List<KhMuaQdTtcpBNganhCtietReq> muaBuList;

    List<KhMuaQdTtcpBNganhCtietReq> muaBsungList;

}
