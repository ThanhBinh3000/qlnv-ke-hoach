package com.tcdt.qlnvkhoach.service.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.ChiTieuKeHoachNam;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcLtVt;
import com.tcdt.qlnvkhoach.enums.ChiTieuKeHoachNamStatus;
import com.tcdt.qlnvkhoach.repository.ChiTieuKeHoachNamRepository;
import com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam.DxDcKeHoachNamRepository;
import com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam.DxDcLtVtRespository;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcLtVtReq;
import com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam.DxDcKeHoachNamRes;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
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
