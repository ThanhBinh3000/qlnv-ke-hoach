package com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam;

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
    private Double soLuong;

    @NotNull(message = "Không được để trống")
    private String donViTinh;

    @NotNull(message = "Không được để trống")
    private String chiTieu;

    /**
     * @see com.tcdt.qlnvkhoach.enums.LoaiHangHoaEnum
     */
    @NotNull(message = "Không được để trống")
    private String loai;
}
