package com.tcdt.qlnvkhoach.service.phuongangia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.entities.phuongangia.*;
import com.tcdt.qlnvkhoach.enums.KhPagQuyetDinhBtcEnum;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiEnum;
import com.tcdt.qlnvkhoach.jwt.CustomUserDetails;
import com.tcdt.qlnvkhoach.repository.phuongangia.*;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhPagQuyetDinhBtcReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagQuyetDinhBtcSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmVattu;
import com.tcdt.qlnvkhoach.util.Constants;
import com.tcdt.qlnvkhoach.util.Contains;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.K;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KhPagQuyetDinhBtcService extends BaseService {
    @Autowired
    private KhLtPhuongAnGiaRepository khLtPhuongAnGiaRepository;
    @Autowired
    private FileDinhKemService fileDinhKemService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KhLtPagKetQuaRepository khLtPagKetQuaRepository;
    @Autowired
    private KhPagQuyetDinhBtcRepository khPagLtQuyetDinhBtcRepository;
    @Autowired
    private KhLtPagTongHopRepository khLtPagTongHopRepository;
    @Autowired
    private KhLtPagTongHopCTietRepository khLtPagTongHopCTietRepository;
    @Autowired
    private KhPagTtChungRepository khPagTtChungRepository;
    @Autowired
    private QlnvDmService qlnvDmService;

    @Autowired
    private KhPagQdBtcCtietRepository khPagQdBtcCtietRepository;
    public Page<KhPagQuyetDinhBtc> searchPage(CustomUserDetails currentUser, KhPagQuyetDinhBtcSearchReq objReq) throws Exception {
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit());
        objReq.setDvql(currentUser.getDvql());
        objReq.toString();
        Page<KhPagQuyetDinhBtc> data = khPagLtQuyetDinhBtcRepository.search(
                objReq,
                pageable);
        Map<String, String> mapHh = qlnvDmService.getListDanhMucHangHoa();
        Map<String, String> mapLoaiGia = qlnvDmService.getListDanhMucChung("LOAI_GIA");
        data.getContent().forEach(s -> {
            if (mapHh.get((s.getLoaiVthh())) != null) {
                s.setTenLoaiVthh(mapHh.get(s.getLoaiVthh()));
            }
            if (mapHh.get((s.getCloaiVthh())) != null) {
                s.setTenCloaiVthh(mapHh.get(s.getCloaiVthh()));
            }
            if (mapLoaiGia.get((s.getLoaiGia())) != null) {
                s.setTenLoaiGia(mapLoaiGia.get(s.getLoaiGia()));
            }
        });
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    public KhPagQuyetDinhBtc create(CustomUserDetails currentUser, KhPagQuyetDinhBtcReq req) throws Exception {
        if (currentUser == null) {
            throw new Exception("Bad request.");
        }
        if (req.getSoToTrinh() == null) {
            throw new Exception("Số tờ trình thiếu hoặc không hợp lệ.");
        }
        if (req.getThongTinGia() == null || req.getThongTinGia().size() == 0) {
            throw new Exception("Thông tin giá thiếu hoặc không hợp lệ.");
        }
        Optional<KhPagQuyetDinhBtc> optional = khPagLtQuyetDinhBtcRepository.findBySoToTrinh(req.getSoToTrinh());
        if(optional.isPresent()){
            throw new Exception("Số tờ trình đã tồn tại");
        }
        KhPagQuyetDinhBtc newRow = new KhPagQuyetDinhBtc();
        BeanUtils.copyProperties(req, newRow, "id");
        newRow.setTrangThai(KhPagQuyetDinhBtcEnum.DU_THAO.getId());
        newRow.setMaDvi(currentUser.getDvql());
        newRow.setCapDvi(currentUser.getUser().getCapDvi());
        khPagLtQuyetDinhBtcRepository.save(newRow);

        //luu thong tin gia
        String strThongTinGia = objectMapper.writeValueAsString(req.getThongTinGia());
        if (req.getPagType().equals("LT")) {
            List<KhPagQdBtcCtiet> listThongTinGiaTongHop = objectMapper.readValue(strThongTinGia, new TypeReference<List<KhPagQdBtcCtiet>>() {
            });
            if (listThongTinGiaTongHop != null) {
                listThongTinGiaTongHop.forEach(s -> {
                    s.setQdBtcId(newRow.getId());
                });
            }
            khPagQdBtcCtietRepository.saveAll(listThongTinGiaTongHop);
        } else if (req.getPagType().equals("VT")) {
            List<KhPagTtChung> listThongTinGiaDeXuat = objectMapper.readValue(strThongTinGia, new TypeReference<List<KhPagTtChung>>() {
            });
            if (listThongTinGiaDeXuat != null) {
                listThongTinGiaDeXuat.forEach(s -> {
                    s.setQdBtcId(newRow.getId());
                });
            }
            khPagTtChungRepository.saveAll(listThongTinGiaDeXuat);
        }
        return newRow;
    }
    @Transactional(rollbackFor = Exception.class)
    public KhPagQuyetDinhBtc update(CustomUserDetails currentUser, KhPagQuyetDinhBtcReq req) throws Exception {
        if (req.getId() == null)
            throw new Exception("Tham số không hợp lệ.");
        KhPagQuyetDinhBtc currentRow = khPagLtQuyetDinhBtcRepository.findById(req.getId()).orElse(null);
        if (currentRow == null)
            throw new Exception("Không tìm thấy dữ liệu.");
        if (!Constants.TONG_CUC.equals(currentUser.getUser().getCapDvi()))
            throw new Exception("Tài khoản không có quyền thực hiện.");
        if (!KhPagQuyetDinhBtcEnum.DU_THAO.getId().equals(currentRow.getTrangThai()))
            throw new Exception("Chỉ được sửa quyết định dự thảo.");
        if (req.getSoToTrinh() == null)
            throw new Exception("Số tờ trình thiếu hoặc không hợp lệ.");
        if (req.getThongTinGia() == null || req.getThongTinGia().size() == 0)
            throw new Exception("Thông tin giá thiếu hoặc không hợp lệ.");
        Optional<KhPagQuyetDinhBtc>optional=khPagLtQuyetDinhBtcRepository.findById(req.getId());
        if (!optional.isPresent()) {
            throw new UnsupportedOperationException("Không tồn tại bản ghi");
        }
        Optional<KhPagQuyetDinhBtc> soToTrinh = khPagLtQuyetDinhBtcRepository.findBySoToTrinh(req.getSoToTrinh());
        if(soToTrinh!=null && soToTrinh.get().getId()!=req.getId()){
            throw new UnsupportedOperationException("Số tờ trình đã tồn tại");
        }

        BeanUtils.copyProperties(req, currentRow, "id", "trangThai");
        khPagLtQuyetDinhBtcRepository.save(currentRow);

        //luu thong tin gia
        String strThongTinGia = objectMapper.writeValueAsString(req.getThongTinGia());
        if (req.getPagType().equals("LT")) {
            List<KhPagQdBtcCtiet> listThongTinGiaTongHop = objectMapper.readValue(strThongTinGia, new TypeReference<List<KhPagQdBtcCtiet>>() {
            });
            if (listThongTinGiaTongHop != null) {
                listThongTinGiaTongHop.forEach(s -> {
                    s.setQdBtcId(currentRow.getId());
                });
            }
            khPagQdBtcCtietRepository.saveAll(listThongTinGiaTongHop);
        } else if (req.getPagType().equals("VT")) {
            List<KhPagTtChung> listThongTinGiaDeXuat = objectMapper.readValue(strThongTinGia, new TypeReference<List<KhPagTtChung>>() {
            });
            if (listThongTinGiaDeXuat != null) {
                listThongTinGiaDeXuat.forEach(s -> {
                    s.setQdBtcId(currentRow.getId());
                });
            }
            khPagTtChungRepository.saveAll(listThongTinGiaDeXuat);
        }
        return currentRow;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMultiple(CustomUserDetails currentUser, List<Long> ids) throws Exception {
        if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");

        List<KhPagQuyetDinhBtc> listData = khPagLtQuyetDinhBtcRepository.findAllById(ids);
        List<KhPagQuyetDinhBtc> listDataValid = listData.stream()
                .filter(s -> s.getTrangThai().equals(KhPagQuyetDinhBtcEnum.DU_THAO.getId()))
                .collect(Collectors.toList());
        khPagLtQuyetDinhBtcRepository.deleteAll(listDataValid);
        return true;
    }

    public KhPagQuyetDinhBtc detail(String id) throws Exception {
        Optional<KhPagQuyetDinhBtc> data = khPagLtQuyetDinhBtcRepository.findById(Long.parseLong(id));
        if (!data.isPresent()) {
            throw new Exception("Bản ghi không tồn tại");
        }
        data.get().setThongTinGia(khPagQdBtcCtietRepository.findAllByQdBtcId(Long.valueOf(id)));
        return data.get();

    }

    public void export(CustomUserDetails currentUser, KhPagQuyetDinhBtcSearchReq objReq, HttpServletResponse response) throws Exception {
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhPagQuyetDinhBtc> page = this.searchPage(currentUser, objReq);
        List<KhPagQuyetDinhBtc> data = page.getContent();

        String title = "Danh sách Quyết định giá mua tối đa, giá bán tối thiểu của BTC";
        String[] rowsName = new String[]{"STT", "Số quyết định", "Ngày ký", "Trích yếu", "Năm kế hoạch", "Loại giá", "Loại hàng hóa", "Trạng thái"};
        String fileName = "danh-sach-qd-gia-mua-toi-da-ban-toi-thieu-cua-btc.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < data.size(); i++) {
            KhPagQuyetDinhBtc dx = data.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i;
            objs[1] = dx.getSoQd();
            objs[2] = dx.getNgayKy();
            objs[3] = dx.getTrichYeu();
            objs[4] = dx.getNamKeHoach();
            objs[5] = dx.getTenLoaiGia();
            objs[6] = dx.getTenLoaiVthh();
            objs[7] = KhPagQuyetDinhBtcEnum.getLabelById(dx.getTrangThai());
            dataList.add(objs);
        }
        ExportExcel ex = new ExportExcel(title, fileName, rowsName, dataList, response);
        ex.export();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(CustomUserDetails currentUser, StatusReq req) throws Exception {
        Optional<KhPagQuyetDinhBtc> currentRow = khPagLtQuyetDinhBtcRepository.findById(req.getId());
        if (!currentRow.isPresent())
            throw new Exception("Không tìm thấy dữ liệu.");
        if (req.getTrangThai().equals(KhPagQuyetDinhBtcEnum.BAN_HANH.getId())) {
            Optional<KhPagTongHop> toTrinh = khLtPagTongHopRepository.findBySoToTrinh(currentRow.get().getSoToTrinh());
            if (toTrinh.isPresent()) {
                toTrinh.get().setTrangThaiTt(KhPagQuyetDinhBtcEnum.DABANHANH_QD.getId());
                khLtPagTongHopRepository.save(toTrinh.get());
            }
            currentRow.get().setTrangThai(KhPagQuyetDinhBtcEnum.BAN_HANH.getId());
            khPagLtQuyetDinhBtcRepository.save(currentRow.get());
            return true;
        }
        return false;
    }


    public List<KhPagTongHop> DsToTrinhDeXuat(KhLtPagTongHopSearchReq objReq) throws Exception {
        List<KhPagTongHop> data = khLtPagTongHopRepository.DsToTrinhDeXuat(objReq);
        return data;
    }
    public List<KhPagTongHop> dsToTrinhTh(KhLtPagTongHopSearchReq objReq) throws Exception {
        List<KhPagTongHop> data = khLtPagTongHopRepository.dsToTrinhTh(objReq.getType(), objReq.getTrangThaiTt());
        List<Long> idsPagTh = data.stream().map(KhPagTongHop::getId).collect(Collectors.toList());
        List<String> soTTBtcs = data.stream().map(KhPagTongHop::getQdGtdttBtc).collect(Collectors.toList());
        List<KhPagQuyetDinhBtc> listQdBtcs = khPagLtQuyetDinhBtcRepository.findAllBySoToTrinhIn(soTTBtcs);
        List<Long> idsQdBtc = listQdBtcs.stream().map(KhPagQuyetDinhBtc::getId).collect(Collectors.toList());
        List<KhPagQdBtcCtiet> listPagQdBtcChiTiets = khPagQdBtcCtietRepository.findAllByQdBtcIdIn(idsQdBtc);
        Map<Long,List<KhPagQdBtcCtiet>> mapQdBtcIdListChitiets = listPagQdBtcChiTiets.stream().collect(Collectors.groupingBy(item -> item.getQdBtcId()));
        List<KhPagTongHopCTiet> lChiTiets = khLtPagTongHopCTietRepository.findByPagThIdIn(idsPagTh);
        List<String> maDvis = lChiTiets.stream().map(KhPagTongHopCTiet::getMaDvi).collect(Collectors.toList());
        Map<String, QlnvDmDonvi> listDvi = qlnvDmService.getMapDonVi(maDvis);
        lChiTiets.forEach(s -> {
            List<KhPagQdBtcCtiet> listCt = mapQdBtcIdListChitiets.get(s.getQdBtcId());
            if(listCt != null && listCt.size() > 0){
                listCt.forEach(c ->{
                    if(s.getMaDvi().equals(c.getMaDvi())){
                        s.setGiaTdttBtc(c.getGiaDn());
                        s.setGiaDnVat(c.getGiaDnVat());
                    }
                });
            }
            s.setTenDvi(listDvi.get(s.getMaDvi()).getTenDvi());
        });
        Map<String, String> mapHh = qlnvDmService.getListDanhMucHangHoa();
        Map<String, String> mapLoaiGia = qlnvDmService.getListDanhMucChung("LOAI_GIA");
        Map<Long, List<KhPagTongHopCTiet>> mapListChitietByPagTh = lChiTiets.stream().collect(Collectors.groupingBy(item -> item.getPagThId()));
        data.forEach(dt -> {
            dt.setTenLoaiGia(mapLoaiGia.get(dt.getLoaiGia() ));
            dt.setTenCloaiVthh(mapHh.get(dt.getCloaiVthh() ));
            dt.setTenLoaiVthh(mapHh.get(dt.getLoaiVthh() ));
            dt.setPagChiTiets(mapListChitietByPagTh.get(dt.getId()));
        });
        return data;
    }

    public List<KhPagTongHopCTiet> DsToTrinhDeXuatChiTietLt(List<Long> ids) throws Exception {
        List<KhPagTongHopCTiet> data = khLtPagTongHopCTietRepository.findByPagThIdIn(ids);
        List<String> maDvis = data.stream().map(KhPagTongHopCTiet::getMaDvi).collect(Collectors.toList());
        Map<String, QlnvDmDonvi> listDvi =qlnvDmService.getMapDonVi(maDvis);
        data.forEach(s -> {
            s.setTenDvi(listDvi.get(s.getMaDvi()).getTenDvi());
        });
        return data;
    }

    public List<KhPhuongAnGia> DsToTrinhDeXuat(KhLtPhuongAnGiaSearchReq objReq) throws Exception {
        List<KhPhuongAnGia> data = khLtPhuongAnGiaRepository.DsToTrinhDeXuat(objReq);
        return data;
    }
    public List<KhPagTtChung> DsToTrinhDeXuatChiTietVt(List<Long> ids) throws Exception {
        List<KhPagTtChung> data = khPagTtChungRepository.findByPhuongAnGiaIdIn(ids);

        List<String> listMaVatTu = data.stream().map(KhPagTtChung::getCloaiVthh).collect(Collectors.toList());
        Map<String, QlnvDmVattu> mapVatTu = qlnvDmService.getMapVatTu(listMaVatTu);
//    List<String> maDvis = data.stream().map(KhPagTtChung::getMaDvi).collect(Collectors.toList());
//    Map<String, QlnvDmDonvi> listDvi =qlnvDmService.getMapDonVi(maDvis);
        data.forEach(s -> {
            if (mapVatTu.get((s.getCloaiVthh())) != null) {
                s.setTenCloaiVthh(mapVatTu.get(s.getCloaiVthh()).getTen());
            }
        });
        return data;
    }
}