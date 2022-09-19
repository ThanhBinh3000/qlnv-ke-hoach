package com.tcdt.qlnvkhoach.response.thongtriduyetydutoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.response.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TtDuyetYDuToanResponse extends CommonResponse {
	private Long id;
	private Integer nam;
	private String soDeNghi;
	private LocalDate ngayDeNghi;
	private String maBoNganh;
	private String tenBoNganh;
	private String ghiChu;
	private String trangThai;
	private String tenTrangThai;
	private String maDvi;
	private String tenDvi;
	private String lyDoTuChoi;
	private List<TtDuyetYDuToanCtResponse> chiTietList;
	private List<FileDinhKemChung> fileDinhKems;
}
