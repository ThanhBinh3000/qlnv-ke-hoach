package com.tcdt.qlnvkhoach.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

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

	public String groupByNamAndVatTuId() {
		return String.format("%s_%s", nam.toString(), vatTuId.toString());
	}
}
