package com.tcdt.qlnvkhoach.response.giaokehoachvondaunam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor

public class KhQdTtcpBoNganhRes {

    private Long id;

    private String maBoNganh;

    private Long soLuong;

    private Integer namKhoach;

    private Long idQdTtcp;

    private Long tongTien;

    private Long ltThocXuat;

    private Long ltGaoXuat;

    private Long ltThocMua;

    private Long ltGaoMua;

    private Long ttMuaTang;

    private Long ttXuatGiam;

    private Long ttXuatBan;

//    private List<KhQdTtcpBoNganhCtietRes> listCtiet;

}

