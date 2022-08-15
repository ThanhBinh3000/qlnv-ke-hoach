package com.tcdt.qlnvkhoach.service.phuongangia;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagCcPhapLy;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagKetQua;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiEnum;
import com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum;
import com.tcdt.qlnvkhoach.enums.TrangThaiEnum;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagCcPhapLyRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagKetQuaRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPhuongAnGiaRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagKetQuaReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPhuongAnGiaReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachdaunam.KhQdBtBoNganhSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuDeXuatResponse;
import com.tcdt.qlnvkhoach.response.chitieukehoachnam.ChiTieuKeHoachNamRes;
import com.tcdt.qlnvkhoach.response.phuongangia.KhLtPhuongAnGiaRes;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.btcgiaocacbonganh.KhQdBtcBoNganh;
import com.tcdt.qlnvkhoach.table.btcgiaocacbonganh.KhQdBtcBoNganhCtiet;
import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdt;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
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
    private  ModelMapper mapper;
    @Autowired
    private  KhLtPagCcPhapLyRepository khLtPagCcPhapLyRepository;
    @Autowired
    private KhLtPagKetQuaRepository khLtPagKetQuaRepository;


    public Iterable<KhLtPhuongAnGia> findAll() {
        return khLtPhuongAnGiaRepository.findAll();
    }

    public Page<KhLtPhuongAnGia> searchPage(KhLtPhuongAnGiaSearchReq objReq) throws Exception{
        Pageable pageable= PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhLtPhuongAnGia> data =khLtPhuongAnGiaRepository.selectPage(
                objReq.getNamKh(),
                objReq.getSoDx(),
                objReq.getLoaiHh(),
                Contains.convertDateToString(objReq.getNgayKyTu()),
                Contains.convertDateToString(objReq.getNgayKyDen()),
                objReq.getTrichYeu(),
                pageable);
        data.getContent().forEach( f -> {
            f.setTenTrangThai(PAGTrangThaiEnum.getTrangThaiDuyetById(f.getTrangThai()));
        });
        List<Long> khLtPagIds = data.getContent().stream().map(KhLtPhuongAnGia::getId).collect(Collectors.toList());
//        get ketqua tham dinh gia va khao sat gia
        Map<Long, List<Object[]>> khLtKetquaThamDinhs = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdsIn(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(),khLtPagIds)
                .stream().collect(Collectors.groupingBy(o -> (Long) o[4]));
        Map<Long, List<Object[]>> khLtKetquaKhaoSats = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdsIn(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(),khLtPagIds)
                .stream().collect(Collectors.groupingBy(o -> (Long) o[4]));
        Map<Long, List<Object[]>> khLtCCPhapLys = khLtPagCcPhapLyRepository.findByPhuongAnGiaIdsIn(khLtPagIds)
                .stream().collect(Collectors.groupingBy(o -> (Long) o[3]));
        for (KhLtPhuongAnGia khLtPhuongAnGia : data.getContent()) {
            List<Object[]> ketquaTDs = khLtKetquaThamDinhs.get(khLtPhuongAnGia.getId());
            List<Object[]> ketquaKSs = khLtKetquaKhaoSats.get(khLtPhuongAnGia.getId());
            List<Object[]> ccPhapLys = khLtCCPhapLys.get(khLtPhuongAnGia.getId());
            List<KhLtPagKetQua> khLtPagKetQuaThamDinhs = new ArrayList<>();
            if (!CollectionUtils.isEmpty(ketquaTDs)) {
                ketquaTDs.forEach(c -> {
                    khLtPagKetQuaThamDinhs.add(new KhLtPagKetQua((Long) c[0], (Long) c[1], (String) c[2], (BigDecimal) c[3],(BigDecimal) c[4], (String) c[5], (String) c[6], (String) c[7], null, (String) c[9], (Long) c[8]));
                });
            }
            List<KhLtPagKetQua> khLtPagKetQuaKhaoSats = new ArrayList<>();
            if (!CollectionUtils.isEmpty(ketquaKSs)) {
                ketquaKSs.forEach(c -> {
                    khLtPagKetQuaKhaoSats.add(new KhLtPagKetQua((Long) c[0], (Long) c[1], (String) c[2], (BigDecimal) c[3],(BigDecimal) c[4], (String) c[5], (String) c[6], (String) c[7], null, (String) c[9], (Long) c[8]));
                });
            }
            List<KhLtPagCcPhapLy> khLtPagCcPhapLys = new ArrayList<>();
            if (!CollectionUtils.isEmpty(ccPhapLys)) {
                ccPhapLys.forEach(c -> {
                    khLtPagCcPhapLys.add(new KhLtPagCcPhapLy((Long)c[0], (Long) c[1],(String) c[2],null,(Long) c[3]));
                });
            }
            khLtPhuongAnGia.setKetQuaThamDinhGia(khLtPagKetQuaThamDinhs);
            khLtPhuongAnGia.setKetQuaKhaoSatGiaThiTruong(khLtPagKetQuaKhaoSats);
            khLtPhuongAnGia.setCanCuPhapLy(khLtPagCcPhapLys);
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    public KhLtPhuongAnGiaRes create(KhLtPhuongAnGiaReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();

        if (userInfo == null) throw new Exception("Bad request.");

        log.info("Save: thông tin phương án giá");
        KhLtPhuongAnGia phuongAnGia =  mapper.map(req, KhLtPhuongAnGia.class);
        phuongAnGia.setTrangThai(PAGTrangThaiEnum.DU_THAO.getId());
        phuongAnGia.setMaDonVi(userInfo.getDvql());
        phuongAnGia.setCapDvi(userInfo.getCapDvi());
        phuongAnGia.setNguoiTaoId(userInfo.getId());
        phuongAnGia.setNgayTao(LocalDate.now());

        phuongAnGia = khLtPhuongAnGiaRepository.save(phuongAnGia);

        log.info("Save: Căn cứ, phương pháp xác định giá: Căn cứ pháp lý");
        KhLtPhuongAnGia finalPhuongAnGia = phuongAnGia;

        List<KhLtPagCcPhapLy> canCuPhapLyList = req.getCanCuPhapLy().stream().map(item -> {
            KhLtPagCcPhapLy canCuPhapLy = mapper.map(item, KhLtPagCcPhapLy.class);
            canCuPhapLy.setPhuongAnGiaId(finalPhuongAnGia.getId());
            log.info("Save file đính kèm");
            List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), canCuPhapLy.getId(), KhLtPagCcPhapLy.TABLE_NAME);
            canCuPhapLy.setFileDinhKems(fileDinhKems);
            return canCuPhapLy;
        }).collect(Collectors.toList());

        phuongAnGia.setCanCuPhapLy(canCuPhapLyList);


        log.info("Save thông tin khảo sát giá");
        log.info("Save kết quả khảo sát giá thị trường");
        List<KhLtPagKetQua> ketQuaKhaoSatGiaThiTruong = this.saveKetQua(req.getKetQuaKhaoSatGiaThiTruong(), PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGia.getId());
        phuongAnGia.setKetQuaKhaoSatGiaThiTruong(ketQuaKhaoSatGiaThiTruong);
        log.info("Save kết quả thẩm định giá");
        List<KhLtPagKetQua> ketQuaThamDinhGia = this.saveKetQua(req.getKetQuaThamDinhGia(), PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGia.getId());
        phuongAnGia.setKetQuaThamDinhGia(ketQuaThamDinhGia);
        log.info("Save thông tin giá của hàng hóa tương tự");
        List<KhLtPagKetQua> thongTinGiaHangHoaTuongTu = this.saveKetQua(req.getThongTinGiaHangHoaTuongTu(), PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGia.getId());
        phuongAnGia.setThongTinGiaHangHoaTuongTu(thongTinGiaHangHoaTuongTu);

        log.info("Build phương án giá response");

        KhLtPhuongAnGiaRes phuongAnGiaRes = mapper.map(phuongAnGia, KhLtPhuongAnGiaRes.class);

        return phuongAnGiaRes;
    }

    private List<KhLtPagKetQua> saveKetQua(List<KhLtPagKetQuaReq> reqs, String type, Long phuongAnGiaId) {
        List<KhLtPagKetQua> ketQuaList = reqs.stream().map(item -> {
            KhLtPagKetQua ketQua = mapper.map(item, KhLtPagKetQua.class);
            ketQua.setPhuongAnGiaId(phuongAnGiaId);
            ketQua = khLtPagKetQuaRepository.save(ketQua);
            ketQua.setType(type);

            List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), ketQua.getId(), KhLtPagKetQua.getFileDinhKemDataType(type));
            ketQua.setFileDinhKems(fileDinhKems);
            return ketQua;
        }).collect(Collectors.toList());
        ketQuaList = khLtPagKetQuaRepository.saveAll(ketQuaList);
        return ketQuaList;
    }


    @Transactional(rollbackFor = Exception.class)
    public KhLtPhuongAnGiaRes update(KhLtPhuongAnGiaReq req) throws Exception {
        if (req == null) return null;

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        Optional<KhLtPhuongAnGia> optional = khLtPhuongAnGiaRepository.findById(req.getId());
        if (!optional.isPresent()) throw new Exception("Đề xuất phương án giá không tồn tại");

        KhLtPhuongAnGia phuongAnGia = optional.get();

        phuongAnGia = mapper.map(req, KhLtPhuongAnGia.class);
        phuongAnGia.setNguoiSuaId(userInfo.getId());
        phuongAnGia.setNgaySua(LocalDate.now());

        log.info("Save: Căn cứ, phương pháp xác định giá: Căn cứ pháp lý");
        KhLtPhuongAnGia finalPhuongAnGia = phuongAnGia;

        List<KhLtPagCcPhapLy> canCuPhapLyList = req.getCanCuPhapLy().stream().map(item -> {
            KhLtPagCcPhapLy canCuPhapLy = mapper.map(item, KhLtPagCcPhapLy.class);

            canCuPhapLy.setPhuongAnGiaId(finalPhuongAnGia.getId());

            canCuPhapLy = khLtPagCcPhapLyRepository.save(canCuPhapLy);
            log.info("Save file đính kèm");
            List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(item.getFileDinhKems(), canCuPhapLy.getId(), KhLtPagCcPhapLy.TABLE_NAME);
            canCuPhapLy.setFileDinhKems(fileDinhKems);
            return canCuPhapLy;
        }).collect(Collectors.toList());

        phuongAnGia.setCanCuPhapLy(canCuPhapLyList);
        phuongAnGia = khLtPhuongAnGiaRepository.save(phuongAnGia);

        log.info("Update kết quả khảo sát giá thị trường");
        List<KhLtPagKetQua> ketQuaKhaoSatGiaThiTruong = this.saveKetQua(req.getKetQuaKhaoSatGiaThiTruong(), PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGia.getId());
        phuongAnGia.setKetQuaKhaoSatGiaThiTruong(ketQuaKhaoSatGiaThiTruong);
        log.info("Update kết quả thẩm định giá");
        List<KhLtPagKetQua> ketQuaThamDinhGia = this.saveKetQua(req.getKetQuaThamDinhGia(), PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGia.getId());
        phuongAnGia.setKetQuaThamDinhGia(ketQuaThamDinhGia);
        log.info("Update thông tin giá của hàng hóa tương tự");
        List<KhLtPagKetQua> thongTinGiaHangHoaTuongTu = this.saveKetQua(req.getThongTinGiaHangHoaTuongTu(), PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGia.getId());
        phuongAnGia.setThongTinGiaHangHoaTuongTu(thongTinGiaHangHoaTuongTu);

        log.info("Build phương án giá response");
        KhLtPhuongAnGiaRes phuongAnGiaRes = mapper.map(phuongAnGia, KhLtPhuongAnGiaRes.class);

        return phuongAnGiaRes;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMultiple(List<Long> ids) throws Exception {
        if (CollectionUtils.isEmpty(ids)) throw new Exception("Bad request.");

        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");

        List<KhLtPhuongAnGia> phuongAnGiaList = khLtPhuongAnGiaRepository.findByIdIn(ids);

        List<Long> phuongAnGiaIds = phuongAnGiaList.stream().map(KhLtPhuongAnGia::getId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(phuongAnGiaList)) throw new Exception("Bad request.");

        log.info("Xóa căn cứ pháp lý và file đính kèm");
        List<KhLtPagCcPhapLy> khLtPagCcPhapLyList = khLtPagCcPhapLyRepository.findByPhuongAnGiaIdIn(phuongAnGiaIds);

        if (!CollectionUtils.isEmpty(khLtPagCcPhapLyList)) {
            List<Long> canCuPhapLyIds = khLtPagCcPhapLyList.stream().map(KhLtPagCcPhapLy::getId).collect(Collectors.toList());
            fileDinhKemService.deleteMultiple(canCuPhapLyIds, Collections.singleton(KhLtPagCcPhapLy.TABLE_NAME));
            khLtPagCcPhapLyRepository.deleteAll(khLtPagCcPhapLyList);
        }
        log.info("Xóa kết quả");
        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), phuongAnGiaIds);
        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), phuongAnGiaIds);
        this.deleteKetQua(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), phuongAnGiaIds);

        khLtPhuongAnGiaRepository.deleteAll(phuongAnGiaList);

        return true;
    }

    private void deleteKetQua(String type, List<Long> phuongAnGiaIds) {
        List<KhLtPagKetQua> ketQuaList = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(type, phuongAnGiaIds);
        if (CollectionUtils.isEmpty(ketQuaList)) return;
        List<Long> ketQuaIds = ketQuaList.stream().map(KhLtPagKetQua::getId).collect(Collectors.toList());
        fileDinhKemService.deleteMultiple(ketQuaIds, Collections.singleton(KhLtPagKetQua.getFileDinhKemDataType(type)));
        khLtPagKetQuaRepository.deleteAll(ketQuaList);
    }


    @javax.transaction.Transactional
    public void delete(Long ids) throws Exception{
        Optional<KhLtPhuongAnGia> qOptional=khLtPhuongAnGiaRepository.findById(ids);
        if(!qOptional.isPresent()){
            throw new UserPrincipalNotFoundException("Id không tồn tại");
        }
        List<Long> pagIds = new ArrayList<>();
        pagIds.add(ids);
        List<KhLtPagCcPhapLy> khLtPagCcPhapLyList = khLtPagCcPhapLyRepository.findByPhuongAnGiaIdIn(pagIds);
        if (!CollectionUtils.isEmpty(khLtPagCcPhapLyList)) {
            List<Long> canCuPhapLyIds = khLtPagCcPhapLyList.stream().map(KhLtPagCcPhapLy::getId).collect(Collectors.toList());
            fileDinhKemService.deleteMultiple(canCuPhapLyIds, Collections.singleton(KhLtPagCcPhapLy.TABLE_NAME));
            khLtPagCcPhapLyRepository.deleteAll(khLtPagCcPhapLyList);
        }
        log.info("Xóa kết quả");
        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), pagIds);
        this.deleteKetQua(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), pagIds);
        this.deleteKetQua(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(), pagIds);
        fileDinhKemService.delete(qOptional.get().getId(), Lists.newArrayList("KH_QD_BTC_TCDT"));
        khLtPhuongAnGiaRepository.delete(qOptional.get());
    }

    @javax.transaction.Transactional
    public KhLtPhuongAnGia detailDxPag(String id) throws  Exception {
        Optional<KhLtPhuongAnGia> qOptional = khLtPhuongAnGiaRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()) {
            throw new Exception("Đề xuất phương án giá không tồn tại");
        }
        KhLtPhuongAnGia data = qOptional.get();
        List<Long> ids = new ArrayList<>();
        ids.add(data.getId());
        List<KhLtPagCcPhapLy> listPagCCPhapLy = khLtPagCcPhapLyRepository.findByPhuongAnGiaIdIn(ids);
        List<KhLtPagKetQua> listPagKetQuaTD = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(),ids);
        List<KhLtPagKetQua> listPagKetQuaKSTT = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(),ids);
        List<KhLtPagKetQua> listPagKetQuaTTHHTT = khLtPagKetQuaRepository.findByTypeAndPhuongAnGiaIdIn(PhuongAnGiaEnum.THONG_TIN_GIA_CUA_HANG_HOA_TUONG_TU.getValue(),ids);
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
        Page<KhLtPhuongAnGia> page=this.searchPage(objReq);
        List<KhLtPhuongAnGia> data=page.getContent();

        String title="Danh sách đề xuất phương án giá";
        String[] rowsName=new String[]{"STT","Số đề xuất","Ngày ký","Trích yếu","Năm kế hoạch","Loại hàng hóa","Loại giá","Trạng thái"};
        String fileName="danh-sach-de-xuat-phuong-an-gia.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs=null;
        for (int i=0;i<data.size();i++){
            KhLtPhuongAnGia dx=data.get(i);
            objs=new Object[rowsName.length];
            objs[0]=i;
            objs[1]=dx.getSoDeXuat();
            objs[2]=dx.getNgayKy();
            objs[3]=dx.getTrichYeu();
            objs[4]=dx.getNamKeHoach();
            objs[5]=dx.getLoaiHangHoa();
            objs[6]=dx.getLoaiGia();
            objs[7]=dx.getTenTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }
}
