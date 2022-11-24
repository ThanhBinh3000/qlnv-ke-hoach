package com.tcdt.qlnvkhoach.response.giaokehoachvondaunam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor

public class KhQdTtcpRes {
    private Long id;

    private Integer nam;

    private String soQd;

    private Date ngayQd;

    private String trichYeu;

//    private List<KhQdTtcpBoNganhRes> listBoNganh;

}

