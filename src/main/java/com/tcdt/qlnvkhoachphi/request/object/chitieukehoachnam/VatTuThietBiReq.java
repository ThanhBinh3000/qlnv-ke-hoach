package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class VatTuThietBiReq {
	private Long id;
	@Min(value = 0, message = "Stt phải lớn hơn 0")
	private Integer stt;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Đơn vị tính không được vượt quá 50 ký tự.")
	private String donViTinh;
	@NotNull(message = "Không được để trống")
	private Long vatTuId;

	@NotNull(message = "Không được để trống")
	private String maVatTu;

	@NotNull(message = "Không được để trống")
	private Long vatTuChaId;

	@NotNull(message = "Không được để trống")
	private String maVatTuCha;
	// Nhap trong nam
	@Min(value = 0, message = "Số lượng phải lớn hơn 0")
	private Double nhapTrongNam;
}
