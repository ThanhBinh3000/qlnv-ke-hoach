package com.tcdt.qlnvkhoach.response.thongtriduyetydutoan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.enums.NhapXuatHangTrangThaiEnum;
import com.tcdt.qlnvkhoach.util.DataUtils;
import com.tcdt.qlnvkhoach.util.LocalDateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TtDuyetYDuToanSearchResponse {
	private Long id;
	private String soDeNghi;
	private String tenBoNganh;
	private LocalDate ngayDeNghi;
	private Integer nam;
	private String trangThaiId;
	private String tenTrangThai;

	private BigDecimal tongTien;
	private BigDecimal kinhPhiDaCap;
	private BigDecimal ycCapThem;


	public TtDuyetYDuToanSearchResponse(Object[] rawData) {
		if (Objects.nonNull(rawData[0])) this.id = (Long) rawData[0];
		if (Objects.nonNull(rawData[1])) this.soDeNghi = (String) rawData[1];
		if (Objects.nonNull(rawData[2])) this.tenBoNganh = (String) rawData[2];
		if (Objects.nonNull(rawData[3])) this.ngayDeNghi = (LocalDate) rawData[3];
		if (Objects.nonNull(rawData[4])) this.nam = (Integer) rawData[4];

		if (Objects.nonNull(rawData[5])) {
			this.trangThaiId = (String) rawData[5];
			this.tenTrangThai = NhapXuatHangTrangThaiEnum.getTenById(this.trangThaiId);
		}
	}

	public Object[] toExcel(String[] rowsName, Integer stt) {
		Object[] objs = new Object[rowsName.length];
		objs[0] = stt;
		objs[1] = DataUtils.toStringValue(this.soDeNghi);
		objs[2] = DataUtils.toStringValue(this.tenBoNganh);
		objs[3] = DataUtils.toStringValue(LocalDateTimeUtils.localDateToString(this.ngayDeNghi));
		objs[4] = DataUtils.toStringValue(this.nam);
		objs[5] = DataUtils.toStringValue(this.tongTien);
		objs[6] = DataUtils.toStringValue(this.kinhPhiDaCap);
		objs[7] = DataUtils.toStringValue(this.ycCapThem);
		objs[8] = DataUtils.toStringValue(this.tenTrangThai);

		return objs;
	}
}
