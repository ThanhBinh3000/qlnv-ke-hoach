package com.tcdt.qlnvkhoachphi.request.object.dexuatdieuchinhkehoachnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DxDcLtVtReq {
    private Long id;

    @NotNull(message = "Không được để trống")
    private String maVatTu;

    private String maVatTuCha;

    @NotNull(message = "Không được để trống")
    private Long soLuong;

    @NotNull(message = "Không được để trống")
    private String donViTinh;

    @NotNull(message = "Không được để trống")
    private String chiTieu;
}
