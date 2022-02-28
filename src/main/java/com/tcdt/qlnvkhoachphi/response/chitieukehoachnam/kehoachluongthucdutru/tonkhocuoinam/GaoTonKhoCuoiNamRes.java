package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.tonkhocuoinam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GaoTonKhoCuoiNamRes {
	private Long vatTuId;
	private Double gao;
}
