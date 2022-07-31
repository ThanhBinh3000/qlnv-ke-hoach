package com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.util.Contains;
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
    Integer namQd;
    @NotNull(message = "Không được để trống")
    @Size(max = 20,message = "Số quyết định không được vượt quá 50 ký tự")
    String soQd;
    @NotNull(message = "Không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQd;

    @Size(max = 255,message = "Trích Yếu không được vượt quá 255 ký tự")
    String trichYeu;

    @Size(max = 2, message = "Trạng thái không được vượt quá 2 ký tự")
    @ApiModelProperty(example = "00")
    String trangThai;

    private KhQdBtcTcdtCtietReq keHoachNhapXuat;

    private List<KhQdBtcTcdtCtietReq> muaTangList;

    private List<KhQdBtcTcdtCtietReq> xuatGiamList;

    private List<KhQdBtcTcdtCtietReq> xuatBanList;

    private List<KhQdBtcTcdtCtietReq> luanPhienList;

}
