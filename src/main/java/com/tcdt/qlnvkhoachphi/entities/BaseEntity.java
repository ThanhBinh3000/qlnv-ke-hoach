package com.tcdt.qlnvkhoachphi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
	private LocalDate ngayTao;
	private Long nguoiTaoId;
	private LocalDate ngaySua;
	private Long nguoiSuaId;
}
