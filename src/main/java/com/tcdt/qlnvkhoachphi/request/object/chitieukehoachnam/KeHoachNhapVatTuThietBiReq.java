package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KeHoachNhapVatTuThietBiReq {
	private Long id;
	private Long cucId;
	private String cucDTNNKhuVuc;

	List<VatTuThietBiReq> vatTuThietBi = new ArrayList<>();
}
