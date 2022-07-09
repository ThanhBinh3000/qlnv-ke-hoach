package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.request.BaseRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchKhLtPhuongAnGiaReq extends BaseRequest {
	private String soQD;
	private String trichYeu;
	private String maDvi;
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

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyTuNgayCt;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyDenNgayCt;
	private String trichYeuCt;
	private String soCt;
}