package com.tcdt.qlnvkhoach.request.denghicapphibonganh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhDnCapPhiBoNganhCt2Request {
	private Long id;
	private String loaiChiPhi;
	private Long namPhatSinh;
	private Long tongChiPhi;
	private Long kinhPhiDaCap;
	private Long yeuCauCapThem;
	private Long capPhiBoNghanhCt1Id;
	private String maVatTuCha;
	private String maVatTu;
}
