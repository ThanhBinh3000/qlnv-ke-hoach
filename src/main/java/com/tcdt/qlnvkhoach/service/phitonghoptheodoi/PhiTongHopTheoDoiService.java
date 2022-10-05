package com.tcdt.qlnvkhoach.service.phitonghoptheodoi;

import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phitonghoptheodoi.PhiTongHopTheoDoiRequest;
import com.tcdt.qlnvkhoach.request.phitonghoptheodoi.PhiTongHopTheoDoiSearchRequest;
import com.tcdt.qlnvkhoach.response.phitonghoptheodoi.PhiTongHopTheoDoiResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

public interface PhiTongHopTheoDoiService {
    @Transactional(rollbackOn = Exception.class)
    PhiTongHopTheoDoiResponse create(PhiTongHopTheoDoiRequest req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    PhiTongHopTheoDoiResponse update(PhiTongHopTheoDoiRequest req) throws Exception;

    boolean delete(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean deleteMultiple(List<Long> ids) throws Exception;

    Page<PhiTongHopTheoDoiResponse> search(PhiTongHopTheoDoiSearchRequest req) throws Exception;

    PhiTongHopTheoDoiResponse detail(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean updateStatus(StatusReq stReq) throws Exception;

    boolean exportToExcel(PhiTongHopTheoDoiSearchRequest req, HttpServletResponse response) throws Exception;
}
