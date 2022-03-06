package com.tcdt.qlnvkhoachphi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchChiTieuKeHoachNamReq {
	private String soQD;
	private String trichYeu;
	private Long donViId;
	private LocalDate ngayKyTuNgay;
	private LocalDate ngayKyDenNgay;
	private String tenDonVi;
}