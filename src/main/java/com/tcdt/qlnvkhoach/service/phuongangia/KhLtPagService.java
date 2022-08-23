package com.tcdt.qlnvkhoach.service.phuongangia;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.phuongangia.*;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiEnum;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiTHEnum;
import com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum;
import com.tcdt.qlnvkhoach.repository.FileDinhKemChungRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.*;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagDiaDiemDeHangReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagKetQuaReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmVattu;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
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

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KhLtPagService extends BaseService {
    @Autowired
    private KhLtPhuongAnGiaRepository khLtPhuongAnGiaRepository;
    @Autowired
    private FileDinhKemService fileDinhKemService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private KhPagCcPhapLyRepository khPagCcPhapLyRepository;
    @Autowired
    private KhLtPagKetQuaRepository khLtPagKetQuaRepository;
    @Autowired
    private KhLtPagDiaDiemDeHangRepository khLtPagDiaDiemDeHangRepository;

    @Autowired
    private QlnvDmService qlnvDmService;
    @Autowired
    private FileDinhKemChungRepository fileDinhKemChungRepository;

    @Autowired
    private KhPagTtChungRepository khPagTtChungRepository;

    @Autowired
    private KhPagPpXacDinhGiaRepository khPagPpXacDinhGiaRepository;


    public Iterable<KhPhuongAnGia> findAll() {
        return khLtPhuongAnGiaRepository.findAll();
    }

    public Page<KhPhuongAnGia> searchPage(KhLtPhuongAnGiaSearchReq objReq) throws Exception {

        UserInfo userInfo = SecurityContextService.getUser();
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhPhuongAnGia> data = khLtPhuongAnGiaRepository.selectPage(
                objReq.getNamKh(),
                objReq.getSoDx(),
                objReq.getLoaiHh(),
                Contains.convertDateToString(objReq.getNgayKyTu()),
                Contains.convertDateToString(objReq.getNgayKyDen()),
                objReq.getTrichYeu(),
                userInfo.getCapDvi().equals("1") ? null : userInfo.getDvql(),
                objReq.getType(),
                objReq.getPagType().equals("VT") ? "02" : null,
                pageable);

        Map<String, String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        Map<String, String> hashMapLoaiGia = qlnvDmService.getListDanhMucChung("LOAI_GIA");
        try {
            data.getContent().forEach(f -> {
                f.setTenTrangThai(PAGTrangThaiEnum.getTrangThaiDuyetById(f.getTrangThai()));
                f.setTenTrangThaiTh(Contains.getThTongHop(f.getTrangThaiTh()));
                f.setTenLoaiVthh(StringUtils.isEmpty(f.getLoaiVthh()) ? null : hashMapHh.get(f.getLoaiVthh()));
                f.setTenLoaiGia(StringUtils.isEmpty(f.getLoaiGia()) ? null : hashMapLoaiGia.get(f.getLoaiGia()));
                f.setTenCloaiVthh(StringUtils.isEmpty(f.getCloaiVthh()) ? null : hashMapHh.get(f.getCloaiVthh()));
            });
            List<Long> khLtPagIds = data.getContent().stream().map(KhPhuongAnGia::getId).collect(Collectors.toList());
//        get ketqua tham dinh gia va khao sat gia
            Map<Long, List<Object[]>> khLtKetquaThamDinhs = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdsIn(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), khLtPagIds)
                    .stream().collect(Collectors.groupingBy(o -> (Long) o[8]));
            Map<Long, List<Object[]>> khLtKetquaKhaoSats = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdsIn(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), khLtPagIds)
                    .stream().collect(Collectors.groupingBy(o -> (Long) o[8]));
            Map<Long, List<Object[]>> khLtCCPhapLys = khPagCcPhapLyRepository.findByPhuongAnGiaIdsIn(khLtPagIds)
                    .stream().collect(Collectors.groupingBy(o -> (Long) o[3]));
            for (KhPhuongAnGia khPhuongAnGia : data.getContent()) {
                List<Object[]> ketquaTDs = khLtKetquaThamDinhs.get(khPhuongAnGia.getId());
                List<Object[]> ketquaKSs = khLtKetquaKhaoSats.get(khPhuongAnGia.getId());
                List<Object[]> ccPhapLys = khLtCCPhapLys.get(khPhuongAnGia.getId());
                List<KhPagKetQua> khPagKetQuaThamDinhs = new ArrayList<>();
                if (!CollectionUtils.isEmpty(ketquaTDs)) {
                    ketquaTDs.forEach(c -> {
                        khPagKetQuaThamDinhs.add(new KhPagKetQua((Long) c[0], (Long) c[1], (String) c[2], (BigDecimal) c[3], (BigDecimal) c[4], (String) c[5], (String) c[6], (String) c[7], null, (String) c[9], (Long) c[8]));
                    });
                }
                List<KhPagKetQua> khPagKetQuaKhaoSats = new ArrayList<>();
                if (!CollectionUtils.isEmpty(ketquaKSs)) {
                    ketquaKSs.forEach(c -> {
                        khPagKetQuaKhaoSats.add(new KhPagKetQua((Long) c[0], (Long) c[1], (String) c[2], (BigDecimal) c[3], (BigDecimal) c[4], (String) c[5], (String) c[6], (String) c[7], null, (String) c[9], (Long) c[8]));
                    });
                }
                List<KhPagCcPhapLy> khPagCcPhapLIES = new ArrayList<>();
                if (!CollectionUtils.isEmpty(ccPhapLys)) {
                    ccPhapLys.forEach(c -> {
                        khPagCcPhapLIES.add(new KhPagCcPhapLy((Long) c[0], (Long) c[1], (String) c[2], null, (Long) c[3]));
                    });
                }
                khPhuongAnGia.setKetQuaThamDinhGia(khPagKetQuaThamDinhs);
                khPhuongAnGia.setKetQuaKhaoSatGiaThiTruong(khPagKetQuaKhaoSats);
                khPhuongAnGia.setCanCuPhapLy(khPagCcPhapLIES);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    public KhLtPhuongAnGiaRes create(KhLtPhuongAnGiaReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        log.info("Save: thông tin phương án giá");
        if (khLtPhuongAnGiaRepository.findBySoDeXuat(req.getSoDeXuat()).isPresent()) {
            throw new Exception("Số đề xuất đã tồn tại trong hệ thống!");
        }
        KhPhuongAnGia phuongAnGia = mapper.map(req, KhPhuongAnGia.class);
        phuongAnGia.setTrangThai(PAGTrangThaiEnum.DU_THAO.getId());
        phuongAnGia.setTrangThaiTh(Contains.CHUA_TH);
        phuongAnGia.setMaDvi(userInfo.getDvql());
        phuongAnGia.setCapDvi(userInfo.getCapDvi());
        phuongAnGia.setNguoiTaoId(userInfo.getId());
        phuongAnGia.setNgayTao(LocalDateTime.now());

        phuongAnGia = khLtPhuongAnGiaRepository.save(phuongAnGia);
        log.info("Save: Căn cứ, phương pháp xác định giá: Căn cứ pháp lý");
        KhPhuongAnGia finalPhuongAnGia = phuongAnGia;
        List<FileDinhKemChung> fileCcPags = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKems(), finalPhuongAnGia.getId(), KhPhuongAnGia.TABLE_NAME);
        phuongAnGia.setListFileCCs(fileCcPags);
        List<KhPagCcPhapLy> canCuPhapLyList = req.getCanCuPhapLy().stream().map(item -> {
            KhPagCcPhapLy canCuPhapLy = mapper.map(item, KhPagCcPhapLy.class);
            canCuPhapLy.setPhuongAnGiaId(finalPhuongAnGia.getId());
            log.info("Save file đính kèm");
            canCuPhapLy = khPagCcPhapLyRepository.save(canCuPhapLy);
            List<FileDinhKemReq> listFile = new ArrayList<>();
            listFile.add(item.getFileDinhKem());
            FileDinhKemChung fileDinhKems = fileDinhKemService.saveListFileDinhKem(listFile, canCuPhapLy.getId(), KhPagCcPhapLy.TABLE_NAME).get(0);
            canCuPhapLy.setFileDinhKem(fileDinhKems);
            return canCuPhapLy;
        }).collect(Collectors.toList());
        phuongAnGia.setCanCuPhapLy(canCuPhapLyList);

        log.info("Save thông tin khảo sát giá");
        log.info("Save kết quả khảo sát giá thị trường");
        List<KhPagKetQua> ketQuaKhaoSatGiaThiTruong = this.saveKetQua(req.getKetQuaKhaoSatGiaThiTruong(), PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGia.getId());
        phuongAnGia.setKetQuaKhaoSatGiaThiTruong(ketQuaKhaoSatGiaThiTruong);
        log.info("Save kết quả thẩm định giá");
        List<KhPagKetQua> ketQuaThamDinhGia = this.saveKetQua(req.getKetQuaThamDinhGia(), PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGia.getId());
        phuongAnGia.setKetQuaThamDinhGia(ketQuaThamDinhGia);
        log.info("Save thông tin giá của hàng hóa tương tự");
        List<KhPagKetQua> thongTinGiaHangHoaTuongTu = this.saveKetQua(req.getThongTinGiaHangHoaTuongTu(), PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGia.getId());
        phuongAnGia.setThongTinGiaHangHoaTuongTu(thongTinGiaHangHoaTuongTu);
        log.info("Save: địa điểm để hàng");
        List<KhPagDiaDiemDeHang> diaDiemDeHangs = this.saveDDDehang(req.getDiaDiemDeHangs(), phuongAnGia.getId());
        phuongAnGia.setDiaDiemDeHangs(diaDiemDeHangs);
        //Lưu thông tin chung của Vật tư
        if (req.getLoaiVthh().startsWith("02")) {
            List<KhPagTtChung> pagTtChungs = this.savePagTtChung(req.getPagTtChungs(), phuongAnGia.getId());
            phuongAnGia.setPagTtChungs(pagTtChungs);
            List<KhPagPpXacDinhGia> pagPpXacDinhGias = this.savePagPpXacDinhGia(req.getPagPpXacDinhGias(), phuongAnGia.getId());
            phuongAnGia.setPagPpXacDinhGias(pagPpXacDinhGias);
        }
        log.info("Build phương án giá response");
        KhLtPhuongAnGiaRes phuongAnGiaRes = mapper.map(phuongAnGia, KhLtPhuongAnGiaRes.class);
        return phuongAnGiaRes;
    }

    private List<KhPagKetQua> saveKetQua(List<KhLtPagKetQuaReq> reqs, String type, Long phuongAnGiaId) {
        List<KhPagKetQua> ketQuaList = reqs.stream().map(item -> {
            KhPagKetQua ketQua = mapper.map(item, KhPagKetQua.class);
            ketQua.setPhuongAnGiaId(phuongAnGiaId);
            ketQua.setType(type);
            ketQua = khLtPagKetQuaRepository.save(ketQua);
            List<FileDinhKemReq> listFile = new ArrayList<>();
            listFile.add(item.getFileDinhKem());
            List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(listFile, ketQua.getId(), KhPagKetQua.TABLE_NAME);
            ketQua.setFileDinhKem(fileDinhKems.get(0));
            return ketQua;
        }).collect(Collectors.toList());
        return ketQuaList;
    }

    private List<KhPagDiaDiemDeHang> saveDDDehang(List<KhLtPagDiaDiemDeHangReq> reqs, Long phuongAnGiaId) {
        List<KhPagDiaDiemDeHang> ddDehangs = reqs.stream().map(item -> {
            KhPagDiaDiemDeHang diadiemDehang = mapper.map(item, KhPagDiaDiemDeHang.class);
            diadiemDehang.setPagId(phuongAnGiaId);
            diadiemDehang = khLtPagDiaDiemDeHangRepository.save(diadiemDehang);
            return diadiemDehang;
        }).collect(Collectors.toList());
        return ddDehangs;
    }

    private List<KhPagTtChung> savePagTtChung(List<KhPagTtChung> reqs, Long phuongAnGiaId) {
        List<KhPagTtChung> ttChungs = reqs.stream().map(item -> {
            KhPagTtChung ttchung = mapper.map(item, KhPagTtChung.class);
            ttchung.setPhuongAnGiaId(phuongAnGiaId);
            ttchung = khPagTtChungRepository.save(ttchung);
            return ttchung;
        }).collect(Collectors.toList());
        return ttChungs;
    }

    private List<KhPagPpXacDinhGia> savePagPpXacDinhGia(List<KhPagPpXacDinhGia> reqs, Long phuongAnGiaId) {
        List<KhPagPpXacDinhGia> ppXacDinhGias = reqs.stream().map(item -> {
            KhPagPpXacDinhGia ppXacDinh = mapper.map(item, KhPagPpXacDinhGia.class);
            ppXacDinh.setPhuongAnGiaId(phuongAnGiaId);
            ppXacDinh = khPagPpXacDinhGiaRepository.save(ppXacDinh);
            return ppXacDinh;
        }).collect(Collectors.toList());
        return ppXacDinhGias;
    }

    @Transactional(rollbackFor = Exception.class)
    public KhLtPhuongAnGiaRes update(KhLtPhuongAnGiaReq req) throws Exception {
        if (req == null) return null;
        if (req.getId() == null) {
            throw new Exception("Bad request");
        }
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        Optional<KhPhuongAnGia> optional = khLtPhuongAnGiaRepository.findById(req.getId());
        if (!optional.isPresent()) throw new Exception("Đề xuất phương án giá không tồn tại");
        Optional<KhPhuongAnGia> optionalCheckUnique = khLtPhuongAnGiaRepository.findBySoDeXuat(req.getSoDeXuat());
        if (optionalCheckUnique.isPresent() && req.getId() != optionalCheckUnique.get().getId()) {
            throw new Exception("Số đề xuất đã tồn tại");
        }
        KhPhuongAnGia phuongAnGia = optional.get();
        BeanUtils.copyProperties(req, phuongAnGia, "id");
        phuongAnGia.setMaDvi(userInfo.getDvql());
        /**
         * Xóa cc pháp lý, file đính kèm , ket qua để insert lại
         */
        List<Long> pagIds = new ArrayList<>();
        pagIds.add(phuongAnGia.getId());
        this.deleteChildOfDxPag(pagIds);
        // Xóa file đính kem`
        fileDinhKemService.deleteMultiple(pagIds, Collections.singleton(KhPhuongAnGia.TABLE_NAME));
        log.info("Save: Căn cứ, phương pháp xác định giá: Căn cứ pháp lý");
        KhPhuongAnGia finalPhuongAnGia = phuongAnGia;
        List<KhPagCcPhapLy> canCuPhapLyList = req.getCanCuPhapLy().stream().map(item -> {
            KhPagCcPhapLy canCuPhapLy = mapper.map(item, KhPagCcPhapLy.class);
            canCuPhapLy.setPhuongAnGiaId(finalPhuongAnGia.getId());
            canCuPhapLy = khPagCcPhapLyRepository.save(canCuPhapLy);
            log.info("Save file đính kèm");
            List<FileDinhKemReq> listFile = new ArrayList<>();
            listFile.add(item.getFileDinhKem());
            FileDinhKemChung fileDinhKem = fileDinhKemService.saveListFileDinhKem(listFile, canCuPhapLy.getId(), KhPagCcPhapLy.TABLE_NAME).get(0);
            canCuPhapLy.setFileDinhKem(fileDinhKem);
            return canCuPhapLy;
        }).collect(Collectors.toList());
        phuongAnGia.setCanCuPhapLy(canCuPhapLyList);
        phuongAnGia = khLtPhuongAnGiaRepository.save(phuongAnGia);
        List<FileDinhKemChung> fileCcPags = fileDinhKemService.saveListFileDinhKem(req.getFileDinhKems(), finalPhuongAnGia.getId(), KhPhuongAnGia.TABLE_NAME);
        phuongAnGia.setListFileCCs(fileCcPags);
        log.info("Update kết quả khảo sát giá thị trường");
        List<KhPagKetQua> ketQuaKhaoSatGiaThiTruong = this.saveKetQua(req.getKetQuaKhaoSatGiaThiTruong(), PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGia.getId());
        phuongAnGia.setKetQuaKhaoSatGiaThiTruong(ketQuaKhaoSatGiaThiTruong);
        log.info("Update kết quả thẩm định giá");
        List<KhPagKetQua> ketQuaThamDinhGia = this.saveKetQua(req.getKetQuaThamDinhGia(), PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGia.getId());
        phuongAnGia.setKetQuaThamDinhGia(ketQuaThamDinhGia);
        log.info("Update thông tin giá của hàng hóa tương tự");
        List<KhPagKetQua> thongTinGiaHangHoaTuongTu = this.saveKetQua(req.getThongTinGiaHangHoaTuongTu(), PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGia.getId());
        phuongAnGia.setThongTinGiaHangHoaTuongTu(thongTinGiaHangHoaTuongTu);
        log.info("Save: địa điểm để hàng");
        List<KhPagDiaDiemDeHang> diaDiemDeHangs = this.saveDDDehang(req.getDiaDiemDeHangs(), phuongAnGia.getId());
        phuongAnGia.setDiaDiemDeHangs(diaDiemDeHangs);
        //Lưu thông tin chung của Vật tư
        if (req.getLoaiVthh().startsWith("02")) {
            List<KhPagTtChung> pagTtChungs = this.savePagTtChung(req.getPagTtChungs(), phuongAnGia.getId());
            phuongAnGia.setPagTtChungs(pagTtChungs);
            List<KhPagPpXacDinhGia> pagPpXacDinhGias = this.savePagPpXacDinhGia(req.getPagPpXacDinhGias(), phuongAnGia.getId());
            phuongAnGia.setPagPpXacDinhGias(pagPpXacDinhGias);
        }
        log.info("Build phương án giá response");
        KhLtPhuongAnGiaRes phuongAnGiaRes = mapper.map(phuongAnGia, KhLtPhuongAnGiaRes.class);
        return phuongAnGiaRes;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMultiple(List<Long> ids) throws Exception {
        if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        List<KhPhuongAnGia> phuongAnGiaList = khLtPhuongAnGiaRepository.findByIdIn(ids);
        List<Long> phuongAnGiaIds = phuongAnGiaList.stream().map(KhPhuongAnGia::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(phuongAnGiaList)) throw new Exception("Bad request.");
        log.info("Xóa căn cứ pháp lý, kết quả và file đính kèm");
        deleteChildOfDxPag(phuongAnGiaIds);
        log.info("Xóa file đính  kèm của phương án giá");
        fileDinhKemService.deleteMultiple(phuongAnGiaIds, Collections.singleton(KhPhuongAnGia.TABLE_NAME));
        khLtPhuongAnGiaRepository.deleteAll(phuongAnGiaList);
        return true;
    }

    private void deleteChildOfDxPag(List<Long> pagIds) {
        log.info("Xóa căn cứ pháp lý và file đính kèm");
        List<KhPagCcPhapLy> khPagCcPhapLyList = khPagCcPhapLyRepository.findByPhuongAnGiaIdIn(pagIds);
        if (!CollectionUtils.isEmpty(khPagCcPhapLyList)) {
            List<Long> canCuPhapLyIds = khPagCcPhapLyList.stream().map(KhPagCcPhapLy::getId).collect(Collectors.toList());
            fileDinhKemService.deleteMultiple(canCuPhapLyIds, Collections.singleton(KhPagCcPhapLy.TABLE_NAME));
            khPagCcPhapLyRepository.deleteAll(khPagCcPhapLyList);
        }
        log.info("Xóa kết quả");
        List<KhPagKetQua> allKetQuas = khLtPagKetQuaRepository.findByPhuongAnGiaIdIn(pagIds);
        if (!CollectionUtils.isEmpty(allKetQuas)) {
            List<Long> pagKetquaIds = allKetQuas.stream().map(KhPagKetQua::getId).collect(Collectors.toList());
            fileDinhKemService.deleteMultiple(pagKetquaIds, Collections.singleton(KhPagKetQua.TABLE_NAME));
            khLtPagKetQuaRepository.deleteAll(allKetQuas);
        }
        /**
         * Xóa thông tin chung khi là vật tư
         */
        List<KhPagTtChung> allTtChung = khPagTtChungRepository.findByPhuongAnGiaIdIn(pagIds);
        if (!CollectionUtils.isEmpty(allTtChung)) {
            khPagTtChungRepository.deleteAll(allTtChung);
        }
        /**
         * Xóa pp xác định giá khi là vật tư
         */
        List<KhPagPpXacDinhGia> allPpXacDinhGias = khPagPpXacDinhGiaRepository.findByPhuongAnGiaIdIn(pagIds);
        if (!CollectionUtils.isEmpty(allPpXacDinhGias)) {
            khPagPpXacDinhGiaRepository.deleteAll(allPpXacDinhGias);
        }
    }


    private void deleteKetQua(String type, List<Long> phuongAnGiaIds) {
        List<KhPagKetQua> ketQuaList = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(type, phuongAnGiaIds);
        if (CollectionUtils.isEmpty(ketQuaList)) return;
        List<Long> ketQuaIds = ketQuaList.stream().map(KhPagKetQua::getId).collect(Collectors.toList());
        fileDinhKemService.deleteMultiple(ketQuaIds, Collections.singleton(KhPagKetQua.getFileDinhKemDataType(type)));
        khLtPagKetQuaRepository.deleteAll(ketQuaList);
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(Long ids) throws Exception {
        Optional<KhPhuongAnGia> qOptional = khLtPhuongAnGiaRepository.findById(ids);
        if (!qOptional.isPresent()) {
            throw new UserPrincipalNotFoundException("Id không tồn tại");
        }
        List<Long> pagIds = new ArrayList<>();
        pagIds.add(ids);
        deleteChildOfDxPag(pagIds);
//        List<KhPagCcPhapLy> khPagCcPhapLyList = khPagCcPhapLyRepository.findByPhuongAnGiaIdIn(pagIds);
//        if (!CollectionUtils.isEmpty(khPagCcPhapLyList)) {
//            List<Long> canCuPhapLyIds = khPagCcPhapLyList.stream().map(KhPagCcPhapLy::getId).collect(Collectors.toList());
//            fileDinhKemService.deleteMultiple(canCuPhapLyIds, Collections.singleton(KhPagCcPhapLy.TABLE_NAME));
//            khPagCcPhapLyRepository.deleteAll(khPagCcPhapLyList);
//        }
//        log.info("Xóa kết quả");
//        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), pagIds);
//        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), pagIds);
//        this.deleteKetQua(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), pagIds);
        fileDinhKemService.delete(qOptional.get().getId(), Lists.newArrayList("KH_QD_BTC_TCDT"));
        khLtPhuongAnGiaRepository.delete(qOptional.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public KhPhuongAnGia approved(StatusReq objReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (StringUtils.isEmpty(objReq.getId()))
            throw new Exception("Không tìm thấy dữ liệu");
        Optional<KhPhuongAnGia> opPag = khLtPhuongAnGiaRepository.findById(Long.valueOf(objReq.getId()));
        if (!opPag.isPresent())
            throw new Exception("Không tìm thấy dữ liệu");
        String status = objReq.getTrangThai() + opPag.get().getTrangThai();
        switch (status) {
            case Contains.CHODUYET_TP + Contains.DUTHAO:
            case Contains.CHODUYET_TP + Contains.TUCHOI_TP:
            case Contains.CHODUYET_LDC + Contains.CHODUYET_TP:
            case Contains.DADUYET_LDC + Contains.CHODUYET_LDC:
            case Contains.CHODUYET_TP + Contains.TUCHOI_LDC:
                opPag.get().setNguoiGuiDuyet(userInfo.getId());
                break;
            case Contains.TUCHOI_TP + Contains.CHODUYET_TP:
            case Contains.TUCHOI_LDC + Contains.CHODUYET_LDC:
                opPag.get().setNguoiPheDuyet(userInfo.getId());
                opPag.get().setLyDoTuChoi(objReq.getLyDoTuChoi());
                break;
            default:
                throw new Exception("Phê duyệt không thành công");
        }
        opPag.get().setTrangThai(objReq.getTrangThai());
        KhPhuongAnGia khPhuongAnGia = khLtPhuongAnGiaRepository.save(opPag.get());
        khPhuongAnGia.setTenTrangThai(PAGTrangThaiEnum.getTrangThaiDuyetById(khPhuongAnGia.getTrangThai()));
        return khPhuongAnGia;
    }

    @Transactional(rollbackFor = Exception.class)
    public KhPhuongAnGia detailDxPag(String id) throws Exception {
        Optional<KhPhuongAnGia> qOptional = khLtPhuongAnGiaRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()) {
            throw new Exception("Đề xuất phương án giá không tồn tại");
        }
        KhPhuongAnGia data = qOptional.get();
        List<Long> ids = new ArrayList<>();
        ids.add(data.getId());
        Collection dataType = new ArrayList();
        dataType.add(KhPhuongAnGia.TABLE_NAME);
        List<FileDinhKemChung> fileDinhKems = fileDinhKemChungRepository.findByDataIdAndDataTypeIn(data.getId(), dataType);
//        Map<String, String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        List<KhPagDiaDiemDeHang> diaDiemDeHangs = khLtPagDiaDiemDeHangRepository.findByPagIdIn(ids);
        List<KhPagCcPhapLy> listPagCCPhapLy = khPagCcPhapLyRepository.findByPhuongAnGiaIdIn(ids);
        //Thông tin chung,can cu xac dinh gia (loại Vật tư)
        List<KhPagTtChung> listPagTtChungs = khPagTtChungRepository.findByPhuongAnGiaIdIn(ids);
        data.setPagTtChungs(listPagTtChungs);
        List<KhPagPpXacDinhGia> listPpXacDinhGia = khPagPpXacDinhGiaRepository.findByPhuongAnGiaIdIn(ids);
        data.setPagPpXacDinhGias(listPpXacDinhGia);
        List<KhPagKetQua> listPagKetQuaTD = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), ids);
        List<KhPagKetQua> listPagKetQuaKSTT = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), ids);
        List<KhPagKetQua> listPagKetQuaTTHHTT = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), ids);
        List<Long> khCcPhapLyIds = listPagCCPhapLy.stream().map(KhPagCcPhapLy::getId).collect(Collectors.toList());
        List<Long> listKqIds = khLtPagKetQuaRepository.findByPhuongAnGiaIdIn(ids).stream().map(KhPagKetQua::getId).collect(Collectors.toList());
        Map<Long, List<FileDinhKemChung>> mapListFileForKqs = fileDinhKemChungRepository.findByDataIdInAndDataType(listKqIds, KhPagKetQua.TABLE_NAME)
                .stream().collect(Collectors.groupingBy(o -> o.getDataId()));
        if (listPagCCPhapLy.size() > 0) {
            Map<Long, List<FileDinhKemChung>> mapListFile = fileDinhKemChungRepository.findByDataIdInAndDataType(khCcPhapLyIds, KhPagCcPhapLy.TABLE_NAME)
                    .stream().collect(Collectors.groupingBy(o -> o.getDataId()));
            listPagCCPhapLy.forEach(f -> {
                f.setFileDinhKem(mapListFile.size() > 0 && mapListFile.get(f.getId()).size() > 0 ? mapListFile.get(f.getId()).get(0) : null);
            });
            data.setCanCuPhapLy(listPagCCPhapLy);
        }
        if (listPagKetQuaTD.size() > 0) {
            listPagKetQuaTD.forEach(f -> {
                f.setFileDinhKem(mapListFileForKqs.size() > 0 && mapListFileForKqs.get(f.getId()).size() > 0 ? mapListFileForKqs.get(f.getId()).get(0) : null);
            });
            data.setKetQuaThamDinhGia(listPagKetQuaTD);
        }
        if (listPagKetQuaKSTT.size() > 0) {
            listPagKetQuaKSTT.forEach(f -> {
                f.setFileDinhKem(mapListFileForKqs.size() > 0 && mapListFileForKqs.get(f.getId()).size() > 0 ? mapListFileForKqs.get(f.getId()).get(0) : null);
            });
            data.setKetQuaKhaoSatGiaThiTruong(listPagKetQuaKSTT);
        }
        if (listPagKetQuaTTHHTT.size() > 0) {
            listPagKetQuaTTHHTT.forEach(f -> {
                f.setFileDinhKem(mapListFileForKqs.size() > 0 && mapListFileForKqs.get(f.getId()).size() > 0 ? mapListFileForKqs.get(f.getId()).get(0) : null);
            });
            data.setThongTinGiaHangHoaTuongTu(listPagKetQuaTTHHTT);
        }
        if (diaDiemDeHangs.size() > 0) {
            data.setDiaDiemDeHangs(diaDiemDeHangs);
        }
        if (fileDinhKems.size() > 0) {
            data.setListFileCCs(fileDinhKems);
        }
        data.setTenTrangThai(PAGTrangThaiEnum.getTrangThaiDuyetById(data.getTrangThai()));
        return data;
    }

    public void exportDxPag(KhLtPhuongAnGiaSearchReq objReq, HttpServletResponse response) throws Exception {
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhPhuongAnGia> page = this.searchPage(objReq);
        List<KhPhuongAnGia> data = page.getContent();
        String title = "Danh sách đề xuất phương án giá";
        String[] rowsName = new String[]{"STT", "Số đề xuất", "Ngày ký", "Trích yếu", "Năm kế hoạch", "Loại hàng hóa", "Loại giá", "Trạng thái"};
        String fileName = "danh-sach-de-xuat-phuong-an-gia.xlsx";
        if (objReq.getPagType().equals("VT")) {
            rowsName = new String[]{"STT", "Số đề xuất", "Ngày ký", "Trích yếu", "Quyết định giao chỉ tiêu kế hoạch", "Năm kế hoạch", "Loại hàng hóa", "Loại giá", "Trạng thái"};
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        if (objReq.getPagType().equals("VT")) {
            for (int i = 0; i < data.size(); i++) {
                KhPhuongAnGia dx = data.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;
                objs[1] = dx.getSoDeXuat();
                objs[2] = dx.getNgayKy();
                objs[3] = dx.getTrichYeu();
                objs[4] = dx.getQdCtKhNam();
                objs[5] = dx.getNamKeHoach();
                objs[6] = dx.getTenLoaiVthh();
                objs[7] = dx.getTenLoaiGia();
                objs[8] = dx.getTenTrangThai();
                dataList.add(objs);
            }
        } else {
            for (int i = 0; i < data.size(); i++) {
                KhPhuongAnGia dx = data.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;
                objs[1] = dx.getSoDeXuat();
                objs[2] = dx.getNgayKy();
                objs[3] = dx.getTrichYeu();
                objs[4] = dx.getNamKeHoach();
                objs[5] = dx.getTenLoaiVthh();
                objs[6] = dx.getTenLoaiGia();
                objs[7] = dx.getTenTrangThai();
                dataList.add(objs);
            }
        }
        ExportExcel ex = new ExportExcel(title, fileName, rowsName, dataList, response);
        ex.export();
    }

}
