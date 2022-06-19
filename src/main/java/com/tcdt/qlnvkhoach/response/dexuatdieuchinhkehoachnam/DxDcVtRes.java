package com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DxDcVtRes {
    private Long id;
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
    private String diaDiemKho;
    private List<DxDcLtVtCtRes> dxDcLtVtCtList = new ArrayList<>();
}
