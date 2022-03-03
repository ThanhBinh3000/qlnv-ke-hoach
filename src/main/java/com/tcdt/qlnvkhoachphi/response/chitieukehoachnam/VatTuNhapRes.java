package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VatTuNhapRes {
    private Long id;
    private Integer nam;
    private Double soLuong;
    private Long vatTuId;
}
