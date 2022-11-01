package com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CtKhNamChiTietDviReq {
    @NotNull(message = "Không được để trống")
    Long namKh;
    @NotNull(message = "Không được để trống")
    String maDvi;
    @NotNull(message = "Không được để trống")
    String loaiVthh;
}
