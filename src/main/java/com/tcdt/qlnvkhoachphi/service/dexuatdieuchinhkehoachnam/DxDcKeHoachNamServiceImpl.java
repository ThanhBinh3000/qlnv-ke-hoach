package com.tcdt.qlnvkhoachphi.service.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoachphi.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoachphi.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoachphi.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import com.tcdt.qlnvkhoachphi.entities.dexuatdieuchinhkehoachnam.DxDcLtVt;
import com.tcdt.qlnvkhoachphi.enums.ChiTieuKeHoachNamStatus;
import com.tcdt.qlnvkhoachphi.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoachphi.repository.dexuatdieuchinhkehoachnam.DxDcKeHoachNamRepository;
import com.tcdt.qlnvkhoachphi.repository.dexuatdieuchinhkehoachnam.DxDcLtVtRespository;
import com.tcdt.qlnvkhoachphi.request.object.dexuatdieuchinhkehoachnam.DxDcKeHoachNamReq;
import com.tcdt.qlnvkhoachphi.request.object.dexuatdieuchinhkehoachnam.DxDcLtVtReq;
import com.tcdt.qlnvkhoachphi.response.dexuatdieuchinhkehoachnam.DxDcKeHoachNamRes;
import com.tcdt.qlnvkhoachphi.service.SecurityContextService;
import com.tcdt.qlnvkhoachphi.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoachphi.table.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DxDcKeHoachNamServiceImpl implements DxDcKeHoachNamService {

    @Autowired
    private DxDcKeHoachNamRepository dxDcKeHoachNamRepository;

    @Autowired
    private DxDcLtVtRespository dxDcLtVtRespository;

    @Autowired
    private FileDinhKemService fileDinhKemService;

    @Autowired
    private ChiTieuKeHoachNamRepository chiTieuKeHoachNamRepository;

    public DxDcKeHoachNamRes create(DxDcKeHoachNamReq req) throws Exception {

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request");

        Optional<ChiTieuKeHoachNam> optionalKhn = chiTieuKeHoachNamRepository.findById(req.getKeHoachNamId());
        if (!optionalKhn.isPresent())
            throw new Exception("Không tồn tại chỉ tiêu kế hoạch năm.");

        ChiTieuKeHoachNam chiTieuKeHoachNam = optionalKhn.get();

        DxDcKeHoachNam dxDc = new DxDcKeHoachNam();
        BeanUtils.copyProperties(req, dxDc, "id");
        dxDc.setNgayTao(LocalDate.now());
        dxDc.setNguoiTaoId(userInfo.getId());
        dxDc.setTrangThai(ChiTieuKeHoachNamStatus.DU_THAO.getId());
        dxDc.setMaDvi(userInfo.getDvql());
        dxDc.setCapDvi(userInfo.getCapDvi());
        dxDc.setKeHoachNamId(chiTieuKeHoachNam.getId());
        dxDc.setKeHoachNam(chiTieuKeHoachNam);
        dxDcKeHoachNamRepository.save(dxDc);

        List<DxDcLtVtReq> dxDcLtVtReqList = req.getDxDcLtVtReqList();
        List<DxDcLtVt> ltVts = new ArrayList<>();
        for (DxDcLtVtReq ltVtReq : dxDcLtVtReqList) {
            DxDcLtVt ltVt = new DxDcLtVt();
            BeanUtils.copyProperties(ltVtReq, ltVt, "id");
            ltVt.setDxdckhnId(dxDc.getId());
            ltVts.add(ltVt);
        }
        if (!CollectionUtils.isEmpty(ltVts))
            dxDcLtVtRespository.saveAll(ltVts);

        dxDc.setDxDcLtVtList(ltVts);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKemReqs(), dxDc.getId(), ChiTieuKeHoachNam.TABLE_NAME);
        dxDc.setFileDinhKems(fileDinhKems);
        return this.buildResponse(dxDc);
    }

    private DxDcKeHoachNamRes buildResponse(DxDcKeHoachNam dxDc) {
        DxDcKeHoachNamRes response = new DxDcKeHoachNamRes();
        BeanUtils.copyProperties(dxDc, response);
        response.setFileDinhKems(dxDc.getFileDinhKems());
        return response;
    }
}
