package com.tcdt.qlnvkhoach.response.denghicapvonbonganh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcdt.qlnvkhoach.enums.TrangThaiAllEnum;
import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.util.DataUtils;
import com.tcdt.qlnvkhoach.util.LocalDateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhDnCapVonBoNganhSearchResponse {
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

	private String parentName;

	private Boolean isSum;

	//Loại để phân biệt là bộ TC (TCDT) hay bộ ngành khác
	private String loaiBn;

	//Loại để phân biệt loai hàng là LT ,VT hay loại khác
	private String loaiHang;

	private String maBn;

	private List<KhDnCapVonBoNganhSearchResponse> listDetail;

	public KhDnCapVonBoNganhSearchResponse(Object[] rawData) {
		if (Objects.nonNull(rawData[0])) this.id = (Long) rawData[0];
		if (Objects.nonNull(rawData[1])) this.soDeNghi = (String) rawData[1];
		if (Objects.nonNull(rawData[2])) this.tenBoNganh = (String) rawData[2];
		if (Objects.nonNull(rawData[3])) this.ngayDeNghi = (LocalDate) rawData[3];
		if (Objects.nonNull(rawData[4])) this.nam = (Integer) rawData[4];
		//bộ ngành khác = BN
		this.loaiBn = "BN";
		this.isSum = Boolean.FALSE;
		if (Objects.nonNull(rawData[5])) {
			this.trangThaiId = (String) rawData[5];
			this.tenTrangThai = TrangThaiAllEnum.getLabelById(this.trangThaiId);
		}
		this.loaiHang = "OTHER";
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
