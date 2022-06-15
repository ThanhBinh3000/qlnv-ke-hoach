package com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DxDcLtVtReq {
    private Long id;

    private String maVatTu;
    private String maVatTuCha;

    private List<DxDcLtVtCtReq> dxDcLtVtCtList = new ArrayList<>();

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
