package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.tonkhocuoinam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TonKhoCuoiNamRes {
	private BigDecimal tongSoQuyThoc;
	private TrongDoTonKhoCuoiNamRes trongDo;
}
