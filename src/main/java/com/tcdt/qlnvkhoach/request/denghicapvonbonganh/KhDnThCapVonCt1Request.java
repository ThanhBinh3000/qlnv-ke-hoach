package com.tcdt.qlnvkhoach.request.denghicapvonbonganh;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class KhDnThCapVonCt1Request {
    private Long khDnCapVonId;
    private BigDecimal tcCapThem;
}
