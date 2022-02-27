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
public class XuatTrongNamRes {
	private BigDecimal tongSoQuyThoc;
	private TrongDoXuatTrongNamRes trongDo;
}
