package com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DxDcLtRes {

    private String chiTieu;

    private Double tdcTongSoQuyThoc;
    private Double dcTongSoQuyThoc;
    private Double sdcTongSoQuyThoc;

    private String maVatTuThoc;
    private Double tdcThoc;
    private Double dcThoc;
    private Double sdcThoc;

    private String maVatTuGao;
    private Double tdcGao;
    private Double dcGao;
    private Double sdcGao;

}
