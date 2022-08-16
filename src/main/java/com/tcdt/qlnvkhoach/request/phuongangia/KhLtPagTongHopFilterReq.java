package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.util.Contains;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class KhLtPagTongHopFilterReq {

    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = "2022")
    String namKhoach;

    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = Contains.LOAI_VTHH_GAO)
    String loaiVthh;

    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = Contains.LOAI_VTHH_GAO)
    String chungloaiVthh;


    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = Contains.LOAI_VTHH_GAO)
    String loaiGia;

    @NotNull(message = "Không được để trống")
    @ApiModelProperty
    String ngayDxuatTu;

    @ApiModelProperty
    String ngayDxuatDen;
}
