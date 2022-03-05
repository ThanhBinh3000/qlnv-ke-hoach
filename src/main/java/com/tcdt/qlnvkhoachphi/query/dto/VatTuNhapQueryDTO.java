package com.tcdt.qlnvkhoachphi.query.dto;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class VatTuNhapQueryDTO {
	private Long id;
	private Integer nam;
	private Double soLuong;
	private Long vatTuId;

	public VatTuNhapQueryDTO(Integer nam, Double soLuong, Long vatTuId) {
		this.nam = nam;
		this.soLuong = soLuong;
		this.vatTuId = vatTuId;
	}
}
