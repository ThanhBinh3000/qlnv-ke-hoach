package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KeHoachMuoiDuTruReq {
	private Integer stt;
	private Long id;
	@NotNull(message = "Không được để trống")
	private Long donViId;
	private String tenDonVi;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Đơn vị tính không được vượt quá 50 ký tự.")
	private String donViTinh;
	@Size(max = 500, message = "Trích yếu không được vượt quá 500 ký tự.")
	private String trichYeu;

	// Nhap trong nam
	private Double nhapTrongNam;

	// Xuat trong nam
	private List<VatTuNhapReq> xuatTrongNam = new ArrayList<>();
}
