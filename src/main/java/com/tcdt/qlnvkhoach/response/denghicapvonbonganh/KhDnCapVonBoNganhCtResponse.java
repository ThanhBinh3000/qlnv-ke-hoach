package com.tcdt.qlnvkhoach.response.denghicapvonbonganh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.response.CommonResponse;
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
public class KhDnCapVonBoNganhCtResponse extends CommonResponse {
	private Long id;
	private String maVatTuCha;
	private String maVatTu;
	private BigDecimal soLuong;
	private String donViTinh;
	private BigDecimal thanhTien;
	private BigDecimal kinhPhiDaCap;
	private BigDecimal ycCapThem;
	private Long deNghiCapVonBoNganhId;
}
