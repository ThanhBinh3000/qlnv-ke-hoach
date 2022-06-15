package com.tcdt.qlnvkhoach.request.search.catalog.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.request.BaseRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDxDcKeHoachNamReq extends BaseRequest {
	private String soVanBan;
	private String soQuyetDinh;
	private String trichYeuQd;
	private String trichYeuDx;
	private Integer namKeHoach;
	private String maDonVi;
	private String loaiHangHoa;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyTuNgayQd;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyDenNgayQd;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyTuNgayDx;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyDenNgayDx;
}