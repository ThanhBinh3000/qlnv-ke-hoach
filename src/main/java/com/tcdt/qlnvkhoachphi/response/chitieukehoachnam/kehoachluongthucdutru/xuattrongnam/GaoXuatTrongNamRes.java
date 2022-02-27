package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.xuattrongnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GaoXuatTrongNamRes {
	private BigDecimal tong;
	private BigDecimal nhap1NamTruoc;
	private BigDecimal nhap2NamTruoc;
}
