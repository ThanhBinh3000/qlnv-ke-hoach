package com.tcdt.qlnvkhoach.service.giaokehoachvonmuabu_bosung.quyetdinh.btc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.enums.GiaoKeHoachVonDauNamEnum;
import com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtcBNganhCtietRepository;
import com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtcBNganhRepository;
import com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtcRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.btc.KhMuaQdBtcBNganhCtietReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.btc.KhMuaQdBtcBNganhReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.btc.KhMuaQdBtcReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdBtcSearchReq;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtc;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtcBNganh;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.btc.KhMuaQdBtcBNganhCTiet;
import com.tcdt.qlnvkhoach.util.Contains;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KhMuaQdBtcService {
    @Autowired
    private KhMuaQdBtcBNganhCtietRepository khMuaQdBtcBNganhCtietRepository;

    @Autowired
    private KhMuaQdBtcBNganhRepository khMuaQdBtcBNganhRepository;

    @Autowired
    private KhMuaQdBtcRepository khMuaQdBtcRepository;

    @Autowired
    private QlnvDmService qlnvDmService;

    @Autowired
    private FileDinhKemService fileDinhKemService;

    public Iterable<KhMuaQdBtc> findAll() {
        return khMuaQdBtcRepository.findAll();
    }

    public Page<KhMuaQdBtc> searchPage(KhMuaQdBtcSearchReq objReq) throws Exception {
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhMuaQdBtc> data = khMuaQdBtcRepository.selectPage(
                objReq.getNamQd(),
                objReq.getSoQd(),
                Contains.convertDateToString(objReq.getNgayQdTu()),
                Contains.convertDateToString(objReq.getNgayQdDen()),
                objReq.getTrichYeu(),
                objReq.getTrangThai(),
                pageable);
        data.getContent().forEach( f -> {
                f.setTenTrangThai(GiaoKeHoachVonDauNamEnum.getTrangThaiDuyetById(f.getTrangThai()));
        });
        return data;
    }

    @Transactional
    public KhMuaQdBtc save(KhMuaQdBtcReq objReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) {
            throw new Exception("Bad request.");
        }

        Optional<KhMuaQdBtc> optional = khMuaQdBtcRepository.findBySoQd(objReq.getSoQd());
        if (optional.isPresent()) {
            throw new Exception("Số quyết định đã tồn tại");
        }
        Optional<KhMuaQdBtc> optional2 = khMuaQdBtcRepository.findByNamQd(objReq.getNamQd());
        if (optional2.isPresent()) {
            throw new Exception("Năm " + objReq.getNamQd() + " đã tồn tại quyết định");
        }

        KhMuaQdBtc data = new ModelMapper().map(objReq, KhMuaQdBtc.class);
        data.setId(null);
        data.setNgayTao(new Date());
        data.setNguoiTao(userInfo.getUsername());
        // Dự thảo
        data.setTrangThai("00");
        KhMuaQdBtc createCheck = khMuaQdBtcRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_MUA_QD_BTC");
        createCheck.setFileDinhkems(fileDinhKems);

        for (KhMuaQdBtcBNganhReq khMuaQdTtcpBNganhReq : objReq.getListBoNganh()) {
            KhMuaQdBtcBNganh khMuaQdTtcpBNganh = new ModelMapper().map(khMuaQdTtcpBNganhReq, KhMuaQdBtcBNganh.class);
            khMuaQdTtcpBNganh.setId(null);
            khMuaQdTtcpBNganh.setIdMuaQdBtc(data.getId());
            khMuaQdBtcBNganhRepository.save(khMuaQdTtcpBNganh);
            saveBNganhCtiet(khMuaQdTtcpBNganhReq, khMuaQdTtcpBNganh);
        }
        return createCheck;
    }

    public void saveBNganhCtiet(KhMuaQdBtcBNganhReq khMuaQdBtcBNganhReq, KhMuaQdBtcBNganh khMuaQdBtcBNganh) {
        for (KhMuaQdBtcBNganhCtietReq khMuaQdTtcpBNganhCtietReq : khMuaQdBtcBNganhReq.getMuaBuList()) {
            KhMuaQdBtcBNganhCTiet cTiet = new ModelMapper().map(khMuaQdTtcpBNganhCtietReq, KhMuaQdBtcBNganhCTiet.class);
            cTiet.setId(null);
            cTiet.setIdBoNganh(khMuaQdBtcBNganh.getId());
            cTiet.setType(Contains.KH_MUA_BU);
            khMuaQdBtcBNganhCtietRepository.save(cTiet);
        }
        for (KhMuaQdBtcBNganhCtietReq khMuaQdTtcpBNganhCtietReq : khMuaQdBtcBNganhReq.getMuaBsungList()) {
            KhMuaQdBtcBNganhCTiet cTiet = new ModelMapper().map(khMuaQdTtcpBNganhCtietReq, KhMuaQdBtcBNganhCTiet.class);
            cTiet.setIdBoNganh(khMuaQdBtcBNganh.getId());
            cTiet.setType(Contains.KH_MUA_BO_SUNG);
            cTiet.setId(null);
            khMuaQdBtcBNganhCtietRepository.save(cTiet);
        }
    }
    public <T> void updateObjectToObject(T source, T objectEdit) throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(Contains.FORMAT_DATE_STR));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.updateValue(source, objectEdit);
    }

    @Transactional
    public KhMuaQdBtc update(KhMuaQdBtcReq objReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<KhMuaQdBtc> qOptional = khMuaQdBtcRepository.findById(objReq.getId());
        if (!qOptional.isPresent())
            throw new UnsupportedOperationException("Id không tồn tại");

        Optional<KhMuaQdBtc> optional = khMuaQdBtcRepository.findBySoQd(objReq.getSoQd());
        if (optional.isPresent()) {
            if (!optional.get().getId().equals(objReq.getId())) {
                throw new Exception("Số quyết định " + objReq.getSoQd() + " đã tồn tại");
            }
        }

        KhMuaQdBtc data = qOptional.get();
        KhMuaQdBtc dataMap = new ModelMapper().map(objReq, KhMuaQdBtc.class);
        updateObjectToObject(data, dataMap);
        data.setNguoiSua(userInfo.getUsername());
        data.setNgaySua(new Date());
        KhMuaQdBtc createCheck = khMuaQdBtcRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_MUA_QD_BTC");
        createCheck.setFileDinhkems(fileDinhKems);

        //xoa tất cả
        khMuaQdBtcBNganhRepository.deleteAllByIdMuaQdBtc(data.getId());
        for (KhMuaQdBtcBNganhReq bn : objReq.getListBoNganh()) {
            KhMuaQdBtcBNganh bNganh = new ModelMapper().map(bn, KhMuaQdBtcBNganh.class);
            bNganh.setId(null);
            bNganh.setIdMuaQdBtc(data.getId());
            khMuaQdBtcBNganhRepository.save(bNganh);
            khMuaQdBtcBNganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
            this.saveBNganhCtiet(bn, bNganh);
        }
        return createCheck;
    }

    @Transactional
    public KhMuaQdBtc detailBtc(String id) throws Exception {
        Optional<KhMuaQdBtc> qOptional = khMuaQdBtcRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()) {
            throw new Exception("Kế hoạch quyết định Bộ Tài Chính không tồn tại");
        }
        KhMuaQdBtc data = qOptional.get();
        Map<String, String> hashMapBoNganh = qlnvDmService.getListDanhMucChung("BO_NGANH");
        Map<String, String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        List<KhMuaQdBtcBNganh> listBoNganh = khMuaQdBtcBNganhRepository.findAllByIdMuaQdBtc(data.getId());
        data.setFileDinhkems(fileDinhKemService.search(data.getId(),Collections.singleton("KH_MUA_QD_BTC")));

        for (KhMuaQdBtcBNganh boNganh : listBoNganh) {
            boNganh.setTenBoNganh(hashMapBoNganh.get(boNganh.getMaBoNganh()));
            List<KhMuaQdBtcBNganhCTiet> listCtiet = khMuaQdBtcBNganhCtietRepository.findAllByIdBoNganh(boNganh.getId());
            listCtiet.forEach(item -> {
                item.setTenCloaiVthh(hashMapHh.get(item.getCloaiVthh()));
                item.setTenVthh(hashMapHh.get(item.getLoaiVthh()));
            });
            if (listCtiet.size() > 0) {
                boNganh.setMuaBuList(listCtiet.stream().filter(item -> item.getType().equals(Contains.KH_MUA_BU)).collect(Collectors.toList()));
                boNganh.setMuaBsungList(listCtiet.stream().filter(item -> item.getType().equals(Contains.KH_MUA_BO_SUNG)).collect(Collectors.toList()));
            }
        }
        data.setListBoNganh(listBoNganh);
        return data;
    }

    @Transactional
    public void deleteBtc(Long ids) {
        Optional<KhMuaQdBtc> qOptional = khMuaQdBtcRepository.findById(ids);
        if (!qOptional.isPresent()) {
            throw new UnsupportedOperationException("Id không tồn tại");
        }

        for (KhMuaQdBtcBNganh bNganh : khMuaQdBtcBNganhRepository.findAllByIdMuaQdBtc(ids)) {
            khMuaQdBtcBNganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
        }
        khMuaQdBtcBNganhRepository.deleteAllByIdMuaQdBtc(ids);
        fileDinhKemService.delete(qOptional.get().getId(), Lists.newArrayList("KH_MUA_QD_BTC"));
        khMuaQdBtcRepository.delete(qOptional.get());

    }

    @Transactional
    public void deleteListId(List<Long> listId){
        for(Long idDel : listId){
            List<KhMuaQdBtcBNganh> boNganh = khMuaQdBtcBNganhRepository.findAllByIdMuaQdBtc(idDel);
            List<Long> listIdBnganh = boNganh.stream().map(KhMuaQdBtcBNganh::getId).collect(Collectors.toList());
            khMuaQdBtcBNganhCtietRepository.deleteAllByIdBoNganhIn(listIdBnganh);
        }
        khMuaQdBtcBNganhRepository.deleteAllByIdMuaQdBtcIn(listId);
        fileDinhKemService.deleteMultiple(listId,Lists.newArrayList("KH_MUA_QD_BTC"));
        khMuaQdBtcRepository.deleteAllByIdIn(listId);
    }

    public  void exportDsQdTtcp(KhMuaQdBtcSearchReq objReq, HttpServletResponse response) throws Exception{
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhMuaQdBtc> page=this.searchPage(objReq);
        List<KhMuaQdBtc> data=page.getContent();

        String title="Danh sách quyết định của Bộ Tài Chính";
        String[] rowsName=new String[]{"STT","Số quyết định","Năm QĐ","Ngày QĐ","Trích yếu","Trạng thái"};
        String fileName="danh-sach-quyet-dinh-btc.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs=null;
        for (int i=0;i<data.size();i++){
            KhMuaQdBtc dx=data.get(i);
            objs=new Object[rowsName.length];
            objs[0]=i;
            objs[1]=dx.getSoQd();
            objs[2]=dx.getNamQd();
            objs[3]=dx.getNgayQd();
            objs[4]=dx.getTrichYeu();
            objs[5]=dx.getTenTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }

    public KhMuaQdBtc approve(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (StringUtils.isEmpty(stReq.getId())){
            throw new Exception("Không tìm thấy dữ liệu");
        }

        Optional<KhMuaQdBtc> optional = khMuaQdBtcRepository.findById(Long.valueOf(stReq.getId()));
        if (!optional.isPresent()){
            throw new Exception("Không tìm thấy dữ liệu");
        }

        String status = stReq.getTrangThai() + optional.get().getTrangThai();
        switch (status) {
            case Contains.BAN_HANH + Contains.MOI_TAO:
                optional.get().setNguoiPduyet(userInfo.getUsername());
                optional.get().setNgayPduyet(new Date());
                break;
            default:
                throw new Exception("Phê duyệt không thành công");
        }

        optional.get().setTrangThai(stReq.getTrangThai());

        KhMuaQdBtc createCheck = khMuaQdBtcRepository.save(optional.get());

        return createCheck;
    }
}
