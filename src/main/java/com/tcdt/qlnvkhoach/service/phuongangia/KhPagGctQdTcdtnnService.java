package com.tcdt.qlnvkhoach.service.phuongangia;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.entities.phuongangia.*;
import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.repository.phuongangia.*;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagGctQdTcdtnnReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagGctQdTcdtnnSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.util.Contains;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import lombok.extern.log4j.Log4j2;
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
public class KhPagGctQdTcdtnnService extends BaseService {

    @Autowired
    private KhPagGctQdTcdtnnRepository khPagGctQdTcdtnnRepository;

    @Autowired
    private KhLtPagTongHopCTietRepository khLtPagTongHopCTietRepository;

    @Autowired
    private QlnvDmService qlnvDmService;

    @Autowired
    private KhLtPagTongHopRepository khLtPagTongHopRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KhPagTtChungRepository khPagTtChungRepository;

    @Autowired
    private KhPagQdTcdtnnCtietRepository khPagQdTcdtnnCtietRepository;


    public Page<KhPagGctQdTcdtnn> searchPage(KhPagGctQdTcdtnnSearchReq objReq) throws Exception {
        Pageable pageable = PageRequest.of(
                objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(),
                Sort.by("id").ascending()
        );
        Page<KhPagGctQdTcdtnn> data = khPagGctQdTcdtnnRepository.selectPage(
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
    public KhPagGctQdTcdtnn create(KhPagGctQdTcdtnnReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        if (req.getLoaiVthh().startsWith("02")) {
            if (req.getThongTinGiaVt() == null || req.getThongTinGiaVt().size() == 0) {
                throw new Exception("Thông tin giá thiếu hoặc không hợp lệ.");
            }
        } else {
            if (req.getThongTinGiaLt() == null || req.getThongTinGiaLt().size() == 0) {
                throw new Exception("Thông tin giá thiếu hoặc không hợp lệ.");
            }
        }

        Optional<KhPagGctQdTcdtnn> optional = khPagGctQdTcdtnnRepository.findBySoQd(req.getSoQd());
        if (optional.isPresent() && optional.get().getLoaiVthh().equals(req.getLoaiVthh()))
            throw new Exception("số quyết định đã tồn tại");
        Optional<KhPagGctQdTcdtnn> soToTrinh = khPagGctQdTcdtnnRepository.findBySoToTrinh(req.getSoToTrinh());
        if (soToTrinh.isPresent())
            throw new Exception("Số tờ trình đã tồn tại");
        KhPagGctQdTcdtnn data = new KhPagGctQdTcdtnn();
        BeanUtils.copyProperties(req, data, "id");
        data.setTrangThai(TrangThaiDungChungEnum.DUTHAO.getId());
        data.setMaDvi(userInfo.getDvql());
        data.setCapDvi(userInfo.getCapDvi());
        KhPagGctQdTcdtnn save = khPagGctQdTcdtnnRepository.save(data);
        //lưu thong tin giá
        if (!req.getLoaiVthh().startsWith("02")) {
            List<KhPagTongHopCTiet> listThongTinGiaTongHop = req.getThongTinGiaLt();
            if (listThongTinGiaTongHop != null) {
                listThongTinGiaTongHop.forEach(s -> {
                    s.setQdTcdtnnId(data.getId());
                });
            }
            khLtPagTongHopCTietRepository.saveAll(listThongTinGiaTongHop);
        } else {
            List<KhPagTtChung> listThongTinGiaDeXuat = req.getThongTinGiaVt();
            if (listThongTinGiaDeXuat != null) {
                listThongTinGiaDeXuat.forEach(s -> {
                    s.setQdTcdtnnId(data.getId());
                });
            }
            khPagTtChungRepository.saveAll(listThongTinGiaDeXuat);
        }
        return save;
    }

    @Transactional(rollbackOn = Exception.class)
    public KhPagGctQdTcdtnn update(KhPagGctQdTcdtnnReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bab request.");
        Optional<KhPagGctQdTcdtnn> optional = khPagGctQdTcdtnnRepository.findById(req.getId());
        if (!optional.isPresent())
            throw new Exception("Quyết định giá của TCDTNN không tồn tại");
        Optional<KhPagGctQdTcdtnn> qdTcdtnn = khPagGctQdTcdtnnRepository.findBySoQd(req.getSoQd());
        if (qdTcdtnn.isPresent() && qdTcdtnn.get().getId() != req.getId())
            throw new Exception("số quyết định đã tồn tại");
        if (!TrangThaiDungChungEnum.DUTHAO.getId().equals(optional.get().getTrangThai()))
            throw new Exception("Chỉ được sửa quyết định dự thảo.");
        Optional<KhPagGctQdTcdtnn> soToTrinh = khPagGctQdTcdtnnRepository.findBySoToTrinh(req.getSoToTrinh());
        if (soToTrinh != null && soToTrinh.get().getId() != req.getId()) {
            throw new UnsupportedOperationException("Số tờ trình đã tồn tại");
        }
        KhPagGctQdTcdtnn data = optional.get();
        BeanUtils.copyProperties(req, data, "id");
        KhPagGctQdTcdtnn update = khPagGctQdTcdtnnRepository.save(data);
        if (!req.getLoaiVthh().startsWith("02")) {
            List<KhPagTongHopCTiet> listThongTinGiaTongHop = req.getThongTinGiaLt();
            if (listThongTinGiaTongHop != null) {
                listThongTinGiaTongHop.forEach(s -> {
                    s.setQdTcdtnnId(data.getId());
                });
            }
            khLtPagTongHopCTietRepository.saveAll(listThongTinGiaTongHop);
        } else {
            List<KhPagTtChung> listThongTinGiaDeXuat = req.getThongTinGiaVt();
            if (listThongTinGiaDeXuat != null) {
                listThongTinGiaDeXuat.forEach(s -> {
                    s.setQdTcdtnnId(data.getId());
                });
            }
            khPagTtChungRepository.saveAll(listThongTinGiaDeXuat);
        }
        return update;
    }

    public KhPagGctQdTcdtnn detail(String id) throws Exception {
        Optional<KhPagGctQdTcdtnn> data = khPagGctQdTcdtnnRepository.findById(Long.valueOf(id));
        Map<String,String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        if (!data.isPresent()) {
            throw new Exception("Không tìm thấy dữ liệu");
        }
        KhPagGctQdTcdtnn detail = data.get();
        detail.setTenLoaiVthh(hashMapHh.get(detail.getLoaiVthh()));
        if (detail.getLoaiVthh().startsWith("02")) {
            List<KhPagTtChung> thongTinChungVt = khPagTtChungRepository.findByQdTcdtnnId(Long.valueOf(id));
            detail.setThongTinGiaVt(thongTinChungVt);
            thongTinChungVt.forEach(item -> {
                item.setTenCloaiVthh(hashMapHh.get(item.getCloaiVthh()));
            });
        } else  {
            List<KhPagTongHopCTiet> thongTinGiaLt = khLtPagTongHopCTietRepository.findAllByQdTcdtnnId(Long.valueOf(id));
            detail.setThongTinGiaLt(thongTinGiaLt);
        }
        return detail;
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(Long id) {
        Optional<KhPagGctQdTcdtnn> optional = khPagGctQdTcdtnnRepository.findById(id);
        if (!optional.isPresent()) throw new UnsupportedOperationException("id không tồn tại");
        if (optional.get().getLoaiVthh().startsWith("02")) {
            List<KhPagTtChung> ttChungs = khPagTtChungRepository.findByQdTcdtnnId(id);
            ttChungs.forEach(item -> {
                item.setQdTcdtnnId(null);
            });
            khPagTtChungRepository.saveAll(ttChungs);
        } else {
            List<KhPagTongHopCTiet> listCTiets = khLtPagTongHopCTietRepository.findAllByQdTcdtnnId(id);
            listCTiets.forEach(item -> {
                item.setQdTcdtnnId(null);
            });
            khLtPagTongHopCTietRepository.saveAll(listCTiets);
        }

        khPagGctQdTcdtnnRepository.delete(optional.get());
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteListId(List<Long> listId) {
        khPagQdTcdtnnCtietRepository.deleteAllByQdTcdtnnIdIn(listId);
        khPagGctQdTcdtnnRepository.deleteAllByIdIn(listId);
    }

    public void export(KhPagGctQdTcdtnnSearchReq objReq, HttpServletResponse response) throws Exception {
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhPagGctQdTcdtnn> page = this.searchPage(objReq);
        List<KhPagGctQdTcdtnn> data = page.getContent();

        String title = "Danh sách quyết định của TCDTNN";
        String[] rowsName = new String[]{"STT", "Số quyết định", "Ngày ký", "Trích yếu", "Năm kế hoạch", "Loại Giá", "Loại hàng hóa", "Trạng thái"};
        String fileName = "danh-sach-quyet-dinh-tcdtnn.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < data.size(); i++) {
            KhPagGctQdTcdtnn dx = data.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i;
            objs[1] = dx.getSoQd();
            objs[2] = dx.getNgayKy();
            objs[3] = dx.getTrichYeu();
            objs[4] = dx.getNamKeHoach();
            objs[5] = dx.getTenLoaiGia();
            objs[6] = dx.getTenLoaiVthh();
            objs[7] = dx.getTenTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex = new ExportExcel(title, fileName, rowsName, dataList, response);
        ex.export();
    }

    public KhPagGctQdTcdtnn approve(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (StringUtils.isEmpty(stReq.getId())) {
            throw new Exception("Không tìm thấy dữ liệu");
        }
        Optional<KhPagGctQdTcdtnn> data = khPagGctQdTcdtnnRepository.findById(stReq.getId());
        if (!data.isPresent()) {
            throw new Exception("Không tìm thấy dữ liệu");
        }
        String status = stReq.getTrangThai() + data.get().getTrangThai();
        switch (status) {
            case Contains.BAN_HANH + Contains.MOI_TAO:
                data.get().setNguoiPduyet(userInfo.getUsername());
                data.get().setNgayPduyet(new Date());
                break;
            default:
                throw new Exception("Phê duyệt không thành công");
        }

        data.get().setTrangThai(stReq.getTrangThai());

        KhPagGctQdTcdtnn createCheck = khPagGctQdTcdtnnRepository.save(data.get());

        return createCheck;
    }

    @org.springframework.transaction.annotation.Transactional
    public List<KhPagTongHop> listToTrinh(KhLtPagTongHopSearchReq req) throws Exception {
        List<KhPagTongHop> list = khLtPagTongHopRepository.listToTrinh(req);
        return list;
    }

    public List<KhPagGctQdTcdtnn> listQdgTcdtnn(KhPagGctQdTcdtnnSearchReq req) throws Exception {
        List<KhPagGctQdTcdtnn> data = khPagGctQdTcdtnnRepository.dsToTrinhTh(req.getTrangThai(), req.getPagType().equals("VT") ? "02" : null);
        List<Long> qdTcdtnnIds = data.stream().map(KhPagGctQdTcdtnn::getId).collect(Collectors.toList());
        List<KhPagTongHopCTiet> lChitiets = khLtPagTongHopCTietRepository.findAllByQdTcdtnnIdIn(qdTcdtnnIds);
        Map<String, String> mapHh = qlnvDmService.getListDanhMucHangHoa();
        Map<String, String> mapLoaiGia = qlnvDmService.getListDanhMucChung("LOAI_GIA");
        List<String> maDvis = lChitiets.stream().map(KhPagTongHopCTiet::getMaDvi).collect(Collectors.toList());
        Map<String, QlnvDmDonvi> listDvi = qlnvDmService.getMapDonVi(maDvis);
        lChitiets.forEach(s -> {
            s.setTenDvi(listDvi.get(s.getMaDvi()).getTenDvi());
        });
        Map<Long, List<KhPagTongHopCTiet>> mapListChitiet = lChitiets.stream().collect(Collectors.groupingBy(item -> item.getQdTcdtnnId()));
        data.forEach(item -> {
            if (item.getLoaiVthh().startsWith("02")) {
                List<KhPagTtChung> thongTinChungVt = khPagTtChungRepository.findByQdTcdtnnId(Long.valueOf(item.getId()));
                item.setThongTinGiaVt(thongTinChungVt);
            } else {
                item.setTchuanCluong(qlnvDmService.getTieuChuanCluongByMaLoaiVthh(item.getLoaiVthh()));
                item.setThongTinGiaLt(mapListChitiet.get(item.getId()));
                item.setTenLoaiGia(mapLoaiGia.get(item.getLoaiGia()));
                item.setTenLoaiVthh(mapHh.get(item.getLoaiVthh()));
                item.setTenCloaiVthh(mapHh.get(item.getCloaiVthh()));
            }
        });
        return data;
    }

}