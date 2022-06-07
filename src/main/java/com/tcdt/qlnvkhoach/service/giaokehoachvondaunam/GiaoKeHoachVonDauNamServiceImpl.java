package com.tcdt.qlnvkhoach.service.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoach.response.giaokehoachvondaunam.GiaoKeHoachVonDauNamCount;
import com.tcdt.qlnvkhoach.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoach.service.dexuatdieuchinhkehoachnam.DxDcKeHoachNamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class GiaoKeHoachVonDauNamServiceImpl implements GiaoKeHoachVonDauNamService {

    private final ChiTieuKeHoachNamService chiTieuKeHoachNamService;
    private final DxDcKeHoachNamService dxDcKeHoachNamService;

    @Override
    public GiaoKeHoachVonDauNamCount count() throws Exception {
        GiaoKeHoachVonDauNamCount count = new GiaoKeHoachVonDauNamCount();
        count.setChiTieuKeHoach(chiTieuKeHoachNamService.countCtkh(ChiTieuKeHoachEnum.QD.getValue()));

        Long dcChiTieu = chiTieuKeHoachNamService.countCtkh(ChiTieuKeHoachEnum.QD_DC.getValue());
        dcChiTieu += dxDcKeHoachNamService.countDxDc();
        count.setDieuChinhChiTieu(dcChiTieu);
        return count;
    }
}
