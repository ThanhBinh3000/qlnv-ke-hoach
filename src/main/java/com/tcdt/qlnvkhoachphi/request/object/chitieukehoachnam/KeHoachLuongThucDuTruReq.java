package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class  KeHoachLuongThucDuTruReq {
	private Integer stt;
	private Long khGaoId;
	private Long khThocId;
	@NotNull(message = "Không được để trống")
	private Long donViId;
	@Size(max = 500, message = "Trích yếu không được vượt quá 500 ký tự.")
	private String trichYeu;
	private String tenDonVi;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Đơn vị tính không được vượt quá 50 ký tự.")
	private String donViTinh;

	// Nhap trong nam
	private Double ntnThoc;
	private Double ntnGao;

	// Xuat trong nam
	private List<VatTuNhapReq> xtnThoc = new ArrayList<>();
	private List<VatTuNhapReq> xtnGao = new ArrayList<>();
}
