package com.tcdt.qlnvkhoach.request.object.chitieukehoachnam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoach.util.Contains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class KhQdBtcBoNganhReq {

    @ApiModelProperty(notes = "Bắt buộc đối với update")
    Long id;

    @NotNull(message = "Không được để trống")
    Integer namQd;

    @NotNull(message = "Không được để trống")
    String soQd;

    Long idTtcpBoNganh;

    @NotNull(message = "Không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayQd;

    @Size(max = 2, message = "Trạng thái không được vượt quá 2 ký tự")
    @ApiModelProperty(example = "00")
    String trangThai;

    String trichYeu;



    private List<KhQdBtcBoNganhCtietReq> muaTangList;

    private List<KhQdBtcBoNganhCtietReq> xuatGiamList;

    private List<KhQdBtcBoNganhCtietReq> xuatBanList;

    private List<KhQdBtcBoNganhCtietReq> luanPhienList;

    private List<FileDinhKemReq> fileDinhKems = new ArrayList<>();

}
