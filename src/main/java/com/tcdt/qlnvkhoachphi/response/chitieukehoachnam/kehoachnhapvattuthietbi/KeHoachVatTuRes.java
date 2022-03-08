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
	private Long donViId;
	private String maDonVi;
	private String tenDonVi;

	private List<NhomVatTuThietBiRes> nhomVatTuThietBi = new ArrayList<>();
}
