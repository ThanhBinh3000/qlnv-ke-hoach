package com.tcdt.qlnvkhoach.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class VatTuNhapReq {
	private Long id;
	private Long vatTuId;
	@Min(value = 0, message = "Số lượng phải lớn hơn 0")
	private Double soLuong;

	@NotNull
	private Integer nam;
}
