package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KeHoachVatTuRes {
	private Long id;
	private Integer stt;
	private Long cucId;
	private String cucDTNNKhuVuc;

	private List<VatTuThietBiRes> vatTuThietBi = new ArrayList<>();
}
