package com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam;

import lombok.Data;

import java.util.List;

@Data


public class KhQdTtcpBoNganhReq {

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

    private List<KhQdTtcpBoNganhCtietReq> listCtiet;

}

