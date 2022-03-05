package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KeHoachNhapVatTuThietBiReq {
	private Integer stt;
	private Long id;
	@NotNull(message = "Không được để trống")
	private Long donViId;
	private String tenDonVi;

	private List<VatTuThietBiReq> vatTuThietBi = new ArrayList<>();
}
