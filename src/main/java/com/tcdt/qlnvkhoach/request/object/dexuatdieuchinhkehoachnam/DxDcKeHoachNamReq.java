package com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DxDcKeHoachNamReq {
    private Long id;

    @NotNull(message = "Không được để trống")
    private String soVanBan;

    @NotNull(message = "Không được để trống")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ngayKy;

    @NotNull(message = "Không được để trống")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ngayHieuLuc;

    @NotNull(message = "Không được để trống")
    private Long namKeHoach;

    @NotNull(message = "Không được để trống")
    private String trichYeu;

    @NotNull(message = "Không được để trống")
    private String nguyenNhan;

    @NotNull(message = "Không được để trống")
    private String noiDung;

    @NotNull(message = "Không được để trống")
    private Long keHoachNamId;

    private List<DxDcLtVtReq> dxDcLtVtReqList = new ArrayList<>();
    private List<FileDinhKemReq> fileDinhKemReqs = new ArrayList<>();
}
