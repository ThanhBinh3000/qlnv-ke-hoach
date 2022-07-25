package com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.ttcp;

import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcpBNganhCTiet;
import lombok.Data;

import java.util.List;
@Data
public class KhMuaQdTtcpBNganhReq {
    private Long id;

    String maBoNganh;

    Long tongTien;

    Long idMuaQdTtcp;

    Long ttMuaBu;

    Long ttMuaBsung;

    List<KhMuaQdTtcpBNganhCtietReq> muaBuList;

    List<KhMuaQdTtcpBNganhCtietReq> muaBSungList;

}
