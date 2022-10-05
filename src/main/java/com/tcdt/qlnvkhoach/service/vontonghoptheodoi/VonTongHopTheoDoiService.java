package com.tcdt.qlnvkhoach.service.vontonghoptheodoi;

import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.vontonghoptheodoi.VonTongHopTheoDoiRequest;
import com.tcdt.qlnvkhoach.request.vontonghoptheodoi.VonTongHopTheoDoiSearchRequest;
import com.tcdt.qlnvkhoach.response.vontonghoptheodoi.VonTongHopTheoDoiResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

public interface VonTongHopTheoDoiService {
    @Transactional(rollbackOn = Exception.class)
    VonTongHopTheoDoiResponse create(VonTongHopTheoDoiRequest req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    VonTongHopTheoDoiResponse update(VonTongHopTheoDoiRequest req) throws Exception;

    boolean delete(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean deleteMultiple(List<Long> ids) throws Exception;

    Page<VonTongHopTheoDoiResponse> search(VonTongHopTheoDoiSearchRequest req) throws Exception;

    VonTongHopTheoDoiResponse detail(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean updateStatus(StatusReq stReq) throws Exception;

    boolean exportToExcel(VonTongHopTheoDoiSearchRequest req, HttpServletResponse response) throws Exception;
}
