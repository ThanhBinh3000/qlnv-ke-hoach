package com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam;

import com.tcdt.qlnvkhoach.request.BaseRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchChiTieuKeHoachNamReq extends BaseRequest {
	private String soQD;
	private String trichYeu;
	private Long donViId;
	private String dvql;
	private String capDvi;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyTuNgay;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyDenNgay;
	private String tenDonVi;
	private String loaiQuyetDinh;
	private Integer namKeHoach;
}