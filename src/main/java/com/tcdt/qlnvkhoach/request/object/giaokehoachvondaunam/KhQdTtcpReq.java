package com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class KhQdTtcpReq {
    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    Long id;
    @NotNull(message = "Không được để trống")
    Integer nam;
    @NotNull(message = "Không được để trống")
    @Size(max = 50,message = "Số quyết định không được vượt quá 50 ký tự")
    String soQd;
    @NotNull(message = "Không được để trống")
    Date ngayQd;
    @Size(max = 500,message = "Trích Yếu không được vượt quá 500 ký tự")
    String trichYeu;




}
