package com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachluongthucdutru;

import com.tcdt.qlnvkhoach.response.chitieukehoachnam.VatTuNhapRes;
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
	private Long khGaoId;
	private Long khThocId;
	private Integer stt;
	private Long donViId;
	private String maDonVi;
	private String tenDonvi;
	private String donViTinh;
	// Ton kho dau nam
	private Double tkdnTongSoQuyThoc;
	private Double tkdnTongThoc;
	private Double tkdnTongGao;
	private List<VatTuNhapRes> tkdnThoc = new ArrayList<>();
	private List<VatTuNhapRes> tkdnGao = new ArrayList<>();

	// Ton kho cuoi nam
	private Double tkcnTongSoQuyThoc;
	private Double tkcnTongThoc;
	private Double tkcnTongGao;

	// Nhap trong nam QD
	private Double ntnTongSoQuyThoc;
	private Double ntnThoc;
	private Double ntnGao;

	// Xuat trong nam QD
	private Double xtnTongSoQuyThoc;
	private Double xtnTongThoc;
	private Double xtnTongGao;
	private List<VatTuNhapRes> xtnThoc = new ArrayList<>();
	private List<VatTuNhapRes> xtnGao = new ArrayList<>();

	// Nhap trong nam QDDC
	private Double tdcNtnTongSoQuyThoc;
	private Double tdcNtnThoc;
	private Double tdcNtnGao;

	private Double dcNtnThoc;
	private Double dcNtnGao;

	private Double sdcNtnTongSoQuyThoc;
	private Double sdcNtnThoc;
	private Double sdcNtnGao;

	// Xuat trong nam QDDC
	private Double tdcXtnTongSoQuyThoc;
	private Double tdcXtnTongThoc;
	private Double tdcXtnTongGao;
	private List<VatTuNhapRes> tdcXtnThoc = new ArrayList<>();
	private List<VatTuNhapRes> tdcXtnGao = new ArrayList<>();

	private List<VatTuNhapRes> dcXtnThoc = new ArrayList<>();
	private List<VatTuNhapRes> dcXtnGao = new ArrayList<>();

	private Double sdcXtnTongSoQuyThoc;
	private Double sdcXtnTongThoc;
	private Double sdcXtnTongGao;
	private List<VatTuNhapRes> sdcXtnThoc = new ArrayList<>();
	private List<VatTuNhapRes> sdcXtnGao = new ArrayList<>();
}
