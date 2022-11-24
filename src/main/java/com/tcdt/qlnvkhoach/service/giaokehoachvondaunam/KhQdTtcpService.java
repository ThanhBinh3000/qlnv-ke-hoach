package com.tcdt.qlnvkhoach.service.giaokehoachvondaunam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.enums.GiaoKeHoachVonDauNamEnum;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpBoNganhCtietRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpBoNganhRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam.KhQdTtcpRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdTtcpBoNganhCtietReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdTtcpBoNganhReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvondaunam.KhQdTtcpReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvondaunam.KhQdTtcpSearchReq;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.service.filedinhkem.FileDinhKemService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganh;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcpBoNganhCTiet;
import com.tcdt.qlnvkhoach.util.Constants;
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
public class KhQdTtcpService {

    @Autowired
    private KhQdTtcpRepository khQdTtcpRepository;

    @Autowired
    private KhQdTtcpBoNganhRepository khQdTtcpBoNganhRepository;

    @Autowired
    private KhQdTtcpBoNganhCtietRepository khQdTtcpBoNganhCtietRepository;

    @Autowired
    private QlnvDmService qlnvDmService;

    @Autowired
    private FileDinhKemService fileDinhKemService;

    public Iterable<KhQdTtcp> findAll() {
        return khQdTtcpRepository.findAll();
    }
    public Page<KhQdTtcp> searchPage(KhQdTtcpSearchReq objReq) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        Pageable pageable= PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhQdTtcp> data=khQdTtcpRepository.selectPage(
                objReq.getNamQd(),
                objReq.getSoQd(),
                Contains.convertDateToString(objReq.getNgayQdTu()),
                Contains.convertDateToString(objReq.getNgayQdDen()),
                objReq.getTrichYeu(),
                objReq.getTrangThai(),
                userInfo.getDvql(),
                pageable);
        data.getContent().forEach( f -> {
                f.setTenTrangThai(GiaoKeHoachVonDauNamEnum.getTentById(f.getTrangThai()));
        });
        return data;
    }

    @Transactional
    public KhQdTtcp save(KhQdTtcpReq objReq) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<KhQdTtcp> optional = khQdTtcpRepository.findBySoQd(objReq.getSoQd());
        if(optional.isPresent()){
            throw new Exception("Số quyết định đã tồn tại");
        }
        Optional<KhQdTtcp> optional2 = khQdTtcpRepository.findByNamQd(objReq.getNamQd());
        if(optional2.isPresent()){
            throw new Exception("Năm "+objReq.getNamQd()+" đã tồn tại quyết định");
        }
        KhQdTtcp data = new ModelMapper().map(objReq,KhQdTtcp.class);
        data.setNgayTao(new Date());
        data.setNguoiTao(userInfo.getUsername());
        // Dự thảo
        data.setTrangThai("00");
        KhQdTtcp createCheck = khQdTtcpRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_QD_TTCP");
        createCheck.setFileDinhkems(fileDinhKems);

        for(KhQdTtcpBoNganhReq bNganhReq : objReq.getListBoNganh()){
            KhQdTtcpBoNganh bNganh = new ModelMapper().map(bNganhReq,KhQdTtcpBoNganh.class);
            bNganh.setIdQdTtcp(data.getId());
            // call save của thằng BO Ngành
            khQdTtcpBoNganhRepository.save(bNganh);
            this.saveBoNgayCtiet(bNganhReq,bNganh);
        }
        return createCheck;
    }

    public void saveBoNgayCtiet(KhQdTtcpBoNganhReq boNganhReq,KhQdTtcpBoNganh bNganhSaved) {
        if(boNganhReq.getMuaTangList() != null ) {
            for(KhQdTtcpBoNganhCtietReq ctietReq : boNganhReq.getMuaTangList()){
                KhQdTtcpBoNganhCTiet cTiet = new ModelMapper().map(ctietReq,KhQdTtcpBoNganhCTiet.class);
                cTiet.setId(null);
                cTiet.setIdBoNganh(bNganhSaved.getId());
                cTiet.setType(Contains.KH_MUA_TANG);
                khQdTtcpBoNganhCtietRepository.save(cTiet);
            }
        }
        if (boNganhReq.getXuatGiamList()!=null) {
            for(KhQdTtcpBoNganhCtietReq ctietReq : boNganhReq.getXuatGiamList()){
                KhQdTtcpBoNganhCTiet cTiet = new ModelMapper().map(ctietReq,KhQdTtcpBoNganhCTiet.class);
                cTiet.setId(null);
                cTiet.setIdBoNganh(bNganhSaved.getId());
                cTiet.setType(Contains.KH_XUAT_GIAM);
                khQdTtcpBoNganhCtietRepository.save(cTiet);
            }
        }
        if (boNganhReq.getXuatBanList() != null) {
            for(KhQdTtcpBoNganhCtietReq ctietReq : boNganhReq.getXuatBanList()){
                KhQdTtcpBoNganhCTiet cTiet = new ModelMapper().map(ctietReq,KhQdTtcpBoNganhCTiet.class);
                cTiet.setId(null);
                cTiet.setIdBoNganh(bNganhSaved.getId());
                cTiet.setType(Contains.KH_XUAT_BAN);
                khQdTtcpBoNganhCtietRepository.save(cTiet);
            }
        }
        if (boNganhReq.getLuanPhienList() != null ) {
            for(KhQdTtcpBoNganhCtietReq ctietReq : boNganhReq.getLuanPhienList()){
                KhQdTtcpBoNganhCTiet cTiet = new ModelMapper().map(ctietReq,KhQdTtcpBoNganhCTiet.class);
                cTiet.setId(null);
                cTiet.setIdBoNganh(bNganhSaved.getId());
                cTiet.setType(Contains.KH_LUAN_PHIEN_DOI_HANG);
                khQdTtcpBoNganhCtietRepository.save(cTiet);
            }
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
    public KhQdTtcp update(KhQdTtcpReq objReq)throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<KhQdTtcp> qOptional=khQdTtcpRepository.findById(objReq.getId());
        if (!qOptional.isPresent())
            throw new UnsupportedOperationException("Id không tồn tại");

        Optional<KhQdTtcp> optional = khQdTtcpRepository.findBySoQd(objReq.getSoQd());
        if (optional.isPresent()){
            if(!optional.get().getId().equals(objReq.getId())){
                throw new Exception("Số quyết định " + objReq.getSoQd() + " đã tồn tại");
            }
        }

        KhQdTtcp data=qOptional.get();
        KhQdTtcp dataMap=new ModelMapper().map(objReq,KhQdTtcp.class);
        updateObjectToObject(data,dataMap);
        data.setNguoiSua(userInfo.getUsername());
        data.setNgaySua(new Date());
        KhQdTtcp createCheck=khQdTtcpRepository.save(data);

        List<FileDinhKemChung> fileDinhKems = fileDinhKemService.saveListFileDinhKem(objReq.getFileDinhKems(),data.getId(),"KH_QD_TTCP");
        createCheck.setFileDinhkems(fileDinhKems);

        //xoa tất cả

        khQdTtcpBoNganhRepository.deleteAllByIdQdTtcp(data.getId());
        for (KhQdTtcpBoNganhReq bn:objReq.getListBoNganh()){
            KhQdTtcpBoNganh bNganh = new ModelMapper().map(bn,KhQdTtcpBoNganh.class);
            bNganh.setId(null);
            bNganh.setIdQdTtcp(data.getId());
            khQdTtcpBoNganhRepository.save(bNganh);
            khQdTtcpBoNganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
            this.saveBoNgayCtiet(bn,bNganh);
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
        Map<String,String> hashMapBoNganh = qlnvDmService.getListDanhMucDonVi(Constants.BO_NGANH);
        Map<String,String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        List<KhQdTtcpBoNganh> listBoNganh = khQdTtcpBoNganhRepository.findAllByIdQdTtcp(data.getId());
        data.setFileDinhkems(fileDinhKemService.search(data.getId(),Collections.singleton("KH_QD_TTCP")));
        data.setTenTrangThai(GiaoKeHoachVonDauNamEnum.getTentById(data.getTrangThai()));

        for(KhQdTtcpBoNganh boNganh : listBoNganh){
            boNganh.setTenBoNganh(hashMapBoNganh.get(boNganh.getMaBoNganh()));
            List<KhQdTtcpBoNganhCTiet> listCtiet = khQdTtcpBoNganhCtietRepository.findAllByIdBoNganh(boNganh.getId());
            listCtiet.forEach( item -> {
                item.setTenCloaiVthh(hashMapHh.get(item.getCloaiVthh()));
                item.setTenVthh(hashMapHh.get(item.getLoaiVthh()));
            });
            if(listCtiet.size() > 0){
                boNganh.setMuaTangList(listCtiet.stream().filter( item -> item.getType().equals(Contains.KH_MUA_TANG)).collect(Collectors.toList()));
                boNganh.setXuatBanList(listCtiet.stream().filter( item -> item.getType().equals(Contains.KH_XUAT_BAN)).collect(Collectors.toList()));
                boNganh.setXuatGiamList(listCtiet.stream().filter( item -> item.getType().equals(Contains.KH_XUAT_GIAM)).collect(Collectors.toList()));
                boNganh.setLuanPhienList(listCtiet.stream().filter( item -> item.getType().equals(Contains.KH_LUAN_PHIEN_DOI_HANG)).collect(Collectors.toList()));
            }
        }
        data.setListBoNganh(listBoNganh);
        return data;
    }

    @Transactional
    public void deleteTtcp(Long ids) throws Exception{
        Optional<KhQdTtcp> qOptional=khQdTtcpRepository.findById(ids);
        if (!qOptional.isPresent()){
            throw new UnsupportedOperationException("Id không tồn tại");
        }
        if (!qOptional.get().getTrangThai().equals(Contains.DUTHAO)){
            throw new Exception("Chỉ được xóa quyết định ở trạng thái Dự thảo");
        }

        for (KhQdTtcpBoNganh bNganh : khQdTtcpBoNganhRepository.findAllByIdQdTtcp(ids)){
            khQdTtcpBoNganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
        }
        khQdTtcpBoNganhRepository.deleteAllByIdQdTtcp(ids);
        fileDinhKemService.delete(qOptional.get().getId(), Lists.newArrayList("KH_QD_TTCP"));
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
        fileDinhKemService.deleteMultiple(listId,Lists.newArrayList("KH_QD_TTCP"));
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
            objs[2]=dx.getNamQd();
            objs[3]=dx.getNgayQd();
            objs[4]=dx.getTrichYeu();
            objs[5]=dx.getTenTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }


    public KhQdTtcp approve(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (StringUtils.isEmpty(stReq.getId())){
            throw new Exception("Không tìm thấy dữ liệu");
        }

        Optional<KhQdTtcp> optional = khQdTtcpRepository.findById(Long.valueOf(stReq.getId()));
        if (!optional.isPresent()){
            throw new Exception("Không tìm thấy dữ liệu");
        }

        String status = stReq.getTrangThai() + optional.get().getTrangThai();
        switch (status) {
            case Contains.BAN_HANH + Contains.DUTHAO:
                optional.get().setNguoiPduyet(userInfo.getUsername());
                optional.get().setNgayPduyet(new Date());
                break;
            default:
                throw new Exception("Phê duyệt không thành công");
        }

        optional.get().setTrangThai(stReq.getTrangThai());

        KhQdTtcp createCheck = khQdTtcpRepository.save(optional.get());

        return createCheck;
    }

}
