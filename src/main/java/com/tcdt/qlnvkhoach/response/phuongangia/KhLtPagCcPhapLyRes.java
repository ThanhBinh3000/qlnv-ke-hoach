package com.tcdt.qlnvkhoach.response.phuongangia;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhLtPagCcPhapLyRes {
	private Long id;

	private Long stt;

	private String moTa;

	private FileDinhKemChung fileDinhKems;

	private Long phuongAnGiaId;
}
