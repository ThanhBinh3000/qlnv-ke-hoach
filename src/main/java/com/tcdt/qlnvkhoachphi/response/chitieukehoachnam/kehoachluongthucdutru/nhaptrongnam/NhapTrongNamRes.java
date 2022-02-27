package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.nhaptrongnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhapTrongNamRes {
	private BigDecimal tongSoQuyThoc;
	private TrongDoNhapTrongNamRes trongDo;
}
