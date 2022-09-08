package com.tcdt.qlnvkhoach.service.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.*;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiEnum;
import com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagTongHopCTietRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagTongHopRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPhuongAnGiaRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagTongHopFilterReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagTongHopReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkhoach.util.Contains;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KhLtTongHopPagService extends BaseService {
    @Autowired
    private KhLtPagTongHopRepository khLtPagTongHopRepository;

    @Autowired
    private KhLtPhuongAnGiaRepository khLtPhuongAnGiaRepository;

    @Autowired
    private QlnvDmDonviRepository qlnvDmDonviRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private KhLtPagTongHopCTietRepository khLtPagTongHopCTietRepository;
    @Autowired
    private QlnvDmService qlnvDmService;

    public Page<KhPagTongHop> searchPage(KhLtPagTongHopSearchReq objReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhPagTongHop> data = khLtPagTongHopRepository.selectPage(
                objReq.getNamKh(),
                objReq.getLoaiHh(),
                Contains.convertDateToString(objReq.getNgayKyTu()),
                Contains.convertDateToString(objReq.getNgayKyDen()),
                objReq.getNoiDung(),
                userInfo.getCapDvi().equals("1") ? null : userInfo.getDvql(),
                objReq.getTrangThai(),
                objReq.getType(),
                pageable);
        List<Long> khLtPagTHIds = data.getContent().stream().map(KhPagTongHop::getId).collect(Collectors.toList());
        List<KhPagTongHopCTiet> lChitiet = khLtPagTongHopCTietRepository.findByPagThIdIn(khLtPagTHIds);
        Map<Long, List<KhPagTongHopCTiet>> mPagTHCtiet = lChitiet.stream().collect(Collectors.groupingBy(o -> o.getPagThId()));
        Map<String, String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        Map<String, String> hashMapLoaiGia = qlnvDmService.getListDanhMucChung("LOAI_GIA");
        for (KhPagTongHop khPagTongHop : data.getContent()) {
            khPagTongHop.setPagChiTiets(mPagTHCtiet.get(khPagTongHop.getId()));
            khPagTongHop.setTenLoaiVthh(StringUtils.isEmpty(khPagTongHop.getLoaiVthh()) ? null : hashMapHh.get(khPagTongHop.getLoaiVthh()));
            khPagTongHop.setTenLoaiGia(StringUtils.isEmpty(khPagTongHop.getLoaiGia()) ? null : hashMapLoaiGia.get(khPagTongHop.getLoaiGia()));
            khPagTongHop.setTenCloaiVthh(StringUtils.isEmpty(khPagTongHop.getCloaiVthh()) ? null : hashMapHh.get(khPagTongHop.getCloaiVthh()));
            khPagTongHop.setTenTrangThaiTt(khPagTongHop.getTrangThaiTt() != null ? Contains.getTrangThaiTt(khPagTongHop.getTrangThaiTt()) : null);
            khPagTongHop.setTenTrangThaiTh(Contains.getTrangThaiTh(khPagTongHop.getTrangThaiTh()));
        }
        return data;
    }

    public KhPagTongHop tongHopData(KhLtPagTongHopFilterReq objReq) throws Exception {
        List<KhPhuongAnGia> listPagTH = khLtPhuongAnGiaRepository.listTongHop(objReq.getLoaiVthh(), objReq.getCloaiVthh(), objReq.getNamTongHop(), objReq.getLoaiGia(), Contains.convertDateToString(objReq.getNgayDxTu()), Contains.convertDateToString(objReq.getNgayDxDen()), objReq.getType(), objReq.getMaDvis(), Contains.CHUA_TH, Contains.DADUYET_LDC);
        if (listPagTH.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu tổng hợp");
        }
        List<Long> khLtPagIds = listPagTH.stream().map(KhPhuongAnGia::getId).collect(Collectors.toList());
        if (khLtPagIds.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu tổng hợp");
        }
        KhPagTongHop pagTH = new KhPagTongHop();
        pagTH.setNamTongHop(Long.valueOf(objReq.getNamTongHop()));
        pagTH.setCloaiVthh(objReq.getCloaiVthh());
        pagTH.setLoaiGia(objReq.getLoaiGia());
        pagTH.setLoaiVthh(objReq.getLoaiVthh());
        List<Object[]> khLtMinMaxKQThamDinhs = khLtPhuongAnGiaRepository.listPagWithDonGia(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), khLtPagIds);
        List<Object[]> khLtMinMaxKQKhaoSats = khLtPhuongAnGiaRepository.listPagWithDonGia(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), khLtPagIds);
        if (khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0) {
            if (StringUtils.isEmpty(pagTH.getGiaTdTu()) || (khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0)) {
                pagTH.setGiaTdTu((khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0) ? (BigDecimal) khLtMinMaxKQThamDinhs.get(0)[0] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaTdDen()) || (khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0)) {
                pagTH.setGiaTdDen((khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0) ? (BigDecimal) khLtMinMaxKQThamDinhs.get(0)[1] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaTdVatTu()) || (khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0)) {
                pagTH.setGiaTdVatTu((khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0) ? (BigDecimal) khLtMinMaxKQThamDinhs.get(0)[2] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaTdVatDen()) || (khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0)) {
                pagTH.setGiaTdVatDen((khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0) ? (BigDecimal) khLtMinMaxKQThamDinhs.get(0)[3] : null);
            }
        }
        if (khLtMinMaxKQKhaoSats != null && khLtMinMaxKQKhaoSats.size() > 0) {
            if (StringUtils.isEmpty(pagTH.getGiaTdTu()) || (khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0)) {
                pagTH.setGiaKsTtTu((khLtMinMaxKQKhaoSats != null && khLtMinMaxKQThamDinhs.size() > 0) ? (BigDecimal) khLtMinMaxKQThamDinhs.get(0)[0] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaTdTu()) || (khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0)) {
                pagTH.setGiaKsTtDen((khLtMinMaxKQKhaoSats != null && khLtMinMaxKQThamDinhs.size() > 0) ? (BigDecimal) khLtMinMaxKQKhaoSats.get(0)[1] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaTdTu()) || (khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0)) {
                pagTH.setGiaKsTtVatTu((khLtMinMaxKQKhaoSats != null && khLtMinMaxKQThamDinhs.size() > 0) ? (BigDecimal) khLtMinMaxKQKhaoSats.get(0)[2] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaTdTu()) || (khLtMinMaxKQThamDinhs != null && khLtMinMaxKQThamDinhs.size() > 0)) {
                pagTH.setGiaKsTtVatDen((khLtMinMaxKQKhaoSats != null && khLtMinMaxKQThamDinhs.size() > 0) ? (BigDecimal) khLtMinMaxKQKhaoSats.get(0)[3] : null);
            }
        }
        List<KhPagTongHopCTiet> lChitiet = new ArrayList<>();
        for (KhPhuongAnGia pag : listPagTH) {
            //set min max gia de nghi
            if (StringUtils.isEmpty(pagTH.getGiaDnTu()) || pagTH.getGiaDnTu().compareTo(pag.getGiaDeNghi()) > 0) {
                pagTH.setGiaDnTu(pag.getGiaDeNghi());
            }
            if (StringUtils.isEmpty(pagTH.getGiaDnDen()) || pagTH.getGiaDnDen().compareTo(pag.getGiaDeNghi()) < 0) {
                pagTH.setGiaDnDen(pag.getGiaDeNghi());
            }
            if (StringUtils.isEmpty(pagTH.getGiaDnVatTu()) || pagTH.getGiaDnVatTu().compareTo(pag.getGiaDeNghiVat()) > 0) {
                pagTH.setGiaDnVatTu(pag.getGiaDeNghiVat());
            }
            if (StringUtils.isEmpty(pagTH.getGiaDnVatDen()) || pagTH.getGiaDnVatDen().compareTo(pag.getGiaDeNghiVat()) < 0) {
                pagTH.setGiaDnVatDen(pag.getGiaDeNghiVat());
            }
            KhPagTongHopCTiet cTiet = new KhPagTongHopCTiet();
            cTiet.setSoDx(pag.getSoDeXuat());
            cTiet.setSoLuong(pag.getSoLuong());
            cTiet.setMaDvi(pag.getMaDvi());
            cTiet.setGiaDn(pag.getGiaDeNghi());
            cTiet.setGiaDnVat(pag.getGiaDeNghiVat());
            cTiet.setPagId(pag.getId());
            lChitiet.add(cTiet);
        }
        List<String> maDvis = lChitiet.stream().map(KhPagTongHopCTiet::getMaDvi).collect(Collectors.toList());
        Map<String, QlnvDmDonvi> listDvi = qlnvDmService.getMapDonVi(maDvis);
        lChitiet.forEach(f -> {
            f.setTenDvi(listDvi.get(f.getMaDvi()).getTenDvi());
        });
        pagTH.setPagChiTiets(lChitiet);
        return pagTH;
    }

    @Transactional(rollbackFor = Exception.class)
    public KhPagTongHop create(KhLtPagTongHopFilterReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        KhPagTongHop pagTH = this.tongHopData(req);
        pagTH.setNgayTongHop(LocalDate.now());
        pagTH.setMaDvi(userInfo.getDvql());
        pagTH.setCapDvi(userInfo.getCapDvi());
        pagTH.setTrangThaiTh(Contains.CHUATAOTOTRINH);
        pagTH.setTrangThaiTt(Contains.DUTHAO);
        pagTH.setType(req.getType());
        pagTH.setGhiChu(req.getGhiChu());
        pagTH.setMoTaHangHoa(req.getMoTaHangHoa());
        pagTH.setNoiDung(req.getNoiDung());
        pagTH.setLDonVi(String.join(",", req.getMaDvis()));
        pagTH.setNgayDxTu(Instant.ofEpochMilli(req.getNgayDxTu().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        pagTH.setNgayDxDen(Instant.ofEpochMilli(req.getNgayDxDen().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        KhPagTongHop pagThSave = khLtPagTongHopRepository.save(pagTH);
        if (pagTH.getPagChiTiets() != null && !pagTH.getPagChiTiets().isEmpty()) {
            List<KhPagTongHopCTiet> pagTGChiTiets = pagTH.getPagChiTiets().stream().map(item -> {
                KhPagTongHopCTiet pagThChiTiet = mapper.map(item, KhPagTongHopCTiet.class);
                pagThChiTiet.setPagThId(pagThSave.getId());
                return pagThChiTiet;
            }).collect(Collectors.toList());
            khLtPagTongHopCTietRepository.saveAll(pagTGChiTiets);
            pagThSave.setPagChiTiets(pagTGChiTiets);
            /**
             * update lại stt của dx pag
             */
            List<Long> pagIds = pagTH.getPagChiTiets().stream().map(KhPagTongHopCTiet::getPagId).collect(Collectors.toList());
            List<KhPhuongAnGia> pagDetails = khLtPhuongAnGiaRepository.findByIdIn(pagIds).stream().map(item -> {
                item.setTrangThaiTh(Contains.DA_TH);
                return item;
            }).collect(Collectors.toList());
            khLtPhuongAnGiaRepository.saveAll(pagDetails);
        }
        return pagThSave;
    }


    @Transactional(rollbackFor = Exception.class)
    public KhPagTongHop updateToTrinhToThPag(KhLtPagTongHopReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<KhPagTongHop> optinal = khLtPagTongHopRepository.findById(req.getId());
        if (!optinal.isPresent()) {
            throw new Exception("Không tìm thấy bản ghi tổng hợp phương án giá");
        }
        KhPagTongHop pagTH = optinal.get();
        Optional<KhPagTongHop> optionalCheckUnique = khLtPagTongHopRepository.findBySoToTrinh(req.getSoToTrinh());
        if (optionalCheckUnique.isPresent() && req.getId() != optionalCheckUnique.get().getId()) {
            throw new Exception("Số tờ trình đã tồn tại");
        }
        pagTH.setTrichYeu(req.getTrichYeu());
        pagTH.setSoToTrinh(req.getSoToTrinh());
        pagTH.setTtGiaDn(req.getTtGiaDn());
        pagTH.setTtGiaDnVat(req.getTtGiaDnVat());
        pagTH.setTtGiaTdtt(req.getTtGiaTdtt());
        pagTH.setTtGiaTdttVat(req.getTtGiaTdttVat());
        pagTH.setGhiChu(req.getGhiChu());
        pagTH.setTrangThaiTh(Contains.DATAOTOTRINH);
        pagTH.setTrangThaiTt(req.getTrangThaiTt());
        pagTH.setTtNgayKy(req.getTtNgayKy());
        pagTH.setMoTaHangHoa(req.getMoTaHangHoa());
        pagTH.setQdGtdttBtc(req.getType().equals("GCT") ? req.getQdGtdttBtc() : null);
        return khLtPagTongHopRepository.save(pagTH);
    }

    public void exportPagTH(KhLtPagTongHopSearchReq objReq, HttpServletResponse response) throws Exception {
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhPagTongHop> page = this.searchPage(objReq);
        List<KhPagTongHop> data = page.getContent();

        Map<String, String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        Map<String, String> hashMapLoaiGia = qlnvDmService.getListDanhMucChung("LOAI_GIA");

        String title = "Danh sách tổng hợp phương án giá";
        String[] rowsName = new String[]{"STT", "Mã tổng hợp", "Ngày tổng hợp", "Nội dung tổng hợp", "Năm kế hoạch", "Loại hàng hóa", "Chủng loại hàng hóa", "Loại giá", "Trạng thái tổng hợp", "Mã tờ trình", "Trạng thái"};
        String fileName = "danh-sach-tong-hop-phuong-an-gia.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < data.size(); i++) {
            KhPagTongHop dx = data.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i;
            objs[1] = dx.getId();
            objs[2] = dx.getNgayTongHop();
            objs[3] = dx.getNoiDung();
            objs[4] = dx.getNamTongHop();
            objs[5] = StringUtils.isEmpty(dx.getLoaiVthh()) ? null : hashMapHh.get(dx.getLoaiVthh());
            objs[6] = StringUtils.isEmpty(dx.getCloaiVthh()) ? null : hashMapHh.get(dx.getCloaiVthh());
            objs[7] = hashMapLoaiGia.get(dx.getLoaiGia());
            objs[8] = dx.getTenTrangThaiTh();
            objs[9] = dx.getSoToTrinh();
            objs[10] = dx.getTenTrangThaiTt();
            dataList.add(objs);
        }
        ExportExcel ex = new ExportExcel(title, fileName, rowsName, dataList, response);
        ex.export();
    }

    @Transactional
    public KhPagTongHop detailPagTH(String id) throws Exception {
        Optional<KhPagTongHop> qOptional = khLtPagTongHopRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()) {
            throw new Exception("Tổng hợp phương án giá không tồn tại");
        }
        KhPagTongHop data = qOptional.get();
        List<Long> ids = new ArrayList<>();
        ids.add(data.getId());
        List<KhPagTongHopCTiet> listPagTHChiTiets = khLtPagTongHopCTietRepository.findByPagThIdIn(ids);
        if (listPagTHChiTiets.size() > 0) {
            data.setPagChiTiets(listPagTHChiTiets);
        }
        data.setMaDvis(data.getLDonVi() != null ? new ArrayList<String>(Arrays.asList(data.getLDonVi().split(","))) : null);
        Map<String, String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        Map<String, String> hashMapLoaiGia = qlnvDmService.getListDanhMucChung("LOAI_GIA");
        data.setTenLoaiVthh(hashMapHh.get(data.getLoaiVthh()));
        data.setTenLoaiGia(hashMapLoaiGia.get(data.getLoaiGia()));
        data.setTenCloaiVthh(hashMapHh.get(data.getCloaiVthh()));
        data.setTenTrangThaiTt(Contains.getTrangThaiTt(data.getTrangThaiTt()));
        return data;
    }

    @Transactional
    public void delete(Long ids) throws Exception {
        Optional<KhPagTongHop> qOptional = khLtPagTongHopRepository.findById(ids);
        if (!qOptional.isPresent()) {
            throw new UserPrincipalNotFoundException("Id không tồn tại");
        }
        if (!StringUtils.isEmpty(qOptional.get().getSoToTrinh())) {
            throw new Exception("Tổng hợp đã ra tờ trình, không được phép xóa.");
        }
        if (qOptional.get().getTrangThaiTt().equals(Contains.DUYET)) {
            throw new Exception("Bản ghi có trạng thái đã duyệt,không được xóa.");
        }
        List<Long> pagTHIds = new ArrayList<>();
        pagTHIds.add(ids);
        List<KhPagTongHopCTiet> khPagTongHopCTiet = khLtPagTongHopCTietRepository.findByPagThIdIn(pagTHIds);
        if (!CollectionUtils.isEmpty(khPagTongHopCTiet)) {
            List<Long> dxIds = khPagTongHopCTiet.stream().map(KhPagTongHopCTiet::getPagThId).collect(Collectors.toList());
            khLtPagTongHopCTietRepository.deleteAll(khPagTongHopCTiet);
            //cập nhật lại đề xuất về trạng thái chưa tổng hợp.
            List<KhPhuongAnGia> pagDetails = khLtPhuongAnGiaRepository.findByIdIn(dxIds).stream().map(item -> {
                item.setTrangThaiTh(Contains.CHUA_TH);
                return item;
            }).collect(Collectors.toList());
            khLtPhuongAnGiaRepository.saveAll(pagDetails);
        }
        khLtPagTongHopRepository.delete(qOptional.get());
    }

    @Transactional
    public KhPagTongHop approved(StatusReq objReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (StringUtils.isEmpty(objReq.getId()))
            throw new Exception("Không tìm thấy dữ liệu");
        Optional<KhPagTongHop> opPagTH = khLtPagTongHopRepository.findById(Long.valueOf(objReq.getId()));
        if (!opPagTH.isPresent())
            throw new Exception("Không tìm thấy dữ liệu");
        String status = objReq.getTrangThai() + opPagTH.get().getTrangThaiTt();
        switch (status) {
            case Contains.CHODUYET_LDV + Contains.DUTHAO:
            case Contains.CHODUYET_LDV + Contains.TUCHOI_LDV:
                opPagTH.get().setTtNguoiGuiDuyet(userInfo.getId());
                break;
            case Contains.TUCHOI_LDV + Contains.CHODUYET_LDV:
                opPagTH.get().setTtNguoiPheDuyet(userInfo.getId());
                opPagTH.get().setTtLyDoTuChoi(objReq.getLyDoTuChoi());
                break;
            case Contains.DADUYET_LDV + Contains.CHODUYET_LDV:
                opPagTH.get().setTtNguoiPheDuyet(userInfo.getId());
                break;
            default:
                throw new Exception("Phê duyệt không thành công");
        }
        opPagTH.get().setTrangThaiTt(objReq.getTrangThai());
        KhPagTongHop khPagTongHop = khLtPagTongHopRepository.save(opPagTH.get());
        return khPagTongHop;
    }
}
