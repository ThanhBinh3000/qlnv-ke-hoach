package com.tcdt.qlnvkhoach.service.giaokehoachvondaunam;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.enums.GiaoKeHoachVonDauNamEnum;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdBtcTcdtCtietRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdBtcTcdtRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdBtcTcdtCtietReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdBtcTcdtReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdBtcTcdtSearchReq;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdt;
import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdtCtiet;
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
import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KhQdBtcTcdtService {
    @Autowired
    KhQdBtcTcdtRepository khQdBtcTcdtRepository;

    @Autowired
    KhQdBtcTcdtCtietRepository khQdBtcTcdtCtietRepository;

    @Autowired
    private QlnvDmService qlnvDmService;

    @Autowired
    private FileDinhKemService fileDinhKemService;

    @Transactional
    public KhQdBtcTcdt save(KhQdBtcTcdtReq objReq) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<KhQdBtcTcdt> soQd = khQdBtcTcdtRepository.findBySoQd(objReq.getSoQd());
        if(soQd.isPresent()){
            throw new Exception("Số quyết định đã tồn tại");
        }
        Optional<KhQdBtcTcdt> namQd = khQdBtcTcdtRepository.findByNamQd(objReq.getNamQd());
        if(namQd.isPresent()){
            throw new Exception("Năm "+objReq.getNamQd()+" đã tồn tại quyết định");
        }
        KhQdBtcTcdt data=new ModelMapper().map(objReq,KhQdBtcTcdt.class);
        data.setNgayTao(new Date());
        data.setNguoiTao(userInfo.getUsername());
        data.setTrangThai("00");
        KhQdBtcTcdt createCheck=khQdBtcTcdtRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_QD_BTC_TCDT");

        createCheck.setFileDinhkems(fileDinhKems);

        this.savaCtiet(objReq,data);

//         for (KhQdBtcTcdtCtietReq cTietreq: objReq.getListCtiet()){
//             KhQdBtcTcdtCtiet cTiet=new ModelMapper().map(cTietreq,KhQdBtcTcdtCtiet.class);
//             cTiet.setIdQdBtcTcdt(data.getId());
//             khQdBtcTcdtCtietRepository.save(cTiet);
//         }

        return createCheck;
    }
    public void savaCtiet (KhQdBtcTcdtReq btcTcdtReq ,KhQdBtcTcdt btcTcdtSave) {

        KhQdBtcTcdtCtiet cTietNx =new ModelMapper().map(btcTcdtReq.getKeHoachNhapXuat(),KhQdBtcTcdtCtiet.class);
        cTietNx.setId(null);
        cTietNx.setIdQdBtcTcdt(btcTcdtSave.getId());
        cTietNx.setType(Contains.KH_NHAP_XUAT_LT);
        khQdBtcTcdtCtietRepository.save(cTietNx);

        for (KhQdBtcTcdtCtietReq cTietreq :btcTcdtReq.getMuaTangList() ){
             KhQdBtcTcdtCtiet cTiet=new ModelMapper().map(cTietreq,KhQdBtcTcdtCtiet.class);
             cTiet.setId(null);
             cTiet.setIdQdBtcTcdt(btcTcdtSave.getId());
             cTiet.setType(Contains.KH_MUA_TANG);
             khQdBtcTcdtCtietRepository.save(cTiet);
         }

        for (KhQdBtcTcdtCtietReq cTietreq: btcTcdtReq.getXuatGiamList()){
            KhQdBtcTcdtCtiet cTiet=new ModelMapper().map(cTietreq,KhQdBtcTcdtCtiet.class);
            cTiet.setId(null);
            cTiet.setIdQdBtcTcdt(btcTcdtSave.getId());
            cTiet.setType(Contains.KH_XUAT_GIAM);
            khQdBtcTcdtCtietRepository.save(cTiet);
        }

        for (KhQdBtcTcdtCtietReq cTietreq: btcTcdtReq.getXuatBanList()){
            KhQdBtcTcdtCtiet cTiet=new ModelMapper().map(cTietreq,KhQdBtcTcdtCtiet.class);
            cTiet.setId(null);
            cTiet.setIdQdBtcTcdt(btcTcdtSave.getId());
            cTiet.setType(Contains.KH_XUAT_BAN);
            khQdBtcTcdtCtietRepository.save(cTiet);
        }

        for (KhQdBtcTcdtCtietReq cTietreq: btcTcdtReq.getLuanPhienList()){
            KhQdBtcTcdtCtiet cTiet=new ModelMapper().map(cTietreq,KhQdBtcTcdtCtiet.class);
            cTiet.setId(null);
            cTiet.setIdQdBtcTcdt(btcTcdtSave.getId());
            cTiet.setType(Contains.KH_LUAN_PHIEN_DOI_HANG);
            khQdBtcTcdtCtietRepository.save(cTiet);
        }
    }

    @Transactional
    public KhQdBtcTcdt update(@Valid KhQdBtcTcdtReq objReq)throws Exception{
    UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<KhQdBtcTcdt> qOptional=khQdBtcTcdtRepository.findById(objReq.getId());
        if (!qOptional.isPresent())
            throw new UnsupportedOperationException("Id không tồn tại");
        Optional<KhQdBtcTcdt> namQd = khQdBtcTcdtRepository.findByNamQd(objReq.getNamQd());
        if (namQd.isPresent()){
            if(!namQd.get().getId().equals(objReq.getId())){
                throw new Exception("Số quyết định " + objReq.getSoQd() + " đã tồn tại");
            }
        }
        KhQdBtcTcdt data=qOptional.get();
        KhQdBtcTcdt dataMap= new ModelMapper().map(objReq,KhQdBtcTcdt.class);
        Contains.updateObjectToObject(data,dataMap);
        data.setNguoiSua(userInfo.getUsername());
        data.setNgaySua(new Date());
        KhQdBtcTcdt createCheck=khQdBtcTcdtRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_QD_BTC_TCDT");
        createCheck.setFileDinhkems(fileDinhKems);

        khQdBtcTcdtCtietRepository.deleteAllByIdQdBtcTcdt(data.getId());
        this.savaCtiet(objReq,data);
        return createCheck;
    }
    @Transactional
    public KhQdBtcTcdt detail(Long id)throws Exception{
        Optional<KhQdBtcTcdt> qOptional =khQdBtcTcdtRepository.findById(id);
        if (!qOptional.isPresent()){
            throw new Exception("Kế hoạch quyết định Btc TCdt không tồn tại");
        }
        Map<String,String> hashMapBoNganh = qlnvDmService.getListDanhMucChung("BO_NGANH");
        Map<String,String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        KhQdBtcTcdt data= qOptional.get();
        List<KhQdBtcTcdtCtiet> listChiTiet = khQdBtcTcdtCtietRepository.findAllByIdQdBtcTcdt(data.getId());

        data.setFileDinhkems(fileDinhKemService.search(data.getId(),Collections.singleton("KH_QD_BTC_TCDT")));
        data.setTenTrangThai(GiaoKeHoachVonDauNamEnum.getTentById(data.getTrangThai()));
        listChiTiet.forEach( item -> {
            item.setTenCloaiVthh(hashMapHh.get(item.getCloaiVthh()));
            item.setTenVthh(hashMapHh.get(item.getLoaiVthh()));
        });
        if(listChiTiet.size() > 0){
            data.setKeHoachNhapXuat(listChiTiet.stream().filter( item -> item.getType().equals(Contains.KH_NHAP_XUAT_LT)).collect(Collectors.toList()).get(0));
            data.setMuaTangList(listChiTiet.stream().filter( item -> item.getType().equals(Contains.KH_MUA_TANG)).collect(Collectors.toList()));
            data.setXuatBanList(listChiTiet.stream().filter( item -> item.getType().equals(Contains.KH_XUAT_BAN)).collect(Collectors.toList()));
            data.setXuatGiamList(listChiTiet.stream().filter( item -> item.getType().equals(Contains.KH_XUAT_GIAM)).collect(Collectors.toList()));
            data.setLuanPhienList(listChiTiet.stream().filter( item -> item.getType().equals(Contains.KH_LUAN_PHIEN_DOI_HANG)).collect(Collectors.toList()));
        }

       return data;
    }
    @Transactional
    public  void  delete(Long ids) throws Exception{
        Optional<KhQdBtcTcdt> qOptional=khQdBtcTcdtRepository.findById(ids);
        if(!qOptional.isPresent()){
            throw new UserPrincipalNotFoundException("Id không tồn tại");
        }
        if (!qOptional.get().getTrangThai().equals(Contains.DUTHAO)){
            throw new Exception("Chỉ được xóa quyết định ở trạng thái Dự thảo");
        }
        khQdBtcTcdtCtietRepository.deleteAllByIdQdBtcTcdt(ids);
        fileDinhKemService.delete(qOptional.get().getId(), Lists.newArrayList("KH_QD_BTC_TCDT"));
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
                objReq.getTrangThai(),
                pageable);
        data.getContent().forEach( f -> {
                f.setTenTrangThai(GiaoKeHoachVonDauNamEnum.getTentById(f.getTrangThai()));
        });
        return data;
    }


    public void deleteListId(List<Long> listId){
        khQdBtcTcdtCtietRepository.deleteAllByIdQdBtcTcdtIn(listId);
        fileDinhKemService.deleteMultiple(listId,Lists.newArrayList("KH_QD_BTC_TCDT"));
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
            objs[4]=dx.getTenTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }
    public KhQdBtcTcdt approve(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (StringUtils.isEmpty(stReq.getId())){
            throw new Exception("Không tìm thấy dữ liệu");
        }

        Optional<KhQdBtcTcdt> optional = khQdBtcTcdtRepository.findById(Long.valueOf(stReq.getId()));
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

        KhQdBtcTcdt createCheck = khQdBtcTcdtRepository.save(optional.get());

        return createCheck;
    }

}
