package com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.enums.DxDcKeHoachNamLoaiEnum;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
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

    private String soVanBan;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ngayKy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ngayHieuLuc;

    @NotNull(message = "Năm kế hoạch không được để trống")
    private Integer namKeHoach;

    private String trichYeu;

    private String nguyenNhan;

    private String noiDung;

    @NotNull(message = "Căn cứ không được để trống")
    private Long keHoachNamId;

    /**
     * @see DxDcKeHoachNamLoaiEnum
     */
    @NotNull(message = "Không được để trống")
    private String loaiHangHoa;

    private List<DxDcLtVtReq> dxDcLtVtReqList = new ArrayList<>();
    private List<FileDinhKemReq> fileDinhKemReqs = new ArrayList<>();
}
