package com.tcdt.qlnvkhoachphi.request.search.catalog.chitieukehoachnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchChiTieuKeHoachNamReq {
	private String soQD;
	private String trichYeu;
	private Long donViId;
	private Long dvql;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyTuNgay;
	private String trangThai;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyDenNgay;
	private String tenDonVi;
	private String loaiQuyetDinh;
	private Integer namKeHoach;
}