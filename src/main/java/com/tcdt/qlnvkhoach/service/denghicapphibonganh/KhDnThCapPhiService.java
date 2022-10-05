package com.tcdt.qlnvkhoach.service.denghicapphibonganh;

import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiRequest;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnThCapPhiResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

public interface KhDnThCapPhiService {
    @Transactional(rollbackOn = Exception.class)
    KhDnThCapPhiResponse create(KhDnThCapPhiRequest req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    KhDnThCapPhiResponse update(KhDnThCapPhiRequest req) throws Exception;

    KhDnThCapPhiResponse detail(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean delete(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean updateStatusQd(StatusReq stReq) throws Exception;

    Page<KhDnThCapPhiResponse> search(KhDnThCapPhiSearchRequest req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean deleteMultiple(DeleteReq req) throws Exception;

    boolean exportToExcel(KhDnThCapPhiSearchRequest objReq, HttpServletResponse response) throws Exception;
}
