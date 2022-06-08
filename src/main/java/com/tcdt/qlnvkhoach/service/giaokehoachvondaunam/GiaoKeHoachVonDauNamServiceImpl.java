package com.tcdt.qlnvkhoach.service.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachEnum;
import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachNamStatusEnum;
import com.tcdt.qlnvkhoach.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoach.request.search.catalog.chitieukehoachnam.SearchChiTieuKeHoachNamReq;
import com.tcdt.qlnvkhoach.response.giaokehoachvondaunam.GiaoKeHoachVonDauNamCount;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.chitieukehoachnam.ChiTieuKeHoachNamService;
import com.tcdt.qlnvkhoach.service.dexuatdieuchinhkehoachnam.DxDcKeHoachNamService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.util.Constants;
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

        String capDvi = userInfo.getCapDvi();
        GiaoKeHoachVonDauNamCount count = new GiaoKeHoachVonDauNamCount();
        Long dcChiTieu = 0L;
        if (Constants.CUC_KHU_VUC.equals(capDvi)) {
            count.setChiTieuKeHoach(chiTieuKeHoachNamRepository.countByParams(true, ChiTieuKeHoachEnum.QD.getValue(), Constants.CUC_KHU_VUC, Constants.TONG_CUC, ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId()));
            dcChiTieu = chiTieuKeHoachNamRepository.countByParams(true, ChiTieuKeHoachEnum.QD_DC.getValue(), Constants.CUC_KHU_VUC, Constants.TONG_CUC, ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId());
        } else if (Constants.TONG_CUC.equals(capDvi)) {
            count.setChiTieuKeHoach(chiTieuKeHoachNamRepository.countByParams(true, ChiTieuKeHoachEnum.QD.getValue(), Constants.TONG_CUC, Constants.CUC_KHU_VUC, ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId()));
            dcChiTieu = chiTieuKeHoachNamRepository.countByParams(true, ChiTieuKeHoachEnum.QD_DC.getValue(), Constants.TONG_CUC, Constants.CUC_KHU_VUC, ChiTieuKeHoachNamStatusEnum.BAN_HANH.getId());
        } else if (Constants.CHI_CUC.equals(capDvi)) {
            SearchChiTieuKeHoachNamReq req = new SearchChiTieuKeHoachNamReq();
            chiTieuKeHoachNamService.prepareSearchReq(req, userInfo, req.getCapDvi());
            count.setChiTieuKeHoach((long) chiTieuKeHoachNamRepository.countCtkhn(req));
        }

        dcChiTieu += dxDcKeHoachNamService.countDxDc();
        count.setDieuChinhChiTieu(dcChiTieu);
        return count;
    }
}
