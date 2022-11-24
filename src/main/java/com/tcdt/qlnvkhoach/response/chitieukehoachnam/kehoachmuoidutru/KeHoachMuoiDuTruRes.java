package com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachmuoidutru;

import com.tcdt.qlnvkhoach.response.chitieukehoachnam.VatTuNhapRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachMuoiDuTruRes {
    private Long id;
    private Integer stt;
    private Long donViId;
    private String maDonVi;
    private String tenDonVi;
    private String donViTinh;
    // Ton kho dau nam
    private Double tonKhoDauNam;
    private List<VatTuNhapRes> tkdnMuoi = new ArrayList<>();

    // Ton kho cuoi nam
    private Double nhapTrongNam;
    private Double xuatTrongNamMuoi;
    private Double tonKhoCuoiNam;

    // Nhap trong nam QD
    private Double ntnTongSoMuoi;

    // Xuat trong nam QD
    private Double xtnTongSoMuoi;
    private List<VatTuNhapRes> xtnMuoi = new ArrayList<>();

    // Nhap trong nam QDDC
    private Double tdcNtnTongSoMuoi;
    private Double dcNtnTongSoMuoi;
    private Double sdcNtnTongSoMuoi;

    // Xuat trong nam QDDC
    private Double tdcXtnTongSoMuoi;
    private Double sdcXtnTongSoMuoi;
    private List<VatTuNhapRes> tdcXtnMuoi = new ArrayList<>();
    private List<VatTuNhapRes> dcXtnMuoi = new ArrayList<>();
    private List<VatTuNhapRes> sdcXtnMuoi = new ArrayList<>();
}
