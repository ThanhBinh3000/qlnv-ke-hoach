package com.tcdt.qlnvkhoachphi.request.object.chitieukehoachnam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QdDcChiTieuKeHoachNamReq {
    @NotNull(message = "Không được để trống")
    private ChiTieuKeHoachNamReq qdDc;

    @NotNull(message = "Không được để trống")
    private ChiTieuKeHoachNamReq qd;

    @NotNull(message = "Không được để trống")
    private Long qdGocId;
}
