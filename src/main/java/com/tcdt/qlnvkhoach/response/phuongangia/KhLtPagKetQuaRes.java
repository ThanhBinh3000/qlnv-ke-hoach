package com.tcdt.qlnvkhoach.response.phuongangia;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhLtPagKetQuaRes {
	private Long id;

	private Long stt;

	private String tenDonVi;

	private BigDecimal donGia;

	private FileDinhKemChung fileDinhKems;

	private String type;

	private Long phuongAnGiaId;
}
