package com.tcdt.qlnvkhoach.response.chitieukehoachnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VatTuNhapRes {
    private Long id;
    private Integer nam;
    private Double soLuong;
    private Long vatTuId;

    public VatTuNhapRes(Integer nam, Double soLuong, Long vatTuId) {
        this.nam = nam;
        this.soLuong = soLuong;
        this.vatTuId = vatTuId;
    }
}
