package com.tcdt.qlnvkhoachphi.response.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoachphi.entities.FileDinhKemChung;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DxDcKeHoachNamRes {
    private Long id;
    private String soVanBan;
    private LocalDate ngayKy;
    private LocalDate ngayHieuLuc;
    private Long namKeHoach;
    private String trichYeu;
    private String trangThai;
    private String tenTrangThai;
    private String lyDoTuChoi;
    private String nguyenNhan;
    private String noiDung;
    private Long keHoachNamId;
    private String soQdKeHoachNam;

    private List<FileDinhKemChung> fileDinhKems = new ArrayList<>();
    private List<DxDcLtRes> dxDcltList = new ArrayList<>();
    private List<DxDcMuoiRes> dxDcMuoiList = new ArrayList<>();
    private List<DxDcVtRes> dxDcVtList = new ArrayList<>();
}
