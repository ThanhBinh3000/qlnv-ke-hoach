package com.tcdt.qlnvkhoach.response.giaokehoachvondaunam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class KhQdBtcBoNganhRes {
    private Long id;

    private Integer namQd;

    private  String soQd;

    private Date ngayQd;

    private String trichYeu;


}
