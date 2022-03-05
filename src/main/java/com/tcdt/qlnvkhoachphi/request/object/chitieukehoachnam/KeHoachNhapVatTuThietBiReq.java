package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KeHoachNhapVatTuThietBiReq {
	private Integer stt;
	private Long id;
	private Long donViId;
	private String tenDonVi;

	private List<VatTuThietBiReq> vatTuThietBi = new ArrayList<>();
}
