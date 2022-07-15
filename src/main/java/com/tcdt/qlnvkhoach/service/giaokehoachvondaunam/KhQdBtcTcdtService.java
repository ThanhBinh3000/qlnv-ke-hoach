package com.tcdt.qlnvkhoach.service.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdBtcTcdtCtietRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdBtcTcdtRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdBtcTcdtCtietReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdBtcTcdtReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdTtcpBoNganhCtietReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdBtcTcdtSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdt;
import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdtCtiet;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
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
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KhQdBtcTcdtService {
    @Autowired
    KhQdBtcTcdtRepository khQdBtcTcdtRepository;

    @Autowired
    KhQdBtcTcdtCtietRepository khQdBtcTcdtCtietRepository;

    @Transactional
    public KhQdBtcTcdt save(KhQdBtcTcdtReq objReq) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        KhQdBtcTcdt data=new ModelMapper().map(objReq,KhQdBtcTcdt.class);
        data.setNgayTao(new Date());
        data.setNguoiTao(userInfo.getUsername());
        data.setTrangThai("00");
        KhQdBtcTcdt createCheck=khQdBtcTcdtRepository.save(data);
         for (KhQdBtcTcdtCtietReq cTietreq: objReq.getListCtiet()){
             KhQdBtcTcdtCtiet cTiet=new ModelMapper().map(cTietreq,KhQdBtcTcdtCtiet.class);
             cTiet.setIdQdBtcTcdt(data.getId());
             khQdBtcTcdtCtietRepository.save(cTiet);
         }

        return createCheck;
    }
    @Transactional
    public KhQdBtcTcdt update(@Valid KhQdBtcTcdtReq objReq)throws Exception{
    UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<KhQdBtcTcdt> qOptional=khQdBtcTcdtRepository.findById(objReq.getId());
        if (!qOptional.isPresent())
            throw new UnsupportedOperationException("Id không tồn tại");
        KhQdBtcTcdt data=qOptional.get();
        KhQdBtcTcdt dataMap= new ModelMapper().map(objReq,KhQdBtcTcdt.class);
        Contains.updateObjectToObject(data,dataMap);
        data.setNguoiSua(userInfo.getUsername());
        data.setNgaySua(new Date());
        KhQdBtcTcdt createCheck=khQdBtcTcdtRepository.save(data);
        khQdBtcTcdtCtietRepository.deleteAllByIdQdBtcTcdt(data.getId());
        for (KhQdBtcTcdtCtietReq cTietreq: objReq.getListCtiet()){
            KhQdBtcTcdtCtiet cTiet=new ModelMapper().map(cTietreq,KhQdBtcTcdtCtiet.class);
            cTiet.setId(null);
            cTiet.setIdQdBtcTcdt(data.getId());
            khQdBtcTcdtCtietRepository.save(cTiet);
        }
        return createCheck;
    }
    @Transactional
    public KhQdBtcTcdt detail(Long id)throws Exception{
        Optional<KhQdBtcTcdt> qOptional =khQdBtcTcdtRepository.findById(id);
        if (!qOptional.isPresent()){
            throw new Exception("Kế hoạch quyết định Thủ tướng Chính phủ không tồn tại");
        }
        KhQdBtcTcdt data= qOptional.get();
        List<KhQdBtcTcdtCtiet> listChiTiet=khQdBtcTcdtCtietRepository.findAllByIdQdBtcTcdt(data.getId());
        data.setListCtiet(listChiTiet);
       return data;
    }
    @Transactional
    public  void  delete(Long ids) throws Exception{
        Optional<KhQdBtcTcdt> qOptional=khQdBtcTcdtRepository.findById(ids);
        if(!qOptional.isPresent()){
            throw new UserPrincipalNotFoundException("Id không tồn tại");
        }
        khQdBtcTcdtCtietRepository.deleteAllByIdQdBtcTcdt(ids);
        khQdBtcTcdtRepository.delete(qOptional.get());
    }
    public Page<KhQdBtcTcdt> searchPage(KhQdBtcTcdtSearchReq objReq) throws Exception{
        Pageable pageable= PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhQdBtcTcdt> data= khQdBtcTcdtRepository.selectPage(
                objReq.getNamQd(),
                objReq.getSoQd(),
                Contains.convertDateToString(objReq.getNgayQdTu()),
                Contains.convertDateToString(objReq.getNgayQdDen()),
                objReq.getTrichYeu(),
                pageable);
        return data;
    }


    public void deleteListId(List<Long> listId){
        khQdBtcTcdtCtietRepository.deleteAllByIdQdBtcTcdtIn(listId);
        khQdBtcTcdtRepository.deleteAllByIdIn(listId);
    }

    public  void exportDsQdBtcTcdt(KhQdBtcTcdtSearchReq objReq, HttpServletResponse response) throws Exception{
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhQdBtcTcdt> page=this.searchPage(objReq);
        List<KhQdBtcTcdt> data=page.getContent();

        String title="Danh sách quyết định BTC giao TCDT";
        String[] rowsName=new String[]{"STT","Số quyết định","Ngày QĐ","Trích yếu","Trạng thái"};
        String fileName="danh-sach-quyet-dinh-btc-tcdt.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs=null;
        for (int i=0;i<data.size();i++){
            KhQdBtcTcdt dx=data.get(i);
            objs=new Object[rowsName.length];
            objs[0]=i;
            objs[1]=dx.getSoQd();
            objs[2]=dx.getNgayQd();
            objs[3]=dx.getTrichYeu();
            objs[4]=dx.getTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }

}
