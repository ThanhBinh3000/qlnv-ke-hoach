package com.tcdt.qlnvkhoach.request.object.chitieukehoachnam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdBtcTcdtCtietReq;
import com.tcdt.qlnvkhoach.util.Contains;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQd;

    @NotNull(message = "Không được để trống")
    String trichYeu;

    private List<KhQdBtcBoNganhCtietReq> ListBoNganh;

    private List<KhQdBtcBoNganhCtietReq> muaTangList;

    private List<KhQdBtcBoNganhCtietReq> xuatGiamList;

    private List<KhQdBtcBoNganhCtietReq> xuatBanList;

    private List<KhQdBtcBoNganhCtietReq> luanPhienList;

}
