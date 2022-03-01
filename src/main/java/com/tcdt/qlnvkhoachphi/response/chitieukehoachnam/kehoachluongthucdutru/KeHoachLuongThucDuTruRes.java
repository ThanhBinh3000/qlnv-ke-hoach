package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachluongthucdutru;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeHoachLuongThucDuTruRes {
	private Integer stt;
	private Long cucId;
	private String cucDTNNKhuVuc;

	// Ton kho dau nam
	private Double tkdnTongSoQuyThoc;
	private Double tkdnTongThoc;
	private Double tkdnTongGao;
	private List<VatTuNhapRes> tkdnThoc = new ArrayList<>();
	private List<VatTuNhapRes> tkdnGao = new ArrayList<>();

	// Nhap trong nam
	private Double ntnTongSoQuyThoc;
	private Double ntnThoc;
	private Double ntnGao;

	// Xuat trong nam
	private Double xtnTongSoQuyThoc;
	private Double xtnTongThoc;
	private Double xtnTongGao;
	private List<VatTuNhapRes> xtnThoc = new ArrayList<>();
	private List<VatTuNhapRes> xtnGao = new ArrayList<>();

	// Ton kho cuoi nam
	private Double tkcnTongSoQuyThoc;
	private Double tkcnTongThoc;
	private Double tkcnTongGao;
}
