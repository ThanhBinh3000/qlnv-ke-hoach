package com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KhQdBtcTcdtCtietReq {
    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    Long id;
    Long idDanhMuc;
    String loaiVthh;
    Long soLuong;
    Long sluongDtoan;
    String type;
    Long idQdBtcTcdt;
}
