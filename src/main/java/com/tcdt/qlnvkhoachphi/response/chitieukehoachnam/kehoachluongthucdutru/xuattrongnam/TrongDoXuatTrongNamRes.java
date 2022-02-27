package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.xuattrongnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrongDoXuatTrongNamRes {
	private ThocXuatTrongNamRes thoc;
	private GaoXuatTrongNamRes gao;
}
