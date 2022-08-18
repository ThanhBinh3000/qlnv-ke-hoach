package com.tcdt.qlnvkhoach.service.phuongangia;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagCcPhapLy;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagDiaDiemDeHang;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagKetQua;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPhuongAnGia;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiEnum;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiTHEnum;
import com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhPagCcPhapLyRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagDiaDiemDeHangRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagKetQuaRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPhuongAnGiaRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagDiaDiemDeHangReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagKetQuaReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.util.Contains;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KhLtPagService {
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


    public Iterable<KhPhuongAnGia> findAll() {
        return khLtPhuongAnGiaRepository.findAll();
    }

    public Page<KhPhuongAnGia> searchPage(KhLtPhuongAnGiaSearchReq objReq) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        Pageable pageable= PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhPhuongAnGia> data =khLtPhuongAnGiaRepository.selectPage(
                objReq.getNamKh(),
                objReq.getSoDx(),
                objReq.getLoaiHh(),
                Contains.convertDateToString(objReq.getNgayKyTu()),
                Contains.convertDateToString(objReq.getNgayKyDen()),
                objReq.getTrichYeu(),
                userInfo.getCapDvi().equals("1") ? null : userInfo.getDvql(),
                pageable);
        data.getContent().forEach( f -> {
            f.setTenTrangThai(PAGTrangThaiEnum.getTrangThaiDuyetById(f.getTrangThai()));
            f.setTenTrangThaiTh(Contains.getThTongHop(f.getTrangThaiTh()));
        });
        List<Long> khLtPagIds = data.getContent().stream().map(KhPhuongAnGia::getId).collect(Collectors.toList());
//        get ketqua tham dinh gia va khao sat gia
        Map<Long, List<Object[]>> khLtKetquaThamDinhs = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdsIn(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(),khLtPagIds)
                .stream().collect(Collectors.groupingBy(o -> (Long) o[8]));
        Map<Long, List<Object[]>> khLtKetquaKhaoSats = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdsIn(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(),khLtPagIds)
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
                    khPagKetQuaThamDinhs.add(new KhPagKetQua((Long) c[0], (Long) c[1], (String) c[2], (BigDecimal) c[3],(BigDecimal) c[4], (String) c[5], (String) c[6], (String) c[7], null, (String) c[9], (Long) c[8]));
                });
            }
            List<KhPagKetQua> khPagKetQuaKhaoSats = new ArrayList<>();
            if (!CollectionUtils.isEmpty(ketquaKSs)) {
                ketquaKSs.forEach(c -> {
                    khPagKetQuaKhaoSats.add(new KhPagKetQua((Long) c[0], (Long) c[1], (String) c[2], (BigDecimal) c[3],(BigDecimal) c[4], (String) c[5], (String) c[6], (String) c[7], null, (String) c[9], (Long) c[8]));
                });
            }
            List<KhPagCcPhapLy> khPagCcPhapLIES = new ArrayList<>();
            if (!CollectionUtils.isEmpty(ccPhapLys)) {
                ccPhapLys.forEach(c -> {
                    khPagCcPhapLIES.add(new KhPagCcPhapLy((Long)c[0], (Long) c[1],(String) c[2],null,(Long) c[3]));
                });
            }
            khPhuongAnGia.setKetQuaThamDinhGia(khPagKetQuaThamDinhs);
            khPhuongAnGia.setKetQuaKhaoSatGiaThiTruong(khPagKetQuaKhaoSats);
            khPhuongAnGia.setCanCuPhapLy(khPagCcPhapLIES);
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    public KhLtPhuongAnGiaRes create(KhLtPhuongAnGiaReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        log.info("Save: thông tin phương án giá");
        KhPhuongAnGia phuongAnGia =  mapper.map(req, KhPhuongAnGia.class);
        phuongAnGia.setTrangThai(PAGTrangThaiEnum.DU_THAO.getId());
        phuongAnGia.setTrangThaiTh(PAGTrangThaiTHEnum.CHUA_TH.getId());
        phuongAnGia.setMaDvi(userInfo.getDvql());
        phuongAnGia.setCapDvi(userInfo.getCapDvi());
        phuongAnGia.setNguoiTaoId(userInfo.getId());
        phuongAnGia.setNgayTao(LocalDate.now());

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
            List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), canCuPhapLy.getId(), KhPagCcPhapLy.TABLE_NAME);
            canCuPhapLy.setFileDinhKems(fileDinhKems);
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
        List<KhPagDiaDiemDeHang> diaDiemDeHangs = this.saveDDDehang(req.getDiaDiemDeHang(), phuongAnGia.getId());
        phuongAnGia.setDiaDiemDeHangs(diaDiemDeHangs);
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
            List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), ketQua.getId(), KhPagKetQua.getFileDinhKemDataType(type));
            ketQua.setFileDinhKems(fileDinhKems);
            return ketQua;
        }).collect(Collectors.toList());
//        ketQuaList = khLtPagKetQuaRepository.saveAll(ketQuaList);
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


    @Transactional(rollbackFor = Exception.class)
    public KhLtPhuongAnGiaRes update(KhLtPhuongAnGiaReq req) throws Exception {
        if (req == null) return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        Optional<KhPhuongAnGia> optional = khLtPhuongAnGiaRepository.findById(req.getId());
        if (!optional.isPresent()) throw new Exception("Đề xuất phương án giá không tồn tại");

        KhPhuongAnGia phuongAnGia = optional.get();

        phuongAnGia = mapper.map(req, KhPhuongAnGia.class);
        phuongAnGia.setNguoiSuaId(userInfo.getId());
        phuongAnGia.setNgaySua(LocalDate.now());

        log.info("Save: Căn cứ, phương pháp xác định giá: Căn cứ pháp lý");
        KhPhuongAnGia finalPhuongAnGia = phuongAnGia;

        List<KhPagCcPhapLy> canCuPhapLyList = req.getCanCuPhapLy().stream().map(item -> {
            KhPagCcPhapLy canCuPhapLy = mapper.map(item, KhPagCcPhapLy.class);
            canCuPhapLy.setPhuongAnGiaId(finalPhuongAnGia.getId());
            canCuPhapLy = khPagCcPhapLyRepository.save(canCuPhapLy);
            log.info("Save file đính kèm");
            List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), canCuPhapLy.getId(), KhPagCcPhapLy.TABLE_NAME);
            canCuPhapLy.setFileDinhKems(fileDinhKems);
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
        List<KhPagDiaDiemDeHang> diaDiemDeHangs = this.saveDDDehang(req.getDiaDiemDeHang(), phuongAnGia.getId());
        phuongAnGia.setDiaDiemDeHangs(diaDiemDeHangs);
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

        log.info("Xóa căn cứ pháp lý và file đính kèm");
        List<KhPagCcPhapLy> khPagCcPhapLyList = khPagCcPhapLyRepository.findByPhuongAnGiaIdIn(phuongAnGiaIds);

        if (!CollectionUtils.isEmpty(khPagCcPhapLyList)) {
            List<Long> canCuPhapLyIds = khPagCcPhapLyList.stream().map(KhPagCcPhapLy::getId).collect(Collectors.toList());
            fileDinhKemService.deleteMultiple(canCuPhapLyIds, Collections.singleton(KhPagCcPhapLy.TABLE_NAME));
            khPagCcPhapLyRepository.deleteAll(khPagCcPhapLyList);
        }
        log.info("Xóa kết quả");
        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGiaIds);
        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGiaIds);
        this.deleteKetQua(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGiaIds);

        khLtPhuongAnGiaRepository.deleteAll(phuongAnGiaList);

        return true;
    }

    private void deleteKetQua(String type, List<Long> phuongAnGiaIds) {
        List<KhPagKetQua> ketQuaList = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(type, phuongAnGiaIds);
        if (CollectionUtils.isEmpty(ketQuaList)) return;
        List<Long> ketQuaIds = ketQuaList.stream().map(KhPagKetQua::getId).collect(Collectors.toList());
        fileDinhKemService.deleteMultiple(ketQuaIds, Collections.singleton(KhPagKetQua.getFileDinhKemDataType(type)));
        khLtPagKetQuaRepository.deleteAll(ketQuaList);
    }


    @javax.transaction.Transactional
    public void delete(Long ids) throws Exception{
        Optional<KhPhuongAnGia> qOptional=khLtPhuongAnGiaRepository.findById(ids);
        if(!qOptional.isPresent()){
            throw new UserPrincipalNotFoundException("Id không tồn tại");
        }
        List<Long> pagIds = new ArrayList<>();
        pagIds.add(ids);
        List<KhPagCcPhapLy> khPagCcPhapLyList = khPagCcPhapLyRepository.findByPhuongAnGiaIdIn(pagIds);
        if (!CollectionUtils.isEmpty(khPagCcPhapLyList)) {
            List<Long> canCuPhapLyIds = khPagCcPhapLyList.stream().map(KhPagCcPhapLy::getId).collect(Collectors.toList());
            fileDinhKemService.deleteMultiple(canCuPhapLyIds, Collections.singleton(KhPagCcPhapLy.TABLE_NAME));
            khPagCcPhapLyRepository.deleteAll(khPagCcPhapLyList);
        }
        log.info("Xóa kết quả");
        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), pagIds);
        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), pagIds);
        this.deleteKetQua(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), pagIds);
        fileDinhKemService.delete(qOptional.get().getId(), Lists.newArrayList("KH_QD_BTC_TCDT"));
        khLtPhuongAnGiaRepository.delete(qOptional.get());
    }

    @javax.transaction.Transactional
    public KhPhuongAnGia detailDxPag(String id) throws  Exception {
        Optional<KhPhuongAnGia> qOptional = khLtPhuongAnGiaRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()) {
            throw new Exception("Đề xuất phương án giá không tồn tại");
        }
        KhPhuongAnGia data = qOptional.get();
        List<Long> ids = new ArrayList<>();
        ids.add(data.getId());
        List<KhPagCcPhapLy> listPagCCPhapLy = khPagCcPhapLyRepository.findByPhuongAnGiaIdIn(ids);
        List<KhPagKetQua> listPagKetQuaTD = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(),ids);
        List<KhPagKetQua> listPagKetQuaKSTT = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(),ids);
        List<KhPagKetQua> listPagKetQuaTTHHTT = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(),ids);
        if(listPagCCPhapLy.size() > 0){
            data.setCanCuPhapLy(listPagCCPhapLy);
        }
        if(listPagKetQuaTD.size() > 0){
            data.setKetQuaThamDinhGia(listPagKetQuaTD);
        }
        if(listPagKetQuaKSTT.size() > 0){
            data.setKetQuaKhaoSatGiaThiTruong(listPagKetQuaKSTT);
        }
        if(listPagKetQuaTTHHTT.size() > 0){
            data.setThongTinGiaHangHoaTuongTu(listPagKetQuaTTHHTT);
        }
        return data;

    }

    public  void exportDxPag(KhLtPhuongAnGiaSearchReq objReq, HttpServletResponse response) throws Exception{
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhPhuongAnGia> page=this.searchPage(objReq);
        List<KhPhuongAnGia> data=page.getContent();

        String title="Danh sách đề xuất phương án giá";
        String[] rowsName=new String[]{"STT","Số đề xuất","Ngày ký","Trích yếu","Năm kế hoạch","Loại hàng hóa","Loại giá","Trạng thái"};
        String fileName="danh-sach-de-xuat-phuong-an-gia.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs=null;
        for (int i=0;i<data.size();i++){
            KhPhuongAnGia dx=data.get(i);
            objs=new Object[rowsName.length];
            objs[0]=i;
            objs[1]=dx.getSoDeXuat();
            objs[2]=dx.getNgayKy();
            objs[3]=dx.getTrichYeu();
            objs[4]=dx.getNamKeHoach();
            objs[5]=dx.getLoaiVthh();
            objs[6]=dx.getLoaiGia();
            objs[7]=dx.getTenTrangThai();
            dataList.add(objs);
        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }
}
