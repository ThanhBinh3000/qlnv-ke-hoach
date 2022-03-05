package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.kehoachmuoidutru;

import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachMuoiDuTruRes {
    private Long id;
    private Integer stt;
    private Long cucId;
    private String maDonVi;
    private String cucDTNNKhuVuc;

    // Ton kho dau nam
    private Double tkdnTongSoMuoi;
    private List<VatTuNhapRes> tkdnMuoi;

    // Nhap trong nam
    private Double ntnTongSoMuoi;

    // Xuat trong nam
    private Double xtnTongSoMuoi;
    private List<VatTuNhapRes> xtnMuoi;

    // Ton kho cuoi nam
    private Double tkcnTongSoMuoi;
}
