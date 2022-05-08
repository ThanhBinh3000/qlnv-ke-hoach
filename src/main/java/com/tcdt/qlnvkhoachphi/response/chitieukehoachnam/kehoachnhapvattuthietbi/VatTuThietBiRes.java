package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachnhapvattuthietbi;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class VatTuThietBiRes {

	private Long id;
	private Integer stt;
	private String donViTinh;

	private String maVatTu;
	private Long vatTuId;
	private String tenVatTu;

	private String maVatTuCha;
	private Long vatTuChaId;
	private String tenVatTuCha;
	private String kyHieu;

	// Nhap trong nam QD
	private Double nhapTrongNam;
	private Double tongNhap;
	private Double tongCacNamTruoc;
	private List<VatTuNhapRes> cacNamTruoc = new ArrayList<>();

	// Nhap trong nam QDDC
	private Double tdcNhapTrongNam;
	private Double tdcTongNhap;
	private Double tdcTongCacNamTruoc;
	private List<VatTuNhapRes> tdcCacNamTruoc = new ArrayList<>();

	private Double dcNhapTrongNam;

	private Double sdcNhapTrongNam;
	private Double sdcTongNhap;
	private Double sdcTongCacNamTruoc;
	private List<VatTuNhapRes> sdcCacNamTruoc = new ArrayList<>();
}
