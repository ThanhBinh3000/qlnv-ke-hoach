package com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachdaunam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.BaseRequest;
import com.tcdt.qlnvkhoach.util.Contains;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;



@Getter
@Setter
public class KhQdBtBoNganhSearchReq extends BaseRequest {
    Integer namKhoach;

    String soQd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQdTu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQdDen;

    String trichYeu;
}
