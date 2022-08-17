package com.tcdt.qlnvkhoach.service.phuongangia;

import com.google.common.collect.Lists;
import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.entities.phuongangia.*;
import com.tcdt.qlnvkhoach.enums.PAGTrangThaiTHEnum;
import com.tcdt.qlnvkhoach.enums.PhuongAnGiaEnum;
import com.tcdt.qlnvkhoach.enums.TrangThaiEnum;
import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagTongHopCTietRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPagTongHopRepository;
import com.tcdt.qlnvkhoach.repository.phuongangia.KhLtPhuongAnGiaRepository;
import com.tcdt.qlnvkhoach.request.PaggingReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagTongHopFilterReq;
import com.tcdt.qlnvkhoach.request.phuongangia.KhLtPagTongHopReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.service.SecurityContextService;
import com.tcdt.qlnvkhoach.table.UserInfo;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
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
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KhLtTongHopPagService extends BaseService {
    @Autowired
    private KhLtPagTongHopRepository khLtPagTongHopRepository;

    @Autowired
    private KhLtPhuongAnGiaRepository khLtPhuongAnGiaRepository;

    @Autowired
    private QlnvDmDonviRepository qlnvDmDonviRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private KhLtPagTongHopCTietRepository khLtPagTongHopCTietRepository;

    public Page<KhLtPagTongHop> searchPage(KhLtPagTongHopSearchReq objReq) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhLtPagTongHop> data = khLtPagTongHopRepository.selectPage(
                objReq.getNamKh(),
                objReq.getLoaiHh(),
                Contains.convertDateToString(objReq.getNgayKyTu()),
                Contains.convertDateToString(objReq.getNgayKyDen()),
                objReq.getNoiDung(),
                userInfo.getCapDvi().equals("1") ? null : userInfo.getDvql(),
                pageable);
        List<Long> khLtPagTHIds = data.getContent().stream().map(KhLtPagTongHop::getId).collect(Collectors.toList());
        List<KhLtPagTongHopCTiet> lChitiet = khLtPagTongHopCTietRepository.findByPagThIdIn(khLtPagTHIds);
        Map<Long, List<KhLtPagTongHopCTiet>> mPagTHCtiet = lChitiet.stream().collect(Collectors.groupingBy(o -> o.getPagThId()));
        for (KhLtPagTongHop khLtPagTongHop : data.getContent()) {
            khLtPagTongHop.setPagChitiets(mPagTHCtiet.get(khLtPagTongHop.getId()));
        }
        return data;
    }

    public KhLtPagTongHop tongHopData(KhLtPagTongHopFilterReq objReq, HttpServletRequest req) throws Exception {
        List<KhLtPhuongAnGia> listPagTH = khLtPhuongAnGiaRepository.listTongHop(objReq.getLoaiVthh(), objReq.getChungloaiVthh(), objReq.getNamKhoach(), objReq.getLoaiGia(), objReq.getNgayDxuatTu(), objReq.getNgayDxuatDen());
        if (listPagTH.isEmpty()) {
            throw new Exception("Không tìm thấy data tổng hợp");
        }
        List<Long> khLtPagIds = listPagTH.stream().map(KhLtPhuongAnGia::getId).collect(Collectors.toList());
        if (khLtPagIds.isEmpty()) {
            throw new Exception("Không tìm thấy data tổng hợp");
        }
        KhLtPagTongHop pagTH = new KhLtPagTongHop();
        pagTH.setNamTongHop(Long.valueOf(objReq.getNamKhoach()));
        pagTH.setCloaiVthh(objReq.getChungloaiVthh());
        pagTH.setLoaiGia(objReq.getLoaiGia());
        pagTH.setLoaiVthh(objReq.getLoaiVthh());
        Map<Long, List<Object[]>> khLtMinMaxKQThamDinhs = khLtPhuongAnGiaRepository.listPagWithDonGia(PhuongAnGiaEnum.KET_QUA_THAM_DINH_GIA.getValue(), khLtPagIds)
                .stream().collect(Collectors.groupingBy(o -> (Long) o[0]));
        Map<Long, List<Object[]>> khLtMinMaxKQKhaoSats = khLtPhuongAnGiaRepository.listPagWithDonGia(PhuongAnGiaEnum.KET_QUA_KHAO_SAT_GIA_THI_TRUONG.getValue(), khLtPagIds)
                .stream().collect(Collectors.groupingBy(o -> (Long) o[0]));
        List<KhLtPagTongHopCTiet> lChitiet = new ArrayList<>();
        for (KhLtPhuongAnGia pag : listPagTH) {
            List<Object[]> ketquaTDs = khLtMinMaxKQThamDinhs.get(pag.getId());
            List<Object[]> ketquaKSs = khLtMinMaxKQKhaoSats.get(pag.getId());
//            set min max gia khao sat
            if (StringUtils.isEmpty(pagTH.getGiaKsTtTu()) || (ketquaKSs != null && !ketquaKSs.isEmpty() && pagTH.getGiaKsTtTu().compareTo((BigDecimal) ketquaKSs.get(0)[1]) > 0)) {
                pagTH.setGiaKsTtTu((ketquaKSs != null && !ketquaKSs.isEmpty()) ? (BigDecimal) ketquaKSs.get(0)[1] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaKsTtDen()) || (ketquaKSs != null && !ketquaKSs.isEmpty() && pagTH.getGiaKsTtDen().compareTo((BigDecimal) ketquaKSs.get(0)[2]) > 0)) {
                pagTH.setGiaKsTtDen((ketquaKSs != null && !ketquaKSs.isEmpty()) ? (BigDecimal) ketquaKSs.get(0)[2] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaKsTtVatTu()) || (ketquaKSs != null && !ketquaKSs.isEmpty() && pagTH.getGiaKsTtVatTu().compareTo((BigDecimal) ketquaKSs.get(0)[3]) > 0)) {
                pagTH.setGiaKsTtVatTu((ketquaKSs != null && !ketquaKSs.isEmpty()) ? (BigDecimal) ketquaKSs.get(0)[3] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaKsTtVatDen()) || (ketquaKSs != null && !ketquaKSs.isEmpty() && pagTH.getGiaKsTtVatDen().compareTo((BigDecimal) ketquaKSs.get(0)[4]) > 0)) {
                pagTH.setGiaKsTtVatDen((ketquaKSs != null && !ketquaKSs.isEmpty()) ? (BigDecimal) ketquaKSs.get(0)[4] : null);
            }
//            set min max gia tham dinh
            if (StringUtils.isEmpty(pagTH.getGiaTdTu()) || (ketquaTDs != null && !ketquaTDs.isEmpty() && pagTH.getGiaTdTu().compareTo((BigDecimal) ketquaTDs.get(0)[1]) > 0)) {
                pagTH.setGiaTdTu((ketquaTDs != null && !ketquaTDs.isEmpty()) ? (BigDecimal) ketquaTDs.get(0)[1] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaTdDen()) || (ketquaTDs != null && !ketquaTDs.isEmpty() && pagTH.getGiaTdDen().compareTo((BigDecimal) ketquaTDs.get(0)[2]) > 0)) {
                pagTH.setGiaTdDen((ketquaTDs != null && !ketquaTDs.isEmpty()) ? (BigDecimal) ketquaTDs.get(0)[2] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaTdVatTu()) || (ketquaTDs != null && !ketquaTDs.isEmpty() && pagTH.getGiaTdVatTu().compareTo((BigDecimal) ketquaTDs.get(0)[3]) > 0)) {
                pagTH.setGiaTdVatTu((ketquaTDs != null && !ketquaTDs.isEmpty()) ? (BigDecimal) ketquaTDs.get(0)[3] : null);
            }
            if (StringUtils.isEmpty(pagTH.getGiaTdVatDen()) || (ketquaTDs != null && !ketquaTDs.isEmpty() && pagTH.getGiaTdVatDen().compareTo((BigDecimal) ketquaTDs.get(0)[4]) > 0)) {
                pagTH.setGiaTdVatTu((ketquaTDs != null && !ketquaTDs.isEmpty()) ? (BigDecimal) ketquaTDs.get(0)[4] : null);
            }
            //set min max gia de nghi
            if (StringUtils.isEmpty(pagTH.getGiaDnTu()) || pagTH.getGiaDnTu().compareTo(pag.getGiaDeNghi()) > 0) {
                pagTH.setGiaDnTu(pag.getGiaDeNghi());
            }
            if (StringUtils.isEmpty(pagTH.getGiaDnDen()) || pagTH.getGiaDnDen().compareTo(pag.getGiaDeNghi()) < 0) {
                pagTH.setGiaDnDen(pag.getGiaDeNghi());
            }
            KhLtPagTongHopCTiet cTiet = new KhLtPagTongHopCTiet();
            cTiet.setSoDx(pag.getSoDeXuat());
            cTiet.setSoLuong(pag.getSoLuong());
            cTiet.setMaDvi(pag.getMaDvi());
            cTiet.setGiaDn(pag.getGiaDeNghi());
            cTiet.setGiaDnVat(pag.getGiaDeNghiVat());
            cTiet.setPagId(pag.getId());
            lChitiet.add(cTiet);
        }
        List<String> maDvis = lChitiet.stream().map(KhLtPagTongHopCTiet::getMaDvi).collect(Collectors.toList());
        List<QlnvDmDonvi> dvis = qlnvDmDonviRepository.findByMaDviIn(maDvis);
        Map<String, QlnvDmDonvi> listDvi = dvis.stream()
                .collect(Collectors.toMap(QlnvDmDonvi::getMaDvi, Function.identity()));
        lChitiet.forEach(f -> {
            f.setTenDvi(listDvi.get(f.getMaDvi()).getTenDvi());
        });
        pagTH.setPagChitiets(lChitiet);
        return pagTH;
    }

    @Transactional(rollbackFor = Exception.class)
    public KhLtPagTongHop create(KhLtPagTongHopReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        KhLtPagTongHop pagTH = mapper.map(req, KhLtPagTongHop.class);
        pagTH.setNguoiTaoId(userInfo.getId());
        pagTH.setNgayTao(LocalDate.now());
        pagTH.setNgayTongHop(LocalDate.now());
        pagTH.setMaDvi(userInfo.getDvql());
        pagTH.setCapDvi(userInfo.getCapDvi());
        pagTH.setTrangThai(Contains.MOI_TAO);
        pagTH.setTrangThaiTH(Contains.CHUA_QUYET_DINH);
        pagTH.setTtToTrinh(Contains.CHUATAOTOTRINH);
        KhLtPagTongHop pagThSave = khLtPagTongHopRepository.save(pagTH);
        List<KhLtPagTongHopCTiet> pagTGChiTiets = req.getPagChitiets().stream().map(item -> {
            KhLtPagTongHopCTiet pagThChiTiet = mapper.map(item, KhLtPagTongHopCTiet.class);
            pagThChiTiet.setPagThId(pagThSave.getId());
            return pagThChiTiet;
        }).collect(Collectors.toList());
        khLtPagTongHopCTietRepository.saveAll(pagTGChiTiets);
        pagThSave.setPagChitiets(pagTGChiTiets);
        /**
         * update lại stt của dx pag
         */
        List<Long> pagIds = req.getPagChitiets().stream().map(KhLtPagTongHopCTiet::getPagId).collect(Collectors.toList());
        List<KhLtPhuongAnGia> lPags = khLtPhuongAnGiaRepository.findByIdIn(pagIds);
        List<KhLtPhuongAnGia> pagDetails = lPags.stream().map(item -> {
            item.setTrangThaiTh(Contains.DA_TH);
            return item;
        }).collect(Collectors.toList());
        khLtPhuongAnGiaRepository.saveAll(pagDetails);
        return pagThSave;
    }


    @Transactional(rollbackFor = Exception.class)
    public KhLtPagTongHop createToTrinh(KhLtPagTongHopReq req) throws Exception {
        UserInfo userInfo = SecurityContextService.getUser();
        if (userInfo == null) throw new Exception("Bad request.");
        Optional<KhLtPagTongHop>  optinal = khLtPagTongHopRepository.findById(req.getId());
        if(!optinal.isPresent()){
            throw new Exception("Không tìm thấy bản ghi tổng hợp phương án giá");
        }
        KhLtPagTongHop pagTH = optinal.get();
        if(khLtPagTongHopRepository.findByMaToTrinh(req.getMaToTrinh()).get() != null){
            throw new Exception("Số tờ trình đã tồn tại");
        }
        pagTH.setNgaySua(LocalDate.now());
        pagTH.setNguoiSuaId(userInfo.getId());
        pagTH.setTrichYeu(req.getTrichYeu());
        pagTH.setMaToTrinh(req.getMaToTrinh());
        pagTH.setTtGiaDn(req.getTtGiaDn());
        pagTH.setTtGiaDnVat(req.getTtGiaDnVat());
        pagTH.setGhiChu(req.getGhiChu());
        pagTH.setTtToTrinh(Contains.DATAOTOTRINH);
        KhLtPagTongHop pagThSave = khLtPagTongHopRepository.save(pagTH);
        return pagThSave;
    }

    public  void exportPagTH(KhLtPagTongHopSearchReq objReq, HttpServletResponse response) throws Exception{
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        objReq.setPaggingReq(paggingReq);
        Page<KhLtPagTongHop> page=this.searchPage(objReq);
        List<KhLtPagTongHop> data=page.getContent();

        String title="Danh sách tổng hợp phương án giá";
        String[] rowsName=new String[]{"STT","Mã tổng hợp","Ngày tổng hợp","Nội dung tổng hợp","Năm kế hoạch","Loại hàng hóa","Chủng loại hàng hóa","Loại giá","Trạng thái tổng hợp","Mã tờ trình","Trạng thái"};
        String fileName="danh-sach-tong-hop-phuong-an-gia.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs=null;
        for (int i=0;i<data.size();i++){
            KhLtPagTongHop dx=data.get(i);
            objs=new Object[rowsName.length];
            objs[0]=i;
            objs[1]=dx.getId();
            objs[2]=dx.getNgayTongHop();
            objs[3]=dx.getNoiDung();
            objs[4]=dx.getNamTongHop();
            objs[5]=Contains.getLoaiVthh(dx.getLoaiVthh());
            objs[6]=dx.getCloaiVthh();
            objs[7]=dx.getLoaiGia();
            objs[8]=Contains.getThTongHop(dx.getTrangThaiTH());
            objs[9]=dx.getMaToTrinh();
            objs[10]=Contains.mapTrangThaiPheDuyet.get(dx.getLoaiGia());
            dataList.add(objs);
        }
        ExportExcel ex =new ExportExcel(title,fileName,rowsName,dataList,response);
        ex.export();
    }

    @Transactional
    public KhLtPagTongHop detailPagTH(String id) throws  Exception {
        Optional<KhLtPagTongHop> qOptional = khLtPagTongHopRepository.findById(Long.parseLong(id));
        if (!qOptional.isPresent()) {
            throw new Exception("Tổng hợp phương án giá không tồn tại");
        }
        KhLtPagTongHop data = qOptional.get();
        List<Long> ids = new ArrayList<>();
        ids.add(data.getId());
        List<KhLtPagTongHopCTiet> listPagTHChiTiets = khLtPagTongHopCTietRepository.findByPagThIdIn(ids);
        if(listPagTHChiTiets.size() > 0){
            data.setPagChitiets(listPagTHChiTiets);
        }
        return data;
    }

    @Transactional
    public void delete(Long ids) throws Exception{
        Optional<KhLtPagTongHop> qOptional=khLtPagTongHopRepository.findById(ids);
        if(!qOptional.isPresent()){
            throw new UserPrincipalNotFoundException("Id không tồn tại");
        }
        if(qOptional.get().getTrangThai().equals(Contains.DUYET)){
            throw new Exception("Bản ghi có trạng thái đã duyệt,không được xóa.");
        }
        List<Long> pagTHIds = new ArrayList<>();
        pagTHIds.add(ids);
        List<KhLtPagTongHopCTiet> khLtPagTongHopCTiet = khLtPagTongHopCTietRepository.findByPagThIdIn(pagTHIds);
        if (!CollectionUtils.isEmpty(khLtPagTongHopCTiet)) {
            khLtPagTongHopCTietRepository.deleteAll(khLtPagTongHopCTiet);
        }
        khLtPagTongHopRepository.delete(qOptional.get());
    }


}
