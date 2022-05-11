package com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi;

import com.tcdt.qlnvkhoach.response.chitieukehoachnam.VatTuNhapRes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
public class NhomVatTuThietBiRes {
	private Integer stt;
	private String maVatTuCha;
	private String tenVatTuCha;
	private Long vatTuChaId;
	private String donViTinh;
	private List<VatTuThietBiRes> vatTuThietBi = new ArrayList();

	private Double nhapTrongNam;
	private Double tongNhap;
	private Double tongCacNamTruoc;
	private List<VatTuNhapRes> cacNamTruoc = new ArrayList<>();

	public List<VatTuThietBiRes> getVatTuThietBi() {
		this.vatTuThietBi.sort(Comparator.comparing(VatTuThietBiRes::getStt));
		return this.vatTuThietBi;
	}

	public Double getNhapTrongNam() {
		return getVatTuThietBi().stream().mapToDouble(VatTuThietBiRes::getNhapTrongNam).sum();
	}

	public Double getTongNhap() {
		return getVatTuThietBi().stream().mapToDouble(VatTuThietBiRes::getTongNhap).sum();
	}

	public Double getTongCacNamTruoc() {
		return getVatTuThietBi().stream().mapToDouble(VatTuThietBiRes::getTongCacNamTruoc).sum();
	}

	public List<VatTuNhapRes> getCacNamTruoc() {
		List<VatTuNhapRes> vatTuNhapResList = new ArrayList<>();
		for (VatTuThietBiRes vatTuThietBi : vatTuThietBi) {
			for (VatTuNhapRes vatTuNhapRes : vatTuThietBi.getCacNamTruoc()) {
				VatTuNhapRes tongNam = vatTuNhapResList.stream().filter(v -> vatTuNhapRes.getNam().equals(v.getNam())).findFirst().orElse(null);
				if (tongNam == null) {
					tongNam = new VatTuNhapRes();
					tongNam.setNam(vatTuNhapRes.getNam());
					tongNam.setSoLuong(0d);
					tongNam.setVatTuId(vatTuThietBi.getVatTuId());
					vatTuNhapResList.add(tongNam);
				}
				tongNam.setSoLuong(tongNam.getSoLuong() + vatTuNhapRes.getSoLuong());
			}
		}
		return vatTuNhapResList;
	}

	public Integer getStt() {
		return getVatTuThietBi().get(0).getStt();
	}
}
