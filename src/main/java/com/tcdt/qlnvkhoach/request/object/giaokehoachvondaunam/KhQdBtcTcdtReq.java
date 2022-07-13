package com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class KhQdBtcTcdtReq {
    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    Long id;
    @NotNull(message = "Không được để trống")
    Integer namKhoach;
    @NotNull(message = "Không được để trống")
    @Size(max = 20,message = "Số quyết định không được vượt quá 50 ký tự")
    String soQd;
    @NotNull(message = "Không được để trống")
    Date ngayQd;
    @NotNull(message = "Không được để trống")
    @Size(max = 255,message = "Trích Yếu không được vượt quá 255 ký tự")
    String trichYeu;

    private List<KhQdBtcTcdtCtietReq> listCtiet;


}
