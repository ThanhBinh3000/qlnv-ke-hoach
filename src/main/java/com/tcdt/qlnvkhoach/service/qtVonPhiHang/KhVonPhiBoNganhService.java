package com.tcdt.qlnvkhoach.service.qtVonPhiHang;
import com.tcdt.qlnvkhoach.enums.TrangThaiDungChungEnum;
import com.tcdt.qlnvkhoach.repository.qtVonPhiHang.KhVonPhiBoNganhRepository;
import com.tcdt.qlnvkhoach.request.search.catalog.qtVonPhiHang.KhVonPhiBnSearchReq;
import com.tcdt.qlnvkhoach.service.BaseService;
import com.tcdt.qlnvkhoach.table.qtVonPhiHang.KhVonPhiBoNganh;
import com.tcdt.qlnvkhoach.util.Contains;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KhVonPhiBoNganhService extends BaseService {
    @Autowired
    private KhVonPhiBoNganhRepository khVonPhiBoNganhRepository;

    public Page<KhVonPhiBoNganh> searchPage(KhVonPhiBnSearchReq objReq) throws Exception {
        Pageable pageable = PageRequest.of(objReq.getPaggingReq().getPage(),
                objReq.getPaggingReq().getLimit(), Sort.by("id").ascending());
        Page<KhVonPhiBoNganh> data = khVonPhiBoNganhRepository.selectPage(
                objReq.getNamQt(),
                Contains.convertDateToString(objReq.getNgayNhapTu()),
                Contains.convertDateToString(objReq.getNgayNhapDen()),
                Contains.convertDateToString(objReq.getNgaySuaTu()),
                Contains.convertDateToString(objReq.getNgaySuaDen()),
                pageable);
        try {
            data.getContent().forEach(f -> {
                f.setTenTrangThai(TrangThaiDungChungEnum.getTenById(f.getTrangThai()));
                f.setTenTrangThaiBtc(TrangThaiDungChungEnum.getTenById(f.getTrangThai()));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }



}
