package com.tcdt.qlnvkhoach.service.giaokehoachvondaunam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.enums.GiaoKeHoachVonDauNamEnum;
import com.tcdt.qlnvkhoach.repository.kehoachquyetdinhbotaichinhbonganh.KhQdBtcBoNganhCtietRepository;
import com.tcdt.qlnvkhoach.repository.kehoachquyetdinhbotaichinhbonganh.KhQdBtcBoNganhRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.KhQdBtcBoNganhCtietReq;
import com.tcdt.qlnvkhoach.request.object.chitieukehoachnam.KhQdBtcBoNganhReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachdaunam.KhQdBtBoNganhSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.btcgiaocacbonganh.KhQdBtcBoNganh;
import com.tcdt.qlnvkhoach.table.btcgiaocacbonganh.KhQdBtcBoNganhCtiet;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KhQdBtcBoNganhService extends BaseService {
    @Autowired
    private KhQdBtcBoNganhRepository khQdBtcBoNganhRepository;

    @Autowired
    private KhQdBtcBoNganhCtietRepository khQdBtcBoNganhCtietRepository;

    @Autowired
    private FileDinhKemService fileDinhKemService;

    @Autowired
    private QlnvDmService qlnvDmService;

    public Iterable<KhQdBtcBoNganh> findAll() {
        return khQdBtcBoNganhRepository.findAll();
    }

    public Page<KhQdBtcBoNganh> searchPage(KhQdBtBoNganhSearchReq objReq) throws Exception{
        Pageable pageable= PageRequest.of(objReq.getPaggingReq().getPage(),objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhQdBtcBoNganh> data=khQdBtcBoNganhRepository.selectPage(
                objReq.getNamQd(),
                objReq.getSoQd(),
                Contains.convertDateToString(objReq.getNgayQdTu()),
                Contains.convertDateToString(objReq.getNgayQdDen()),
                objReq.getTrichYeu(),
                objReq.getTrangThai(),
                pageable);
        Map<String, String> hasMApTenBnganh = qlnvDmService.getListDanhMucChung("BO_NGANH");
        data.getContent().forEach( f -> {
                f.setTenTrangThai(GiaoKeHoachVonDauNamEnum.getTentById(f.getTrangThai()));
                f.setTenBoNganh(StringUtils.isEmpty(f.getIdTtcpBoNganh()) ? null : hasMApTenBnganh.get(f.getIdTtcpBoNganh()));
        });
        return data;
    }

    @Transactional
    public KhQdBtcBoNganh save (KhQdBtcBoNganhReq objReq, HttpServletRequest req) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw  new Exception("Bad request.");
        Optional<KhQdBtcBoNganh> namQd= khQdBtcBoNganhRepository.findByNamQd(objReq.getNamQd());
        if(namQd.isPresent()){
            throw new Exception("Năm "+objReq.getNamQd()+" đã tồn tại quyết định");
        }
        Optional<KhQdBtcBoNganh> soQd= khQdBtcBoNganhRepository.findBySoQd(objReq.getSoQd());
        if (soQd.isPresent()){
            throw new Exception("số quyết định đã tồn tại");
        }
        KhQdBtcBoNganh data = new ModelMapper().map(objReq,KhQdBtcBoNganh.class);
        data.setNgayTao(new Date());
        data.setNguoiTao(userInfo.getUsername());
        data.setTrangThai("00");
        KhQdBtcBoNganh createCheck = khQdBtcBoNganhRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_QD_BTC_BO_NGANH");
        createCheck.setFileDinhkems(fileDinhKems);

        this.saveCtiet(objReq,data);
//        for(KhQdBtcBoNganhCtietRes bNganhReq : objReq.getListBoNganh()){
//            KhQdBtcBoNganhCtiet cTiet = new ModelMapper().map(bNganhReq,KhQdBtcBoNganhCtiet.class);
//            cTiet.setIdQdBtcNganh(data.getId());
//            khQdBtcBoNganhCtietRepository.save(cTiet);
//        }
        return createCheck;
    }

    public void saveCtiet(KhQdBtcBoNganhReq btcBoNganhReq,KhQdBtcBoNganh boNganhSave){
        for (KhQdBtcBoNganhCtietReq ctietReq :btcBoNganhReq.getMuaTangList()){
          KhQdBtcBoNganhCtiet cTiet=new ModelMapper().map(ctietReq,KhQdBtcBoNganhCtiet.class);
          cTiet.setId(null);
          cTiet.setIdQdBtcNganh(boNganhSave.getId());
          cTiet.setType(Contains.KH_MUA_TANG);
          khQdBtcBoNganhCtietRepository.save(cTiet);
        }

        for (KhQdBtcBoNganhCtietReq ctietReq :btcBoNganhReq.getXuatGiamList()){
            KhQdBtcBoNganhCtiet cTiet=new ModelMapper().map(ctietReq,KhQdBtcBoNganhCtiet.class);
            cTiet.setId(null);
            cTiet.setIdQdBtcNganh(boNganhSave.getId());
            cTiet.setType(Contains.KH_XUAT_GIAM);
            khQdBtcBoNganhCtietRepository.save(cTiet);
        }

        for (KhQdBtcBoNganhCtietReq ctietReq :btcBoNganhReq.getXuatBanList()){
            KhQdBtcBoNganhCtiet cTiet=new ModelMapper().map(ctietReq,KhQdBtcBoNganhCtiet.class);
            cTiet.setId(null);
            cTiet.setIdQdBtcNganh(boNganhSave.getId());
            cTiet.setType(Contains.KH_XUAT_BAN);
            khQdBtcBoNganhCtietRepository.save(cTiet);
        }

        for (KhQdBtcBoNganhCtietReq ctietReq :btcBoNganhReq.getLuanPhienList()){
            KhQdBtcBoNganhCtiet cTiet=new ModelMapper().map(ctietReq,KhQdBtcBoNganhCtiet.class);
            cTiet.setId(null);
            cTiet.setIdQdBtcNganh(boNganhSave.getId());
            cTiet.setType(Contains.KH_LUAN_PHIEN_DOI_HANG);
            khQdBtcBoNganhCtietRepository.save(cTiet);
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
    public KhQdBtcBoNganh update(@Valid KhQdBtcBoNganhReq objReq) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<KhQdBtcBoNganh> qOptional=khQdBtcBoNganhRepository.findById(objReq.getId());
        if (!qOptional.isPresent())
            throw new UnsupportedOperationException("Id không tồn tại");
        Optional<KhQdBtcBoNganh> soQd= khQdBtcBoNganhRepository.findBySoQd(objReq.getSoQd());
        if (soQd.isPresent()){
            if(!soQd.get().getId().equals(objReq.getId())){
                throw new Exception("Số quyết định " + objReq.getSoQd() + " đã tồn tại");
            }
        }
        KhQdBtcBoNganh data=qOptional.get();
        KhQdBtcBoNganh dataMap=new ModelMapper().map(objReq,KhQdBtcBoNganh.class);
        updateObjectToObject(data,dataMap);
        data.setNguoiSua(userInfo.getUsername());
        data.setNgaySua(new Date());
        KhQdBtcBoNganh createCheck=khQdBtcBoNganhRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_QD_BTC_BO_NGANH");
        createCheck.setFileDinhkems(fileDinhKems);

        khQdBtcBoNganhCtietRepository.deleteAllByIdQdBtcNganh(data.getId());
        this.saveCtiet(objReq,data);
//        for (KhQdBtcBoNganhCtietReq bnCtiet : objReq.getListBoNganh()){
//            KhQdBtcBoNganhCtiet cTiet = new ModelMapper().map(bnCtiet,KhQdBtcBoNganhCtiet.class);
//            cTiet.setId(null);
//            cTiet.setIdQdBtcNganh(data.getId());
//            khQdBtcBoNganhCtietRepository.save(cTiet);
//        }
        return createCheck;
    }

    @Transactional
    public KhQdBtcBoNganh detailBtc(String id) throws  Exception {
        Optional<KhQdBtcBoNganh> qOptional = khQdBtcBoNganhRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()) {
            throw new Exception("Kế hoạch quyết định bộ tài chính giao bộ ngành không tồn tại");
        }
        KhQdBtcBoNganh data = qOptional.get();
        List<KhQdBtcBoNganhCtiet> listBoNganh = khQdBtcBoNganhCtietRepository.findAllByIdQdBtcNganh(data.getId());
        data.setFileDinhkems(fileDinhKemService.search(data.getId(), Collections.singleton("KH_QD_BTC_BO_NGANH")));
        data.setTenTrangThai(GiaoKeHoachVonDauNamEnum.getTentById(data.getTrangThai()));

        if(listBoNganh.size() > 0){
            data.setMuaTangList(listBoNganh.stream().filter( item -> item.getType().equals(Contains.KH_MUA_TANG)).collect(Collectors.toList()));
            data.setXuatBanList(listBoNganh.stream().filter( item -> item.getType().equals(Contains.KH_XUAT_BAN)).collect(Collectors.toList()));
            data.setXuatGiamList(listBoNganh.stream().filter( item -> item.getType().equals(Contains.KH_XUAT_GIAM)).collect(Collectors.toList()));
            data.setLuanPhienList(listBoNganh.stream().filter( item -> item.getType().equals(Contains.KH_LUAN_PHIEN_DOI_HANG)).collect(Collectors.toList()));
        }

        return data;

    }

    @Transactional
    public void delete(Long ids) throws Exception{
        Optional<KhQdBtcBoNganh> qOptional=khQdBtcBoNganhRepository.findById(ids);
        if (!qOptional.isPresent()){
            throw new UnsupportedOperationException("ID không tồn tại");
        }
        if (!qOptional.get().getTrangThai().equals(Contains.DUTHAO)){
            throw new Exception("Chỉ được xóa quyết định ở trạng thái Dự thảo");
        }
        for (KhQdBtcBoNganhCtiet bNganh : khQdBtcBoNganhCtietRepository.findAllByIdQdBtcNganh(ids)){
            khQdBtcBoNganhCtietRepository.deleteAllByIdQdBtcNganh(bNganh.getId());
        }
        fileDinhKemService.delete(qOptional.get().getId(), Lists.newArrayList("KH_QD_BTC_BO_NGANH"));

        khQdBtcBoNganhRepository.delete(qOptional.get());
    }
    @Transactional
    public  void deleteListId(List<Long> listId){
        khQdBtcBoNganhCtietRepository.deleteAllByIdQdBtcNganhIn(listId);
        fileDinhKemService.deleteMultiple(listId,Lists.newArrayList("KH_QD_BTC_BO_NGANH"));

        khQdBtcBoNganhRepository.deleteAllByIdIn(listId);
    }

    public  void exportDsQdBtc(KhQdBtBoNganhSearchReq objReq, HttpServletResponse response) throws Exception{
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhQdBtcBoNganh> page=this.searchPage(objReq);
        List<KhQdBtcBoNganh> data=page.getContent();

        String title="Danh sách quyết định bộ tài chính giao các bộ ngành";
        String[] rowsName=new String[]{"STT","Số quyết định","Năm QĐ","Ngày QĐ","Trích yếu","Trạng thái"};
        String fileName="danh-sach-ke-hoach-thu-tuong.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs=null;
        for (int i=0;i<data.size();i++){
            KhQdBtcBoNganh dx=data.get(i);
            objs=new Object[rowsName.length];
            objs[0]=i;
            objs[1]=dx.getSoQd();
            objs[2]=dx.getNamQd();
            objs[3]=dx.getNgayQd();
            objs[4]=dx.getTenBoNganh();
            objs[5]=dx.getTrichYeu();
            objs[6]=dx.getTenTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }

    public KhQdBtcBoNganh approve(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (StringUtils.isEmpty(stReq.getId())){
            throw new Exception("Không tìm thấy dữ liệu");
        }

        Optional<KhQdBtcBoNganh> optional = khQdBtcBoNganhRepository.findById(Long.valueOf(stReq.getId()));
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
        KhQdBtcBoNganh createCheck = khQdBtcBoNganhRepository.save(optional.get());
        return createCheck;
    }


}
