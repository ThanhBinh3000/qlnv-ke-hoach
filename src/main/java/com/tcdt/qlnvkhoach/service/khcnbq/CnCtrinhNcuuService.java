package com.tcdt.qlnvkhoach.service.khcnbq;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuu;
import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuuCanCu;
import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuuTienDo;
import com.tcdt.qlnvkhoach.entities.phuongangia.*;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiEnum;
import com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum;
import com.tcdt.qlnvkhoach.repository.FileDinhKemChungRepository;
import com.tcdt.qlnvkhoach.repository.khcnbq.CnCtrinhNcuuCanCuRepository;
import com.tcdt.qlnvkhoach.repository.khcnbq.CnCtrinhNcuuRepository;
import com.tcdt.qlnvkhoach.repository.khcnbq.CnCtrinhNcuuTienDoRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.*;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.khcnbq.CnCtrinhNcuuCanCuReq;
import com.tcdt.qlnvkhoach.request.khcnbq.CnCtrinhNcuuReq;
import com.tcdt.qlnvkhoach.request.khcnbq.CnCtrinhNcuuTienDoReq;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagDiaDiemDeHangReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagKetQuaReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.search.catalog.khcnbq.CnCtrinhNcuuSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.util.Contains;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CnCtrinhNcuuService extends BaseService {

    @Autowired
    private CnCtrinhNcuuRepository cnCtrinhNcuuRepository;
    @Autowired
    private FileDinhKemService fileDinhKemService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CnCtrinhNcuuCanCuRepository cnCtrinhNcuuCanCuRepository;
    @Autowired
    private CnCtrinhNcuuTienDoRepository cnCtrinhNcuuTienDoRepository;


    public Page<CnCtrinhNcuu> searchPage(CnCtrinhNcuuSearchReq objReq) throws Exception {
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<CnCtrinhNcuu> data = cnCtrinhNcuuRepository.selectPage(
                objReq.getMaDt(),
                objReq.getTenDt(),
                objReq.getCapDt(),
                objReq.getTrangThai(),
                objReq.getNamTu(),
                objReq.getNamDen(),
                pageable);
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    public CnCtrinhNcuu create(CnCtrinhNcuuReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        CnCtrinhNcuu ctrinhNcuu = mapper.map(req, CnCtrinhNcuu.class);
        ctrinhNcuu.setTrangThai(Contains.DUTHAO);
        ctrinhNcuu = cnCtrinhNcuuRepository.save(ctrinhNcuu);
        CnCtrinhNcuu finalCnCtrinhNcuu = ctrinhNcuu;
        //Save can cu
        List<CnCtrinhNcuuCanCu> cnCanCus = this.saveCanCu(req.getCnCtrinhNcuuCanCuuReqs(), finalCnCtrinhNcuu.getId());
        ctrinhNcuu.setCnCtrinhNcuuCanCuses(cnCanCus);
        //Save tien do
        List<CnCtrinhNcuuTienDo> cnTienDos = this.saveTienDo(req.getCnCtrinhNcuuTienDoReqs(), finalCnCtrinhNcuu.getId());
        ctrinhNcuu.setCnCtrinhNcuuTienDos(cnTienDos);
        return ctrinhNcuu;
    }

    private List<CnCtrinhNcuuCanCu> saveCanCu(List<CnCtrinhNcuuCanCuReq> reqs, Long cnCtrinhNCuuId) {
        List<CnCtrinhNcuuCanCu> canCuList = reqs.stream().map(item -> {
            CnCtrinhNcuuCanCu canCu = mapper.map(item, CnCtrinhNcuuCanCu.class);
            canCu.setCnCtNcId(cnCtrinhNCuuId);
            canCu = cnCtrinhNcuuCanCuRepository.save(canCu);
            List<FileDinhKemReq> listFile = new ArrayList<>();
            listFile.add(item.getFileDinhKem());
            List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(listFile, canCu.getId(), CnCtrinhNcuuCanCu.TABLE_NAME);
            canCu.setFileDinhKem(fileDinhKems.get(0));
            return canCu;
        }).collect(Collectors.toList());
        return canCuList;
    }

    private List<CnCtrinhNcuuTienDo> saveTienDo(List<CnCtrinhNcuuTienDoReq> reqs, Long cnCtNcId) {
        List<CnCtrinhNcuuTienDo> tienDos = reqs.stream().map(item -> {
            CnCtrinhNcuuTienDo tienDo = mapper.map(item, CnCtrinhNcuuTienDo.class);
            tienDo.setCnCtNcId(cnCtNcId);
            tienDo = cnCtrinhNcuuTienDoRepository.save(tienDo);
            return tienDo;
        }).collect(Collectors.toList());
        return tienDos;
    }

    @Transactional(rollbackFor = Exception.class)
    public CnCtrinhNcuu update(CnCtrinhNcuuReq req) throws Exception {
        if (req == null) return null;
        if (req.getId() == null) {
            throw new Exception("Bad request");
        }
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<CnCtrinhNcuu> optional = cnCtrinhNcuuRepository.findById(req.getId());
        if (!optional.isPresent()) throw new Exception("Dữ liệu không tồn tại");
        CnCtrinhNcuu cnCtrinhNcuu = optional.get();
        BeanUtils.copyProperties(req, cnCtrinhNcuu, "id");
        List<Long> cnCtNcIds = new ArrayList<>();
        cnCtNcIds.add(cnCtrinhNcuu.getId());
        this.deleteChildOfCnCtNc(cnCtNcIds);
        //Save can cu
        List<CnCtrinhNcuuCanCu> cnCanCus = this.saveCanCu(req.getCnCtrinhNcuuCanCuuReqs(), cnCtrinhNcuu.getId());
        cnCtrinhNcuu.setCnCtrinhNcuuCanCuses(cnCanCus);
        //Save tien do
        List<CnCtrinhNcuuTienDo> cnTienDos = this.saveTienDo(req.getCnCtrinhNcuuTienDoReqs(), cnCtrinhNcuu.getId());
        cnCtrinhNcuu.setCnCtrinhNcuuTienDos(cnTienDos);
        return cnCtrinhNcuu;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMultiple(List<Long> ids) throws Exception {
        if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");
        List<CnCtrinhNcuu> cnCtrinhNcuuList = cnCtrinhNcuuRepository.findByIdIn(ids);
        List<Long> cnCtNcIds = cnCtrinhNcuuList.stream().map(CnCtrinhNcuu::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cnCtrinhNcuuList)) throw new Exception("Bad request.");
        deleteChildOfCnCtNc(cnCtNcIds);
        cnCtrinhNcuuRepository.deleteAll(cnCtrinhNcuuList);
        return true;
    }

    private void deleteChildOfCnCtNc(List<Long> cnCtNcIds) {
        List<CnCtrinhNcuuTienDo> cnCtrinhNcuuTienDoList = cnCtrinhNcuuTienDoRepository.findAllByCnCtNcIdIn(cnCtNcIds);
        if (!CollectionUtils.isEmpty(cnCtrinhNcuuTienDoList)) {
            cnCtrinhNcuuTienDoRepository.deleteAll(cnCtrinhNcuuTienDoList);
        }
        List<CnCtrinhNcuuCanCu> cnCtrinhNcuuCanCuList = cnCtrinhNcuuCanCuRepository.findAllByCnCtNcIdIn(cnCtNcIds);
        if (!CollectionUtils.isEmpty(cnCtrinhNcuuCanCuList)) {
            List<Long> canCuIds = cnCtrinhNcuuCanCuList.stream().map(CnCtrinhNcuuCanCu::getId).collect(Collectors.toList());
            fileDinhKemService.deleteMultiple(canCuIds, Collections.singleton(CnCtrinhNcuuCanCu.TABLE_NAME));
            cnCtrinhNcuuCanCuRepository.deleteAll(cnCtrinhNcuuCanCuList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception {
        Optional<CnCtrinhNcuu> qOptional = cnCtrinhNcuuRepository.findById(id);
        if (!qOptional.isPresent()) {
            throw new UserPrincipalNotFoundException("Id không tồn tại");
        }
        List<Long> pagIds = new ArrayList<>();
        pagIds.add(id);
        deleteChildOfCnCtNc(pagIds);
        cnCtrinhNcuuRepository.delete(qOptional.get());
    }

//    @Transactional(rollbackFor = Exception.class)
//    public CnCtrinhNcuu approved(StatusReq objReq) throws Exception {
//        UserInfo userInfo = SecurityContextService.getUser();
//        if (StringUtils.isEmpty(objReq.getId()))
//            throw new Exception("Không tìm thấy dữ liệu");
//        Optional<CnCtrinhNcuu> opPag = cnCtrinhNcuuRepository.findById(Long.valueOf(objReq.getId()));
//        if (!opPag.isPresent())
//            throw new Exception("Không tìm thấy dữ liệu");
//        String status = objReq.getTrangThai() + opPag.get().getTrangThai();
//        switch (status) {
//            case Contains.CHODUYET_TP + Contains.DUTHAO:
//            case Contains.CHODUYET_TP + Contains.TUCHOI_TP:
//            case Contains.CHODUYET_LDC + Contains.CHODUYET_TP:
//            case Contains.DADUYET_LDC + Contains.CHODUYET_LDC:
//            case Contains.CHODUYET_TP + Contains.TUCHOI_LDC:
//            case Contains.CHODUYET_LDV + Contains.DUTHAO:
//                break;
//            case Contains.TUCHOI_TP + Contains.CHODUYET_TP:
//            case Contains.TUCHOI_LDC + Contains.CHODUYET_LDC:
//            case Contains.TUCHOI_LDV + Contains.CHODUYET_LDV:
//            case Contains.DADUYET_LDV + Contains.CHODUYET_LDV:
//                break;
//            default:
//                throw new Exception("Phê duyệt không thành công");
//        }
//        opPag.get().setTrangThai(objReq.getTrangThai());
//        CnCtrinhNcuu khPhuongAnGia = cnCtrinhNcuuRepository.save(opPag.get());
//        khPhuongAnGia.setTenTrangThai(PAGTrangThaiEnum.getTrangThaiDuyetById(khPhuongAnGia.getTrangThai()));
//        return khPhuongAnGia;
//    }

    @Transactional(rollbackFor = Exception.class)
    public CnCtrinhNcuu detail(String id) throws Exception {
        Optional<CnCtrinhNcuu> qOptional = cnCtrinhNcuuRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()) {
            throw new Exception("Công trình nghiên cứu công nghệ bảo quản không tồn tại");
        }
        CnCtrinhNcuu data = qOptional.get();
        data.setCnCtrinhNcuuTienDos(cnCtrinhNcuuTienDoRepository.findByCnCtNcId(data.getId()));
        data.setCnCtrinhNcuuCanCuses(cnCtrinhNcuuCanCuRepository.findByCnCtNcId(data.getId()));
        return data;
    }

    public void exportCnCtrinhNcuu(CnCtrinhNcuuSearchReq objReq, HttpServletResponse response) throws Exception {
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<CnCtrinhNcuu> page = this.searchPage(objReq);
        List<CnCtrinhNcuu> data = page.getContent();
        String title = "Danh sách đề xuất phương án giá";
        String[] rowsName = new String[]{"STT", "Mã đề tài", "Tên đề tài", "Cấp đề tài", "Từ năm", "Đến năm", "Trạng thái"};
        String fileName = "danh-sach-cn-ctrinh-ncuu.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < data.size(); i++) {
            CnCtrinhNcuu dx = data.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i;
            objs[1] = dx.getMaDt();
            objs[2] = dx.getTenDt();
            objs[3] = dx.getTenCapDt();
            objs[4] = dx.getNamTu();
            objs[5] = dx.getNamDen();
            objs[8] = dx.getTenTrangThai();
            dataList.add(objs);
        }
        ExportExcel ex = new ExportExcel(title, fileName, rowsName, dataList, response);
        ex.export();
    }


}
