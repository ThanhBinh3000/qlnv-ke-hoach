package com.tcdt.qlnvkhoach.request.denghicapvonbonganh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.request.BaseRequest;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhDnCapVonBoNganhSearchRequest extends BaseRequest {
	private String soDeNghi;
	private String maBoNganh;
	private String trangThaiTh;
	private String trangThai;
	private Integer nam;
	//Type đánh dấu là search màn list hay màn tổng hợp
	private String type;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayDeNghiTuNgay;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ngayDeNghiDenNgay;
	//Dùng cho tổng hợp, loại tổng hợp (All, BTC,BN khác)
	private String loaiTh;
}
