package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhLtPagDiaDiemDeHangReq {
	private Long id;

	private Long stt;

	private String maDvi;

	private String tenDvi;

	private BigDecimal soLuong;

	private Long phuongAnGiaId;

}
