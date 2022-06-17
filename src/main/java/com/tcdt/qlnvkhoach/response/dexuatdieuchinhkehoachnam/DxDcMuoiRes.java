package com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DxDcMuoiRes {
    private Long id;
    private String chiTieu;
    private String maVatTu;
    private Double tdc;
    private Double dc;
    private Double sdc;

    private List<DxDcLtVtCtRes> dxDcLtVtCtList = new ArrayList<>();
}
