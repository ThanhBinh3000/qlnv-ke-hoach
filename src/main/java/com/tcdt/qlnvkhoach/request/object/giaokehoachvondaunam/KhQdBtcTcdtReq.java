package com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdtCtiet;
import com.tcdt.qlnvkhoach.util.Contains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQd;
    @NotNull(message = "Không được để trống")
    @Size(max = 255,message = "Trích Yếu không được vượt quá 255 ký tự")
    String trichYeu;

    private List<KhQdBtcTcdtCtietReq> listCtiet;

    private List<KhQdBtcTcdtCtietReq> muaTangList;

    private List<KhQdBtcTcdtCtietReq> xuatGiamList;

    private List<KhQdBtcTcdtCtietReq> xuatBanList;

    private List<KhQdBtcTcdtCtietReq> luanPhienList;

}
