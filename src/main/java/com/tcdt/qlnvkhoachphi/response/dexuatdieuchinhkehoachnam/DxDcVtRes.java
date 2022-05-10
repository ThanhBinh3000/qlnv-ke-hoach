package com.tcdt.qlnvkhoachphi.response.dexuatdieuchinhkehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DxDcVtRes {
    private String chiTieu;
    private String maVatTu;
    private String tenVatTu;
    private String maVatTuCha;
    private String tenVatTuCha;
    private String kyHieu;
    private String donViTinh;
    private Double tdcTongSo;
    private Double tdcCacNamTruoc;
    private Double tdcKeHoachNam;
    private Double dc;
    private Double sdcTongSo;
    private Double sdcCacNamTruoc;
    private Double sdcKeHoachNam;
}
