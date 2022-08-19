package com.tcdt.qlnvkhoach.request.phuongangia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkhoach.util.Contains;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class KhLtPagTongHopFilterReq {

    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = "2022")
    String namKeHoach;

    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = Contains.LOAI_VTHH_GAO)
    String loaiVthh;

    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = Contains.LOAI_VTHH_GAO)
    String cloaiVthh;


    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = Contains.LOAI_VTHH_GAO)
    String loaiGia;

    @NotNull(message = "Không được để trống")
    @ApiModelProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayKyTu;

    @ApiModelProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayKyDen;

    @ApiModelProperty
    String type;

    @NotNull(message = "Không được để trống")
    List<String> maDvis;

    private String noiDung;

    private LocalDate ngayTongHop;

    private String ghiChu;

    private String trichYeu;

    private String maToTrinh;

    private BigDecimal ttGiaDn;

    private BigDecimal ttGiaDnVat;
}
