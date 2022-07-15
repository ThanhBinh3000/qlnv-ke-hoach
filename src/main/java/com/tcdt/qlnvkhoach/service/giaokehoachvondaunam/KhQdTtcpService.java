package com.tcdt.qlnvkhoach.service.giaokehoachvondaunam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpBoNganhCtietRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpBoNganhRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdTtcpBoNganhCtietReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdTtcpBoNganhReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdTtcpReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganh;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganhCTiet;
import com.tcdt.qlnvkhoach.util.Contains;
import com.tcdt.qlnvkhoach.util.ExportExcel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KhQdTtcpService {

    @Autowired
    private KhQdTtcpRepository khQdTtcpRepository;

    @Autowired
    private KhQdTtcpBoNganhRepository khQdTtcpBoNganhRepository;

    @Autowired
    private KhQdTtcpBoNganhCtietRepository khQdTtcpBoNganhCtietRepository;

    public Iterable<KhQdTtcp> findAll() {
        return khQdTtcpRepository.findAll();
    }
    public Page<KhQdTtcp> searchPage(KhQdTtcpSearchReq objReq) throws Exception{
        Pageable pageable= PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhQdTtcp> data=khQdTtcpRepository.selectPage(
                objReq.getNamQd(),
                objReq.getSoQd(),
                Contains.convertDateToString(objReq.getNgayQdTu()),
                Contains.convertDateToString(objReq.getNgayQdDen()),
                objReq.getTrichYeu(),
                pageable);
        return data;
    }

    @Transactional
    public KhQdTtcp save(KhQdTtcpReq objReq) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        KhQdTtcp data = new ModelMapper().map(objReq,KhQdTtcp.class);
        data.setNgayTao(new Date());
        data.setNguoiTao(userInfo.getUsername());
        KhQdTtcp createCheck = khQdTtcpRepository.save(data);

        for(KhQdTtcpBoNganhReq bNganhReq : objReq.getListBoNganh()){
            KhQdTtcpBoNganh bNganh = new ModelMapper().map(bNganhReq,KhQdTtcpBoNganh.class);
            bNganh.setIdQdTtcp(data.getId());
            // call save của thằng BO Ngành
            khQdTtcpBoNganhRepository.save(bNganh);

            for(KhQdTtcpBoNganhCtietReq ctietReq : bNganhReq.getListCtiet()){
                KhQdTtcpBoNganhCTiet cTiet = new ModelMapper().map(ctietReq,KhQdTtcpBoNganhCTiet.class);
                cTiet.setIdBoNganh(bNganh.getId());
                khQdTtcpBoNganhCtietRepository.save(cTiet);
            }
        }
        return createCheck;

    }

    public <T> void updateObjectToObject(T source, T objectEdit) throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(Contains.FORMAT_DATE_STR));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.updateValue(source, objectEdit);
    }
    @Transactional
    public KhQdTtcp update(@Valid KhQdTtcpReq objReq)throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<KhQdTtcp> qOptional=khQdTtcpRepository.findById(objReq.getId());
        if (!qOptional.isPresent())
            throw new UnsupportedOperationException("Id không tồn tại");
        KhQdTtcp data=qOptional.get();
        KhQdTtcp dataMap=new ModelMapper().map(objReq,KhQdTtcp.class);
        updateObjectToObject(data,dataMap);
        data.setNguoiSua(userInfo.getUsername());
        data.setNgaySua(new Date());
        KhQdTtcp createCheck=khQdTtcpRepository.save(data);

        //xoa tất cả
        khQdTtcpBoNganhRepository.deleteAllByIdQdTtcp(data.getId());
        for (KhQdTtcpBoNganhReq bn:objReq.getListBoNganh()){
            KhQdTtcpBoNganh bNganh = new ModelMapper().map(bn,KhQdTtcpBoNganh.class);
            bNganh.setId(null);
            bNganh.setIdQdTtcp(data.getId());
            khQdTtcpBoNganhRepository.save(bNganh);
            khQdTtcpBoNganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
            for (KhQdTtcpBoNganhCtietReq bnCtiet:bn.getListCtiet()){
                KhQdTtcpBoNganhCTiet cTiet=new ModelMapper().map(bnCtiet,KhQdTtcpBoNganhCTiet.class);
                cTiet.setId(null);
                cTiet.setIdBoNganh(bn.getId());
                khQdTtcpBoNganhCtietRepository.save(cTiet);

            }
        }

        return createCheck;


    }
    @Transactional
    public KhQdTtcp detailTtcp(String id) throws Exception{
        Optional<KhQdTtcp> qOptional=khQdTtcpRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()){
            throw new Exception("Kế hoạch quyết định Thủ tướng Chính phủ không tồn tại");
        }
        KhQdTtcp data = qOptional.get();
        // Call repository get all bo nganh by id by id KhQdTTcpt
        List<KhQdTtcpBoNganh> listBoNganh = khQdTtcpBoNganhRepository.findAllByIdQdTtcp(data.getId());
        for(KhQdTtcpBoNganh boNganh : listBoNganh){
            // Call repository get all C tiet by id Bo nganh boNganh.getId()
            List<KhQdTtcpBoNganhCTiet> listCtiet = khQdTtcpBoNganhCtietRepository.findAllByIdBoNganh(boNganh.getId());
            boNganh.setListCtiet(listCtiet);
        }
        data.setListBoNganh(listBoNganh);
        return data;

    }

    @Transactional
    public void deleteTtcp(Long ids){
        Optional<KhQdTtcp> qOptional=khQdTtcpRepository.findById(ids);
        if (!qOptional.isPresent()){
            throw new UnsupportedOperationException("Id không tồn tại");
        }

        for (KhQdTtcpBoNganh bNganh : khQdTtcpBoNganhRepository.findAllByIdQdTtcp(ids)){
            khQdTtcpBoNganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
        }
        khQdTtcpBoNganhRepository.deleteAllByIdQdTtcp(ids);

        khQdTtcpRepository.delete(qOptional.get());
    }
    @Transactional
    public void deleteListId(List<Long> listId){
        for(Long idDel : listId){
            List<KhQdTtcpBoNganh> boNganh = khQdTtcpBoNganhRepository.findAllByIdQdTtcp(idDel);
            List<Long> listIdBnganh = boNganh.stream().map(KhQdTtcpBoNganh::getId).collect(Collectors.toList());
            khQdTtcpBoNganhCtietRepository.deleteAllByIdBoNganhIn(listIdBnganh);
        }
        khQdTtcpBoNganhRepository.deleteAllByIdQdTtcpIn(listId);
        khQdTtcpRepository.deleteAllByIdIn(listId);
    }


    public  void exportDsQdTtcp(KhQdTtcpSearchReq objReq, HttpServletResponse response) throws Exception{
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhQdTtcp> page=this.searchPage(objReq);
        List<KhQdTtcp> data=page.getContent();

        String title="Danh sách quyết định Thủ tướng";
        String[] rowsName=new String[]{"STT","Số quyết định","Năm QĐ","Ngày QĐ","Trích yếu","Trạng thái"};
        String fileName="danh-sach-quyet-dinh-thu-tuong.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs=null;
        for (int i=0;i<data.size();i++){
            KhQdTtcp dx=data.get(i);
            objs=new Object[rowsName.length];
            objs[0]=i;
            objs[1]=dx.getSoQd();
            objs[2]=dx.getNam();
            objs[3]=dx.getNgayQd();
            objs[4]=dx.getTrichYeu();
            objs[5]=dx.getTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }

}
