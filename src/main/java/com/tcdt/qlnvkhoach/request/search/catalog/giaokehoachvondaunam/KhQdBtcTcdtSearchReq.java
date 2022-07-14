package com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.util.Contains;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class KhQdBtcTcdtSearchReq extends BaseRequest {
    Integer namQd;

    String soQd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQdTu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQdDen;

    String trichYeu;

    List<Long> idList;
}
