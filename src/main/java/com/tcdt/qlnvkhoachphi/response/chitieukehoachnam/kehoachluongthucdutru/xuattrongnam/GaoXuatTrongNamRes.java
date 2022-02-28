package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru.xuattrongnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GaoXuatTrongNamRes {
	private Double tong;
	private Double nhap1NamTruoc;
	private Double nhap2NamTruoc;
	private Long vatTuId;
}
