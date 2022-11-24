package com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DxDcLtRes {

    private Long gaoId;
    private Long thocId;
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
    private String diaDiemKho;

    private List<DxDcLtVtCtRes> dxDcLtVtCtList = new ArrayList<>();
}
