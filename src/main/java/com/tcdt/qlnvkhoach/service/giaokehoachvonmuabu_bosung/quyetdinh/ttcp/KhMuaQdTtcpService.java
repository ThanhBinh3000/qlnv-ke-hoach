package com.tcdt.qlnvkhoach.service.giaokehoachvonmuabu_bosung.quyetdinh.ttcp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.enums.GiaoKeHoachVonDauNamEnum;
import com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcpBNganhCtietRepository;
import com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcpBNganhRepository;
import com.tcdt.qlnvkhoach.repository.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcpRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpBNganhCtietReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpBNganhReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvonmuabu_bosung.quyetdinh.ttcp.KhMuaQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcp;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcpBNganh;
import com.tcdt.qlnvkhoach.table.giaoKeHoachVonMuaBu_BoSung.quyetDinh.ttcp.KhMuaQdTtcpBNganhCTiet;
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
public class KhMuaQdTtcpService {
    @Autowired
    private KhMuaQdTtcpRepository khMuaQdTtcpRepository;

    @Autowired
    private KhMuaQdTtcpBNganhRepository khMuaQdTtcpBNganhRepository;

    @Autowired
    private KhMuaQdTtcpBNganhCtietRepository khMuaQdTtcpBNganhCtietRepository;

    @Autowired
    private QlnvDmService qlnvDmService;

    @Autowired
    private FileDinhKemService fileDinhKemService;


    public Iterable<KhMuaQdTtcp> findAll() {
        return khMuaQdTtcpRepository.findAll();
    }

    public Page<KhMuaQdTtcp> searchPage(KhMuaQdTtcpSearchReq objReq) throws Exception {
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhMuaQdTtcp> data = khMuaQdTtcpRepository.selectPage(
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
    public KhMuaQdTtcp save(KhMuaQdTtcpReq objReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) {
            throw new Exception("Bad request.");
        }

        Optional<KhMuaQdTtcp> optional = khMuaQdTtcpRepository.findBySoQd(objReq.getSoQd());
        if (optional.isPresent()) {
            throw new Exception("Số quyết định đã tồn tại");
        }
        Optional<KhMuaQdTtcp> optional2 = khMuaQdTtcpRepository.findByNamQd(objReq.getNamQd());
        if (optional2.isPresent()) {
            throw new Exception("Năm " + objReq.getNamQd() + " đã tồn tại quyết định");
        }

        KhMuaQdTtcp data = new ModelMapper().map(objReq, KhMuaQdTtcp.class);
        data.setId(null);
        data.setNgayTao(new Date());
        data.setNguoiTao(userInfo.getUsername());
        // Dự thảo
        data.setTrangThai("00");
        KhMuaQdTtcp createCheck = khMuaQdTtcpRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_MUA_QD_TTCP");
        createCheck.setFileDinhkems(fileDinhKems);

        for (KhMuaQdTtcpBNganhReq khMuaQdTtcpBNganhReq : objReq.getListBoNganh()) {
            KhMuaQdTtcpBNganh khMuaQdTtcpBNganh = new ModelMapper().map(khMuaQdTtcpBNganhReq, KhMuaQdTtcpBNganh.class);
            khMuaQdTtcpBNganh.setId(null);
            khMuaQdTtcpBNganh.setIdMuaQdTtcp(data.getId());
            khMuaQdTtcpBNganhRepository.save(khMuaQdTtcpBNganh);
            saveBNganhCtiet(khMuaQdTtcpBNganhReq, khMuaQdTtcpBNganh);
        }
        return createCheck;
    }

    public void saveBNganhCtiet(KhMuaQdTtcpBNganhReq muaQdTtcpBNganhReq, KhMuaQdTtcpBNganh khMuaQdTtcp) {
        for (KhMuaQdTtcpBNganhCtietReq khMuaQdTtcpBNganhCtietReq : muaQdTtcpBNganhReq.getMuaBuList()) {
            KhMuaQdTtcpBNganhCTiet cTiet = new ModelMapper().map(khMuaQdTtcpBNganhCtietReq, KhMuaQdTtcpBNganhCTiet.class);
            cTiet.setId(null);
            cTiet.setIdBoNganh(khMuaQdTtcp.getId());
            cTiet.setType(Contains.KH_MUA_BU);
            khMuaQdTtcpBNganhCtietRepository.save(cTiet);
        }
        for (KhMuaQdTtcpBNganhCtietReq khMuaQdTtcpBNganhCtietReq : muaQdTtcpBNganhReq.getMuaBsungList()) {
            KhMuaQdTtcpBNganhCTiet cTiet = new ModelMapper().map(khMuaQdTtcpBNganhCtietReq, KhMuaQdTtcpBNganhCTiet.class);
            cTiet.setIdBoNganh(khMuaQdTtcp.getId());
            cTiet.setType(Contains.KH_MUA_BO_SUNG);
            cTiet.setId(null);
            khMuaQdTtcpBNganhCtietRepository.save(cTiet);
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
    public KhMuaQdTtcp update(KhMuaQdTtcpReq objReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<KhMuaQdTtcp> qOptional = khMuaQdTtcpRepository.findById(objReq.getId());
        if (!qOptional.isPresent())
            throw new UnsupportedOperationException("Id không tồn tại");

        Optional<KhMuaQdTtcp> optional = khMuaQdTtcpRepository.findBySoQd(objReq.getSoQd());
        if (optional.isPresent()) {
            if (!optional.get().getId().equals(objReq.getId())) {
                throw new Exception("Số quyết định " + objReq.getSoQd() + " đã tồn tại");
            }
        }

        KhMuaQdTtcp data = qOptional.get();
        KhMuaQdTtcp dataMap = new ModelMapper().map(objReq, KhMuaQdTtcp.class);
        updateObjectToObject(data, dataMap);
        data.setNguoiSua(userInfo.getUsername());
        data.setNgaySua(new Date());
        KhMuaQdTtcp createCheck = khMuaQdTtcpRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_MUA_QD_TTCP");
        createCheck.setFileDinhkems(fileDinhKems);

        //xoa tất cả
        khMuaQdTtcpBNganhRepository.deleteAllByIdMuaQdTtcp(data.getId());
        for (KhMuaQdTtcpBNganhReq bn : objReq.getListBoNganh()) {
            KhMuaQdTtcpBNganh bNganh = new ModelMapper().map(bn, KhMuaQdTtcpBNganh.class);
            bNganh.setId(null);
            bNganh.setIdMuaQdTtcp(data.getId());
            khMuaQdTtcpBNganhRepository.save(bNganh);
            khMuaQdTtcpBNganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
            this.saveBNganhCtiet(bn, bNganh);
        }
        return createCheck;
    }

    @Transactional
    public KhMuaQdTtcp detailTtcp(String id) throws Exception {
        Optional<KhMuaQdTtcp> qOptional = khMuaQdTtcpRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()) {
            throw new Exception("Kế hoạch quyết định Thủ tướng Chính phủ không tồn tại");
        }
        KhMuaQdTtcp data = qOptional.get();
        Map<String, String> hashMapBoNganh = qlnvDmService.getListDanhMucChung("BO_NGANH");
        Map<String, String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        List<KhMuaQdTtcpBNganh> listBoNganh = khMuaQdTtcpBNganhRepository.findAllByIdMuaQdTtcp(data.getId());
        data.setFileDinhkems(fileDinhKemService.search(data.getId(),Collections.singleton("KH_MUA_QD_TTCP")));

        for (KhMuaQdTtcpBNganh boNganh : listBoNganh) {
            boNganh.setTenBoNganh(hashMapBoNganh.get(boNganh.getMaBoNganh()));
            List<KhMuaQdTtcpBNganhCTiet> listCtiet = khMuaQdTtcpBNganhCtietRepository.findAllByIdBoNganh(boNganh.getId());
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
    public void deleteTtcp(Long ids) {
        Optional<KhMuaQdTtcp> qOptional = khMuaQdTtcpRepository.findById(ids);
        if (!qOptional.isPresent()) {
            throw new UnsupportedOperationException("Id không tồn tại");
        }

        for (KhMuaQdTtcpBNganh bNganh : khMuaQdTtcpBNganhRepository.findAllByIdMuaQdTtcp(ids)) {
            khMuaQdTtcpBNganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
        }
        khMuaQdTtcpBNganhRepository.deleteAllByIdMuaQdTtcp(ids);
        fileDinhKemService.delete(qOptional.get().getId(), Lists.newArrayList("KH_MUA_QD_TTCP"));
        khMuaQdTtcpRepository.delete(qOptional.get());

    }

    @Transactional
    public void deleteListId(List<Long> listId){
        for(Long idDel : listId){
            List<KhMuaQdTtcpBNganh> boNganh = khMuaQdTtcpBNganhRepository.findAllByIdMuaQdTtcp(idDel);
            List<Long> listIdBnganh = boNganh.stream().map(KhMuaQdTtcpBNganh::getId).collect(Collectors.toList());
            khMuaQdTtcpBNganhCtietRepository.deleteAllByIdBoNganhIn(listIdBnganh);
        }
        khMuaQdTtcpBNganhRepository.deleteAllByIdMuaQdTtcpIn(listId);
        fileDinhKemService.deleteMultiple(listId,Lists.newArrayList("KH_MUA_QD_TTCP"));
        khMuaQdTtcpRepository.deleteAllByIdIn(listId);
    }

    public  void exportDsQdTtcp(KhMuaQdTtcpSearchReq objReq, HttpServletResponse response) throws Exception{
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhMuaQdTtcp> page=this.searchPage(objReq);
        List<KhMuaQdTtcp> data=page.getContent();

        String title="Danh sách quyết định của Thủ tướng chính phủ";
        String[] rowsName=new String[]{"STT","Số quyết định","Năm QĐ","Ngày QĐ","Trích yếu","Trạng thái"};
        String fileName="danh-sach-quyet-dinh-thu-tuong.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs=null;
        for (int i=0;i<data.size();i++){
            KhMuaQdTtcp dx=data.get(i);
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

    public KhMuaQdTtcp approve(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (StringUtils.isEmpty(stReq.getId())){
            throw new Exception("Không tìm thấy dữ liệu");
        }

        Optional<KhMuaQdTtcp> optional = khMuaQdTtcpRepository.findById(Long.valueOf(stReq.getId()));
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

        KhMuaQdTtcp createCheck = khMuaQdTtcpRepository.save(optional.get());

        return createCheck;
    }


}
