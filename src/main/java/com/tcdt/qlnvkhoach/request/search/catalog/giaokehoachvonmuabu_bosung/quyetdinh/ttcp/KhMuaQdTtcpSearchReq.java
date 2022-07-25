package com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvonmuabu_bosung.quyetdinh.ttcp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.util.Contains;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class KhMuaQdTtcpSearchReq extends BaseRequest {
    Integer namQd;

    String soQd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQdTu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQdDen;

    String trichYeu;

    String trangThai;

    List<Long> idList;
}
