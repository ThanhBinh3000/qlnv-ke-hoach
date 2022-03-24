package com.tcdt.qlnvkhoachphi.response.chitieukehoachnam;

import com.tcdt.qlnvkhoachphi.query.dto.VatTuNhapQueryDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SoLuongTruocDieuChinhRes {
    private Long donViId;
    private String maDonVi;
    private List<VatTuNhapRes> tonKhoDauNam;
    private List<VatTuNhapRes> nhapTrongNam;
    private List<VatTuNhapRes> xuatTrongNam;
    private List<VatTuNhapRes> keHoachVatTuNamTruoc;
}
