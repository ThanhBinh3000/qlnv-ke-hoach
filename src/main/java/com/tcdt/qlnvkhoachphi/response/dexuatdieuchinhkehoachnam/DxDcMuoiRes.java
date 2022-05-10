package com.tcdt.qlnvkhoachphi.response.dexuatdieuchinhkehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DxDcMuoiRes {
    private String chiTieu;
    private String maVatTu;
    private Double tdc;
    private Double dc;
    private Double sdc;
}
