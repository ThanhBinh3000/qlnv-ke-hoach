package com.tcdt.qlnvkhoach.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KeHoachNhapVatTuThietBiReq {
	@NotNull
	@Min(value = 0, message = "Stt phải lớn hơn 0")
	private Integer stt;
	private Long id;
	private Long donViId;
	private String maDvi;

	private String tenDonVi;

	private List<VatTuThietBiReq> vatTuThietBi = new ArrayList<>();
}
