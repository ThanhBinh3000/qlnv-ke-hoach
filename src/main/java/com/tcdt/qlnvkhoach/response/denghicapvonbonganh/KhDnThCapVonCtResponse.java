package com.tcdt.qlnvkhoach.response.denghicapvonbonganh;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class KhDnThCapVonCtResponse extends KhDnCapVonBoNganhResponse{
    private BigDecimal tongTien;
    private BigDecimal kinhPhiDaCap;
    private BigDecimal ycCapThem;
}
