package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.nhaptrongnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrongDoNhapTrongNamRes {
	private ThocNhapTrongNamRes thoc;
	private GaoNhapTrongNamRes gao;
}
