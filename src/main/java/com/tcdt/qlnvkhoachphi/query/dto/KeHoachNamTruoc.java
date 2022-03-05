package com.tcdt.qlnvkhoachphi.query.dto;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KeHoachNamTruoc {
	private Long vatTuId;
	private String maVatTu;
	private List<VatTuNhapRes> cacNamTruoc = new ArrayList<>();
}
