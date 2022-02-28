package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.tonkhocuoinam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TonKhoCuoiNamRes {
	private Double tongSoQuyThoc;
	private TrongDoTonKhoCuoiNamRes trongDo;
}
