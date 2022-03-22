package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachmuoidutru;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
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
    private Double tkdnTongSoMuoi;
    private List<VatTuNhapRes> tkdnMuoi = new ArrayList<>();

    // Nhap trong nam
    private Double ntnTongSoMuoi;

    // Xuat trong nam
    private Double xtnTongSoMuoi;
    private List<VatTuNhapRes> xtnMuoi = new ArrayList<>();

    // Ton kho cuoi nam
    private Double tkcnTongSoMuoi;
}
