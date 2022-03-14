package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
public class KeHoachVatTuRes {
	private Long id;
	private Integer stt;
	private Long donViId;
	private String maDonVi;
	private String tenDonVi;

	private List<VatTuThietBiRes> vatTuThietBi = new ArrayList();

	public List<VatTuThietBiRes> getVatTuThietBi() {

		this.vatTuThietBi.sort(Comparator.comparing(VatTuThietBiRes::getStt, Comparator.nullsLast(Comparator.naturalOrder())));
		return this.vatTuThietBi;
	}
}
