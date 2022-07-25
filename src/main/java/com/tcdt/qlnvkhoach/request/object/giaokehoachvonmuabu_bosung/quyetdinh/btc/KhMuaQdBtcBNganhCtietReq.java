package com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.btc;

import lombok.Data;

@Data
public class KhMuaQdBtcBNganhCtietReq {
    private Long id;

    String loaiVthh;

    String cloaiVthh;

    String dviTinh;

    Long soLuong;

    Long idBoNganh;
}
