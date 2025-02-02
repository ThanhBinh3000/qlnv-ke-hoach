package com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DxDcLtVtCtReq {
    private Long id;
    private String maDvi;
    private String maDiemKho;
    private String maNhaKho;
    private String maNganKho;
    private String maLoKho;
    private Double soLuongTang;
    private Double soLuongGiam;
}
