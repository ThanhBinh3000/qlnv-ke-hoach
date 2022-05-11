package com.tcdt.qlnvkhoach.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class  KeHoachLuongThucDuTruReq {
	@NotNull
	@Min(value = 0, message = "Stt phải lớn hơn 0")
	private Integer stt;
	private Long khGaoId;
	private Long khThocId;
	private Long donViId;
	private String maDvi;

	@Size(max = 500, message = "Trích yếu không được vượt quá 500 ký tự.")
	private String trichYeu;
	private String tenDonVi;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Đơn vị tính không được vượt quá 50 ký tự.")
	private String donViTinh;

	// Nhap trong nam
	@Min(value = 0, message = "Số lượng phải lớn hơn 0")
	private Double ntnThoc;

	@Min(value = 0, message = "Số lượng phải lớn hơn 0")
	private Double ntnGao;

	// Xuat trong nam
	private List<VatTuNhapReq> xtnThoc = new ArrayList<>();
	private List<VatTuNhapReq> xtnGao = new ArrayList<>();
}
