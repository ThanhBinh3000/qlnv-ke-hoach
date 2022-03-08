package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
public class NhomVatTuThietBiRes {
	private String maVatTuCha;
	private String tenVatTuCha;
	private Long vatTuChaId;
	private List<VatTuThietBiRes> vatTuThietBi = new ArrayList();

	public List<VatTuThietBiRes> getVatTuThietBi() {
		this.vatTuThietBi.sort(Comparator.comparing(VatTuThietBiRes::getStt));
		return this.vatTuThietBi;
	}
}
