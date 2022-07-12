package com.tcdt.qlnvkhoach.service.giaokehoachvondaunam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpBoNganhCtietRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpBoNganhRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpRepository;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdTtcpReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.response.giaokehoachvondaunam.KhQdTtcpBoNganhCtietRes;
import com.tcdt.qlnvkhoach.response.giaokehoachvondaunam.KhQdTtcpBoNganhRes;
import com.tcdt.qlnvkhoach.response.giaokehoachvondaunam.KhQdTtcpRes;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganh;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganhCTiet;
import com.tcdt.qlnvkhoach.util.Contains;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KhQdTtcpService {

    @Autowired
    private KhQdTtcpRepository khQdTtcpRepository;

    @Autowired
    private KhQdTtcpBoNganhRepository khQdTtcpBoNganhRepository;

    private KhQdTtcpBoNganhCtietRepository khQdTtcpBoNganhCtietRepository;

    public Iterable<KhQdTtcp> findAll() {
        return khQdTtcpRepository.findAll();
    }
    public Page<KhQdTtcp> searchPage(KhQdTtcpSearchReq objReq) throws Exception{
        Pageable pageable= PageRequest.of(objReq.getPaggingReq().getPage(),objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
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
    public KhQdTtcp save(KhQdTtcpRes objReq, HttpServletRequest req) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        KhQdTtcp data = new ModelMapper().map(objReq,KhQdTtcp.class);
        data.setNgayTao(new Date());
        data.setNguoiTao(userInfo.getUsername());
        KhQdTtcp createCheck = khQdTtcpRepository.save(data);

        for(KhQdTtcpBoNganhRes bNganhReq : objReq.getListBoNganh()){
            KhQdTtcpBoNganh bNganh = new ModelMapper().map(bNganhReq,KhQdTtcpBoNganh.class);
            bNganh.setIdQdTtcp(data.getId());
            // call save của thằng BO Ngành
            khQdTtcpBoNganhRepository.save(bNganh);

            for(KhQdTtcpBoNganhCtietRes ctietRes : bNganhReq.getListCtiet()){
                KhQdTtcpBoNganhCTiet cTiet = new ModelMapper().map(bNganhReq,KhQdTtcpBoNganhCTiet.class);
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
    public KhQdTtcp update(@Valid KhQdTtcpReq objReq, HttpServletRequest req)throws Exception{
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
        return createCheck;


    }

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
    public void delete(Long ids){
        Optional<KhQdTtcp> qOptional=khQdTtcpRepository.findById(ids);
        if (!qOptional.isPresent()){
            throw new UnsupportedOperationException("Id không tồn tại");
        }
        khQdTtcpRepository.delete(qOptional.get());
    }

}
