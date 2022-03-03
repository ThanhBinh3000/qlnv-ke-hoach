package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VatTuNhapReq {
	private Long id;
	private Long vatTuId;
	private Double soLuong;
	private Integer nam;
}
