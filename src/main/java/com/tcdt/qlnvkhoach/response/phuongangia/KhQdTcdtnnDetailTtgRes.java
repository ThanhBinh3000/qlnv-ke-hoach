package com.tcdt.qlnvkhoach.response.phuongangia;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KhQdTcdtnnDetailTtgRes {
    Long namKh;
    String maDvi;
    String loaiVthh;
    String cloaiVthh;
    BigDecimal giaDn;
    BigDecimal giaDnVat;
    BigDecimal giaQd;
    BigDecimal giaQdVat;
    BigDecimal pagId;
}
