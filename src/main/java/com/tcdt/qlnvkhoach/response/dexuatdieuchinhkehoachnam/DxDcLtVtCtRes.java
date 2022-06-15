package com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DxDcLtVtCtRes {
    private Long id;

    private Long dxDcLtVtId; // DxDcLtVt
    private String maDvi;
    private String maDiemKho;
    private String maNhaKho;
    private String maNganKho;
    private String maLoKho;
    private Double soLuong;
}
