package com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.btc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpBNganhReq;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtc;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtcBNganh;
import com.tcdt.qlnvkhoach.util.Contains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
@Data
public class KhMuaQdBtcReq {
    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    Long id;

    @NotNull(message = "Không được để trống")
    Integer namQd;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số quyết định không được vượt quá 50 ký tự")
    String soQd;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số quyết định không được vượt quá 50 ký tự")
    String soQdTtcp;

    @NotNull(message = "Không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQd;

    @Size(max = 500, message = "Trích Yếu không được vượt quá 500 ký tự")
    String trichYeu;

    private List<KhMuaQdBtcBNganhReq> listBoNganh;
}
