package com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabubosung;

import lombok.Data;

import java.util.List;

@Data
public class KhMuaQdUbtvqhBnganhReq {
    private Long id;

    private String maBoNganh;

    private Long tongTien;

    private Long idMuaQdUbtvqh;

    private Long ttMuaBu;

    private Long ttMuaBsung;

    List<KhMuaQdUbtvqhBnganhCtietReq> muaBuList;

    List<KhMuaQdUbtvqhBnganhCtietReq> muaBsungList;


}
