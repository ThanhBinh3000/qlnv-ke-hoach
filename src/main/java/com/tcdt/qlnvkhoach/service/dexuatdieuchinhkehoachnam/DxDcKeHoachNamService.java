package com.tcdt.qlnvkhoach.service.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.object.dexuatdieuchinhkehoachnam.DxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.request.search.catalog.dexuatdieuchinhkehoachnam.SearchDxDcKeHoachNamReq;
import com.tcdt.qlnvkhoach.response.dexuatdieuchinhkehoachnam.DxDcKeHoachNamRes;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

public interface DxDcKeHoachNamService {
    Page<DxDcKeHoachNamRes> search(SearchDxDcKeHoachNamReq req) throws Exception;

    DxDcKeHoachNamRes create(DxDcKeHoachNamReq req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    DxDcKeHoachNamRes update(DxDcKeHoachNamReq req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean delete(Long id) throws Exception;

    DxDcKeHoachNamRes detail(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean updateStatus(StatusReq req) throws Exception;

    DxDcKeHoachNamRes getSoLuongTruocDieuChinh(Long ctkhnId) throws Exception;

    Boolean exportListToExcel(SearchDxDcKeHoachNamReq req, HttpServletResponse response) throws Exception;
}
