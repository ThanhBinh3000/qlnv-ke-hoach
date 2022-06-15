package com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
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
    private Integer namKeHoach;
    private String trichYeu;
    private String trangThai;
    private String tenTrangThai;// Trang thai cuc
    private String trangThaiDuyet;
    private String lyDoTuChoi;
    private String nguyenNhan;
    private String noiDung;
    private Long keHoachNamId; // can cu
    private String soQdKeHoachNam;// can cu
    private String tenDonVi;
    private String maDonVi;
    private String loaiHangHoa;
    private String tenLoaiHangHoa;
    private List<FileDinhKemChung> fileDinhKems = new ArrayList<>();
    private List<DxDcLtMuoiRes> dxDcltList = new ArrayList<>();
    private List<DxDcLtMuoiRes> dxDcMuoiList = new ArrayList<>();
    private List<DxDcVtRes> dxDcVtList = new ArrayList<>();
}
