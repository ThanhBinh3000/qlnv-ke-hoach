package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VatTuThietBiReq {
	private String donViTinh;
	private Long vatTuId;
	private Long vatTuChaId;

	// Nhap trong nam
	private Double nhapTrongNam;

	// Chi tieu cac nam truoc
//	private List<VatTuNhapReq> nhapCacNamTruoc = new ArrayList<>();
}
