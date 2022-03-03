package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class  KeHoachLuongThucDuTruReq {
	private Long id;
	private Long cucId;
	private String cucDTNNKhuVuc;
	private String donViTinh;

	// Nhap trong nam
	private Double ntnThoc;
	private Double ntnGao;

	// Xuat trong nam
	private List<VatTuNhapReq> xtnThoc = new ArrayList<>();
	private List<VatTuNhapReq> xtnGao = new ArrayList<>();
}
