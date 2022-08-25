package com.tcdt.qlnvkhoach.service.phuongangia;


import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.phuongangia.*;
import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.repository.phuongangia.*;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagGctQdDcTcdtnnReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagGctQdTcdtnnReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagGctQdDcTcdtnnSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagGctQdTcdtnnSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
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
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KhPagGctQdDcTcdtnnService extends BaseService {

    @Autowired
    private KhPagGctQdDcTcdtnnRepository khPagGctQdDcTcdtnnRepository;

    @Autowired
    private KhPagGctQdDcTcdtnnCTietRepository khPagGctQdDcTcdtnnCTietRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private QlnvDmService qlnvDmService;

    @Autowired
    private KhLtPagTongHopRepository khLtPagTongHopRepository;


    public Page<KhPagGctQdDcTcdtnn> searchPage(KhPagGctQdDcTcdtnnSearchReq objReq) throws Exception {
        Pageable pageable = PageRequest.of(
                objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(),
                Sort.by("id").ascending()
        );
        Page<KhPagGctQdDcTcdtnn> data = khPagGctQdDcTcdtnnRepository.selectPage(
                objReq.getNamKh(),
                objReq.getSoQd(),
                Contains.convertDateToString(objReq.getNgayKyTu()),
                Contains.convertDateToString(objReq.getNgayKyDen()),
                objReq.getTrichYeu(),
                objReq.getPagType().equals("VT") ? "02" : null,
                pageable);
        Map<String, String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        Map<String, String> hashMapLoaiGia = qlnvDmService.getListDanhMucChung("LOAI_GIA");
        data.getContent().forEach(f -> {
            f.setTenTrangThai(TrangThaiDungChungEnum.getTrangThaiDuyetById(f.getTrangThai()));
            f.setTenLoaiVthh(StringUtils.isEmpty(f.getLoaiVthh()) ? null : hashMapHh.get(f.getLoaiVthh()));
            f.setTenLoaiGia(StringUtils.isEmpty(f.getLoaiGia()) ? null : hashMapLoaiGia.get(f.getLoaiGia()));
            f.setTenCloaiVthh(StringUtils.isEmpty(f.getCloaiVthh()) ? null : hashMapHh.get(f.getCloaiVthh()));
        });
        return data;
    }

    @Transactional(rollbackOn = Exception.class)
    public KhPagGctQdDcTcdtnn craete(KhPagGctQdDcTcdtnnReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<KhPagGctQdDcTcdtnn> optional = khPagGctQdDcTcdtnnRepository.findBySoQd(req.getSoQd());
        if (optional.isPresent()) throw new Exception("số quyết định đã tồn tại");
        KhPagGctQdDcTcdtnn data = new ModelMapper().map(req, KhPagGctQdDcTcdtnn.class);
        data.setTrangThai(TrangThaiDungChungEnum.DUTHAO.getId());
        data.setMaDvi(userInfo.getDvql());
        data.setCapDvi(userInfo.getCapDvi());
        KhPagGctQdDcTcdtnn khPagGctQdDcTcdtnn = khPagGctQdDcTcdtnnRepository.save(data);
        //lưu thong tin giá
        List<KhPagQdDcTcdtnnCTiet> khPagQdDcTcdtnnCTiets = req.getThongTinGias().stream().map(item -> {
            KhPagQdDcTcdtnnCTiet modelCTiet = mapper.map(item, KhPagQdDcTcdtnnCTiet.class);
            modelCTiet.setQdDcTcdtnnId(khPagGctQdDcTcdtnn.getId());
            log.info("Save file đính kèm");
            modelCTiet = khPagGctQdDcTcdtnnCTietRepository.save(modelCTiet);
            return modelCTiet;
        }).collect(Collectors.toList());
        khPagGctQdDcTcdtnn.setKhPagQdDcTcdtnnCTiets(khPagQdDcTcdtnnCTiets);
        return khPagGctQdDcTcdtnn;
    }

    @Transactional(rollbackOn = Exception.class)
    public KhPagGctQdDcTcdtnn update(KhPagGctQdDcTcdtnnReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bab request.");
        Optional<KhPagGctQdDcTcdtnn> optional = khPagGctQdDcTcdtnnRepository.findById(req.getId());
        if (!optional.isPresent()) throw new Exception("Quyết định điều chỉnh giá của TCDTNN không tồn tại");
        Optional<KhPagGctQdDcTcdtnn> qdDcTcdtnnUnique = khPagGctQdDcTcdtnnRepository.findBySoQd(req.getSoQd());
        if (qdDcTcdtnnUnique.isPresent() && qdDcTcdtnnUnique.get().getId() != req.getId())
            throw new Exception("số quyết định đã tồn tại");
        if (!TrangThaiDungChungEnum.DUTHAO.getId().equals(optional.get().getTrangThai()))
            throw new Exception("Chỉ được sửa quyết định dự thảo.");
        KhPagGctQdDcTcdtnn data = optional.get();
        BeanUtils.copyProperties(req, data, "id");
        KhPagGctQdDcTcdtnn update = khPagGctQdDcTcdtnnRepository.save(data);
        khPagGctQdDcTcdtnnCTietRepository.deleteAllByQdDcTcdtnnId(data.getId());
        //lưu thong tin giá
        List<KhPagQdDcTcdtnnCTiet> khPagQdDcTcdtnnCTiets = req.getThongTinGias().stream().map(item -> {
            KhPagQdDcTcdtnnCTiet modelCTiet = mapper.map(item, KhPagQdDcTcdtnnCTiet.class);
            modelCTiet.setQdDcTcdtnnId(data.getId());
            log.info("Save file đính kèm");
            modelCTiet = khPagGctQdDcTcdtnnCTietRepository.save(modelCTiet);
            return modelCTiet;
        }).collect(Collectors.toList());
        update.setKhPagQdDcTcdtnnCTiets(khPagQdDcTcdtnnCTiets);
        return update;
    }

    public KhPagGctQdDcTcdtnn detail(String id) throws Exception {
        Optional<KhPagGctQdDcTcdtnn> data = khPagGctQdDcTcdtnnRepository.findById(Long.valueOf(id));
        if (!data.isPresent()) {
            throw new Exception("Không tìm thấy dữ liệu");
        }
        List<Long> ids = new ArrayList<>();
        ids.add(Long.parseLong(id));
        List<KhPagQdDcTcdtnnCTiet> lChitiets = khPagGctQdDcTcdtnnCTietRepository.findAllByQdDcTcdtnnIdIn(ids);
        data.get().setKhPagQdDcTcdtnnCTiets(lChitiets);
        Map<String, String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        Map<String, String> hashMapLoaiGia = qlnvDmService.getListDanhMucChung("LOAI_GIA");
        data.get().setTenTrangThai(TrangThaiDungChungEnum.getTrangThaiDuyetById(data.get().getTrangThai()));
        data.get().setTenLoaiVthh(StringUtils.isEmpty(data.get().getLoaiVthh()) ? null : hashMapHh.get(data.get().getLoaiVthh()));
        data.get().setTenLoaiGia(StringUtils.isEmpty(data.get().getLoaiGia()) ? null : hashMapLoaiGia.get(data.get().getLoaiGia()));
        data.get().setTenCloaiVthh(StringUtils.isEmpty(data.get().getCloaiVthh()) ? null : hashMapHh.get(data.get().getCloaiVthh()));
        return data.get();
    }

    //
    @Transactional(rollbackOn = Exception.class)
    public void delete(Long id) {
        Optional<KhPagGctQdDcTcdtnn> optional = khPagGctQdDcTcdtnnRepository.findById(id);
        if (!optional.isPresent()) throw new UnsupportedOperationException("Quyết định điều chỉnh không tồn tại");
        khPagGctQdDcTcdtnnCTietRepository.deleteAllByQdDcTcdtnnId(id);
        khPagGctQdDcTcdtnnRepository.delete(optional.get());
    }

   @Transactional(rollbackOn = Exception.class)
   public void deleteListId(List<Long> listId){
       khPagGctQdDcTcdtnnCTietRepository.deleteAllByQdDcTcdtnnIdIn(listId);
       khPagGctQdDcTcdtnnRepository.deleteAllByIdIn(listId);
   }

    public void export(KhPagGctQdDcTcdtnnSearchReq objReq, HttpServletResponse response) throws Exception {
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhPagGctQdDcTcdtnn> page = this.searchPage(objReq);
        List<KhPagGctQdDcTcdtnn> data = page.getContent();

        String title = "Danh sách quyết định của TCDTNN";
        String[] rowsName = new String[]{"STT", "Số quyết định", "Ngày ký", "Trích yếu", "Số QĐ giá của TCDTNN", "Năm kế hoạch", "Loại Giá", "Loại hàng hóa", "Trạng thái"};
        String fileName = "danh-sach-quyet-dinh-tcdtnn.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < data.size(); i++) {
            KhPagGctQdDcTcdtnn dx = data.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i;
            objs[1] = dx.getSoQd();
            objs[2] = dx.getNgayKy();
            objs[3] = dx.getTrichYeu();
            objs[4] = dx.getSoQdgTcdtnn();
            objs[5] = dx.getNamKeHoach();
            objs[6] = dx.getTenLoaiGia();
            objs[7] = dx.getTenLoaiVthh();
            objs[8] = dx.getTenTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex = new ExportExcel(title, fileName, rowsName, dataList, response);
        ex.export();
    }


}
