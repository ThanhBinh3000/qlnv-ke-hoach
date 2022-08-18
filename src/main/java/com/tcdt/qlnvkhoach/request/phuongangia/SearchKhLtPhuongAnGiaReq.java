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
	private Long namKeHoach;
	private String loaiHangHoa;
	private String soDeXuat;
	private String trichYeu;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyTuNgay;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayKyDenNgay;
	private String capDvi;
}