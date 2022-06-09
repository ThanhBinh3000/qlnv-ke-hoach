package com.tcdt.qlnvkhoach.service.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoach.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamCount;
import com.tcdt.qlnvkhoach.response.giaokehoachvondaunam.GiaoKeHoachVonDauNamCount;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoach.service.dexuatdieuchinhkehoachnam.DxDcKeHoachNamService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class GiaoKeHoachVonDauNamServiceImpl implements GiaoKeHoachVonDauNamService {

    private final ChiTieuKeHoachNamService chiTieuKeHoachNamService;
    private final DxDcKeHoachNamService dxDcKeHoachNamService;
    private final ChiTieuKeHoachNamRepository chiTieuKeHoachNamRepository;
    @Override
    public GiaoKeHoachVonDauNamCount count() throws Exception {

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request");

        GiaoKeHoachVonDauNamCount count = new GiaoKeHoachVonDauNamCount();
        ChiTieuKeHoachNamCount countQd = chiTieuKeHoachNamService.countCtkhn(ChiTieuKeHoachEnum.QD.getValue());
        count.setChiTieuKeHoach(countQd.getChiTieuKeHoachNamTongCuc() + countQd.getChiTieuKeHoachNamCuc());

        ChiTieuKeHoachNamCount countQdDc = chiTieuKeHoachNamService.countCtkhn(ChiTieuKeHoachEnum.QD_DC.getValue());
        Long dcChiTieu = countQdDc.getChiTieuKeHoachNamTongCuc() + countQdDc.getChiTieuKeHoachNamCuc();

        dcChiTieu += dxDcKeHoachNamService.countDxDc();
        count.setDieuChinhChiTieu(dcChiTieu);
        return count;
    }
}
