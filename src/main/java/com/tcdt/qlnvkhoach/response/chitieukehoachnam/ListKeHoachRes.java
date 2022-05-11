package com.tcdt.qlnvkhoach.response.chitieukehoachnam;

import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachluongthucdutru.KeHoachLuongThucDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachmuoidutru.KeHoachMuoiDuTruRes;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.kehoachnhapvattuthietbi.KeHoachVatTuRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListKeHoachRes {
    private List<KeHoachLuongThucDuTruRes> khluongthuc;
    private List<KeHoachMuoiDuTruRes> khMuoi;
    private List<KeHoachVatTuRes> khVatTu;
}
