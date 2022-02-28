package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.xuattrongnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XuatTrongNamRes {
	private Double tongSoQuyThoc;
	private TrongDoXuatTrongNamRes trongDo;
}
