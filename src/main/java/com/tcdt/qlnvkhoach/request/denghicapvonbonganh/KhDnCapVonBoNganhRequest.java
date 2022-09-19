package com.tcdt.qlnvkhoach.request.denghicapvonbonganh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhDnCapVonBoNganhRequest  {
	private Long id;
	private Integer nam;
	private String soDeNghi;
	private LocalDate ngayDeNghi;
	private String maBoNganh;
	private String ghiChu;
	private List<KhDnCapVonBoNganhCtRequest> chiTietList;
	private List<FileDinhKemReq> fileDinhKemReqs;
}
