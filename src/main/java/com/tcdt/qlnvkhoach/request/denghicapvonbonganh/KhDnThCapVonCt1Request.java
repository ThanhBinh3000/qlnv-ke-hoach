package com.tcdt.qlnvkhoach.request.denghicapvonbonganh;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class KhDnThCapVonCt1Request {
    private Long khDnCapVonId;
    private BigDecimal tcCapThem;
    private String tenBoNganh;
    private String loaiBn;
    private String maBn;
    private String loaiHang;
    private BigDecimal tongTien;
    private BigDecimal kinhPhiDaCap;
    private BigDecimal ycCapThem;
    private Integer nam;
    private String soDeNghi;
    private String ngayDeNghi;
}
