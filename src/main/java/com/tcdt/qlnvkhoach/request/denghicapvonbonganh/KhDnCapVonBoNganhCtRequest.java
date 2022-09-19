package com.tcdt.qlnvkhoach.request.denghicapvonbonganh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhDnCapVonBoNganhCtRequest {
	private Long id;
	private String maVatTuCha;
	private String maVatTu;
	private BigDecimal soLuong;
	private String donViTinh;
	private BigDecimal thanhTien;
	private BigDecimal kinhPhiDaCap;
	private BigDecimal ycCapThem;
	private Long deNghiCapVonBoNganhId;
	private String dvCungCapHang;
	private String soTaiKhoan;
	private String nganHang;
	private String tenHangHoa;

}
