package com.tcdt.qlnvkhoach.request.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHopCTiet;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhPagGctQdTcdtnnReq{
    private Long id;

    private String maDvi;

    private String trangThai;

    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = "2022")
    private Long namKeHoach;

    @NotNull(message = "Không được để trống")
    private String soQd;

    private LocalDate ngayKy;

    private LocalDate ngayHieuLuc;

    @NotNull(message = "Không được để trống")
    private String soToTrinh;

    private String loaiVthh;

    private String cloaiVthh;

    private String loaiGia;

    private String tchuanCluong;

    private String trichYeu;

    private String ghiChu;

    private String capDvi;

    private BigDecimal giaMtdbttBtc;

    private BigDecimal giaMtdbttVatBtc;

    private List<Object> thongTinGia;

    private String pagType;

}
