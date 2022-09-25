package com.tcdt.qlnvkhoach.response.denghicapphibonganh;

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
public class KhDnCapPhiBoNganhCt2Response {
	private Long id;
	private String loaiChiPhi;
	private Long namPhatSinh;
	private BigDecimal tongTien;
	private BigDecimal kinhPhiDaCap;
	private BigDecimal yeuCauCapThem;
	private Long capPhiBoNghanhCt1Id;

}
