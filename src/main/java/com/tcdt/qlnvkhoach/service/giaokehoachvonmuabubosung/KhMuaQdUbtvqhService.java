package com.tcdt.qlnvkhoach.service.giaokehoachvonmuabubosung;
import com.tcdt.qlnvkhoach.repository.giaokehoachvonmuabubosung.KhMuaQdUbtvqhBnganhCtietRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvonmuabubosung.KhMuaQdUbtvqhBnganhRepository;
import com.tcdt.qlnvkhoach.repository.giaokehoachvonmuabubosung.KhMuaQdUbtvqhRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabubosung.KhMuaQdUbtvqhBnganhCtietReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabubosung.KhMuaQdUbtvqhBnganhReq;
import com.tcdt.qlnvkhoach.request.object.giaokehoachvonmuabubosung.KhMuaQdUbtvqhReq;
import com.tcdt.qlnvkhoach.request.search.catalog.giaokehoachvonmuabubosung.KhMuaQdUbtvqhSearchReq;
import com.tcdt.qlnvkhoach.service.QlnvDmService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung.KhMuaQdUbtvqh;
import com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung.KhMuaQdUbtvqhBnganh;
import com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung.KhMuaQdUbtvqhBnganhCtiet;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KhMuaQdUbtvqhService {
    @Autowired
    public KhMuaQdUbtvqhRepository khMuaQdUbtvqhRepository;

    @Autowired
    public KhMuaQdUbtvqhBnganhRepository khMuaQdUbtvqhBnganhRepository;

    @Autowired
    public KhMuaQdUbtvqhBnganhCtietRepository khMuaQdUbtvqhBnganhCtietRepository;

    @Autowired
    private QlnvDmService qlnvDmService;

    public Page<KhMuaQdUbtvqh> searchPage(KhMuaQdUbtvqhSearchReq objReq) throws Exception {
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhMuaQdUbtvqh> data = khMuaQdUbtvqhRepository.selectPage(
                objReq.getNamQd(),
                objReq.getSoQd(),
                Contains.convertDateToString(objReq.getNgayQdTu()),
                Contains.convertDateToString(objReq.getNgayQdDen()),
                objReq.getTrichYeu(),
                objReq.getTrangThai(),
                pageable);
        return data;
    }

    @Transactional
    public KhMuaQdUbtvqh save(KhMuaQdUbtvqhReq objReq) throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<KhMuaQdUbtvqh> optional = khMuaQdUbtvqhRepository.findBySoQd(objReq.getSoQd());
        if(optional.isPresent()){
            throw new Exception("Số quyết định đã tồn tại");
        }
        Optional<KhMuaQdUbtvqh> optional2 = khMuaQdUbtvqhRepository.findByNamQd(objReq.getNamQd());
        if(optional2.isPresent()){
            throw new Exception("Năm "+objReq.getNamQd()+" đã tồn tại quyết định");
        }
        KhMuaQdUbtvqh data = new ModelMapper().map(objReq,KhMuaQdUbtvqh.class);
        data.setNgayTao(new Date());
        data.setNguoiTao(userInfo.getUsername());
        // Dự thảo
        data.setTrangThai("00");
        KhMuaQdUbtvqh createCheck = khMuaQdUbtvqhRepository.save(data);
        for(KhMuaQdUbtvqhBnganhReq bNganhReq : objReq.getListBoNganh()){
            KhMuaQdUbtvqhBnganh bNganh = new ModelMapper().map(bNganhReq, com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung.KhMuaQdUbtvqhBnganh.class);
            bNganh.setIdMuaQdUbtvqh(data.getId());

            khMuaQdUbtvqhBnganhRepository.save(bNganh);
            this.saveBoNgayCtiet(bNganhReq,bNganh);
        }
        return createCheck;
    }

    public void saveBoNgayCtiet(KhMuaQdUbtvqhBnganhReq boNganhReq, KhMuaQdUbtvqhBnganh bNganhSaved) {
        for(KhMuaQdUbtvqhBnganhCtietReq ctietReq : boNganhReq.getMuaBuList()){
            KhMuaQdUbtvqhBnganhCtiet cTiet = new ModelMapper().map(ctietReq,KhMuaQdUbtvqhBnganhCtiet.class);
            cTiet.setId(null);
            cTiet.setIdBoNganh(bNganhSaved.getId());
            cTiet.setType(Contains.KH_MUA_BU);
            khMuaQdUbtvqhBnganhCtietRepository.save(cTiet);
        }
        for(KhMuaQdUbtvqhBnganhCtietReq ctietReq : boNganhReq.getMuaBsungList()){
            KhMuaQdUbtvqhBnganhCtiet cTiet = new ModelMapper().map(ctietReq,KhMuaQdUbtvqhBnganhCtiet.class);
            cTiet.setId(null);
            cTiet.setIdBoNganh(bNganhSaved.getId());
            cTiet.setType(Contains.KH_MUA_BO_SUNG);
            khMuaQdUbtvqhBnganhCtietRepository.save(cTiet);
        }

    }

    @Transactional
    public KhMuaQdUbtvqh update(KhMuaQdUbtvqhReq objReq)throws Exception{
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<KhMuaQdUbtvqh> qOptional=khMuaQdUbtvqhRepository.findById(objReq.getId());
        if (!qOptional.isPresent())
            throw new UnsupportedOperationException("Id không tồn tại");

        Optional<KhMuaQdUbtvqh> optional = khMuaQdUbtvqhRepository.findBySoQd(objReq.getSoQd());
        if (optional.isPresent()){
            if(!optional.get().getId().equals(objReq.getId())){
                throw new Exception("Số quyết định " + objReq.getSoQd() + " đã tồn tại");
            }
        }

        KhMuaQdUbtvqh data=qOptional.get();
        KhMuaQdUbtvqh dataMap=new ModelMapper().map(objReq,KhMuaQdUbtvqh.class);
        Contains.updateObjectToObject(data,dataMap);
        data.setNguoiSua(userInfo.getUsername());
        data.setNgaySua(new Date());
        KhMuaQdUbtvqh createCheck=khMuaQdUbtvqhRepository.save(data);

        //xoa tất cả
        khMuaQdUbtvqhBnganhRepository.deleteAllByIdMuaQdUbtvqh(data.getId());
        for (KhMuaQdUbtvqhBnganhReq bnganhReq : objReq.getListBoNganh()){
            KhMuaQdUbtvqhBnganh bNganh = new ModelMapper().map(bnganhReq,KhMuaQdUbtvqhBnganh.class);
            bNganh.setId(null);
            bNganh.setIdMuaQdUbtvqh(data.getId());
            khMuaQdUbtvqhBnganhRepository.save(bNganh);
            khMuaQdUbtvqhBnganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
            this.saveBoNgayCtiet(bnganhReq,bNganh);
        }
        return createCheck;
    }

    @Transactional
    public KhMuaQdUbtvqh detail(String id) throws Exception{
        Optional<KhMuaQdUbtvqh> qOptional=khMuaQdUbtvqhRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()){
            throw new Exception("Nghị quyết của ủy ban thường vụ Quốc hội không tồn tại");
        }
        KhMuaQdUbtvqh data = qOptional.get();
        Map<String,String> hashMapBoNganh = qlnvDmService.getListDanhMucChung("BO_NGANH");
        Map<String,String> hashMapHh = qlnvDmService.getListDanhMucHangHoa();
        List<KhMuaQdUbtvqhBnganh> listBoNganh = khMuaQdUbtvqhBnganhRepository.findAllByIdMuaQdUbtvqh(data.getId());
        for(KhMuaQdUbtvqhBnganh boNganh : listBoNganh){
            boNganh.setTenBoNganh(hashMapBoNganh.get(boNganh.getMaBoNganh()));
            List<KhMuaQdUbtvqhBnganhCtiet> listCtiet = khMuaQdUbtvqhBnganhCtietRepository.findAllByIdBoNganh(boNganh.getId());
            listCtiet.forEach( item -> {
                item.setTenCloaiVthh(hashMapHh.get(item.getCloaiVthh()));
                item.setTenVthh(hashMapHh.get(item.getLoaiVthh()));
            });
            if(listCtiet.size() > 0){
                boNganh.setMuaBuList(listCtiet.stream().filter( item -> item.getType().equals(Contains.KH_MUA_BU)).collect(Collectors.toList()));
                boNganh.setMuaBsungList(listCtiet.stream().filter( item -> item.getType().equals(Contains.KH_MUA_BO_SUNG)).collect(Collectors.toList()));
                }
        }
        data.setListBoNganh(listBoNganh);
        return data;
    }

    @Transactional
    public void delete(Long ids){
        Optional<KhMuaQdUbtvqh> qOptional=khMuaQdUbtvqhRepository.findById(ids);
        if (!qOptional.isPresent()){
            throw new UnsupportedOperationException("Id không tồn tại");
        }

        for (KhMuaQdUbtvqhBnganh bNganh : khMuaQdUbtvqhBnganhRepository.findAllByIdMuaQdUbtvqh(ids)){
            khMuaQdUbtvqhBnganhCtietRepository.deleteAllByIdBoNganh(bNganh.getId());
        }
        khMuaQdUbtvqhBnganhRepository.deleteAllByIdMuaQdUbtvqh(ids);

        khMuaQdUbtvqhRepository.delete(qOptional.get());
    }

    @Transactional
    public void deleteListId(List<Long> listId){
        for(Long idDel : listId){
            List<KhMuaQdUbtvqhBnganh> boNganh = khMuaQdUbtvqhBnganhRepository.findAllByIdMuaQdUbtvqh(idDel);
            List<Long> listIdBnganh = boNganh.stream().map(KhMuaQdUbtvqhBnganh::getId).collect(Collectors.toList());
            khMuaQdUbtvqhBnganhCtietRepository.deleteAllByIdBoNganhIn(listIdBnganh);
        }
        khMuaQdUbtvqhBnganhRepository.deleteAllByIdMuaQdUbtvqhIn(listId);
        khMuaQdUbtvqhRepository.deleteAllByIdIn(listId);
    }

    public  void export(KhMuaQdUbtvqhSearchReq objReq, HttpServletResponse response) throws Exception{
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhMuaQdUbtvqh> page=this.searchPage(objReq);
        List<KhMuaQdUbtvqh> data=page.getContent();

        String title="Danh sách nghị quyết của Ủy ban thường vụ Quốc hội";
        String[] rowsName=new String[]{"STT","Số nghị quyết","Năm nghị quyết","Ngày nghị quyết","Trích yếu","Trạng thái"};
        String fileName="danh-sach-nghi-quyet-ủy-ban-thường-vu-quốc-hội.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs=null;
        for (int i=0;i<data.size();i++){
            KhMuaQdUbtvqh dx=data.get(i);
            objs=new Object[rowsName.length];
            objs[0]=i;
            objs[1]=dx.getSoQd();
            objs[2]=dx.getNamQd();
            objs[3]=dx.getNgayQd();
            objs[4]=dx.getTrichYeu();
            objs[5]=dx.getTrangThai();
            dataList.add(objs);

        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }

    public KhMuaQdUbtvqh approve(StatusReq stReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (StringUtils.isEmpty(stReq.getId())){
            throw new Exception("Không tìm thấy dữ liệu");
        }

        Optional<KhMuaQdUbtvqh> optional = khMuaQdUbtvqhRepository.findById(Long.valueOf(stReq.getId()));
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

        KhMuaQdUbtvqh createCheck = khMuaQdUbtvqhRepository.save(optional.get());

        return createCheck;
    }


}
