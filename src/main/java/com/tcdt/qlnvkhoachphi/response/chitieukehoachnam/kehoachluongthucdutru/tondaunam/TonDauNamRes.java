package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.tondaunam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TonDauNamRes {
	private Double tongSoQuyThoc;
	private TrongDoTonDauNamRes trongDo;
}
