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
public class KhLtPagKetQuaReq {
	private Long id;

	private Long stt;

	private String tenDonVi;

	private BigDecimal donGia;

	private BigDecimal donGiaVat;

	private String ghiChu;

	private String chungLoaiHh;

	private String thoiHanBaoGia;

	private List<FileDinhKemReq> fileDinhKems = new ArrayList<>();

	private String type;

	private Long phuongAnGiaId;

}
