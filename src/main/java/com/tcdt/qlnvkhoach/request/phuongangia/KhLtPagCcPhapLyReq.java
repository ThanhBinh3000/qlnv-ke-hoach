package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhLtPagCcPhapLyReq {
	private Long id;

	private Long stt;

	private String moTa;

	private List<FileDinhKemReq> fileDinhKems = new ArrayList<>();

	private Long phuongAnGiaId;
}
