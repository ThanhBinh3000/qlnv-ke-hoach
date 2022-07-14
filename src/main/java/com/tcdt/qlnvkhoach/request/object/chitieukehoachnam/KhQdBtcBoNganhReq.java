package com.tcdt.qlnvkhoach.request.object.chitieukehoachnam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class KhQdBtcBoNganhReq {

    @ApiModelProperty(notes = "Bắt buộc đối với update")
    Long id;

    @NotNull(message = "Không được để trống")
    Integer namKhoach;

    @NotNull(message = "Không được để trống")
    String soQd;

    @NotNull(message = "Không được để trống")
    Date ngayQd;

    @NotNull(message = "Không được để trống")
    String trichYeu;

    private List<KhQdBtcBoNganhCtietReq> ListBoNganh;

}
