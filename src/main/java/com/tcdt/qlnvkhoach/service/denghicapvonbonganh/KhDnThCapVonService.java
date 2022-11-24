package com.tcdt.qlnvkhoach.service.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.request.DeleteReq;
import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonSearchRequest;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnThCapVonResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

public interface KhDnThCapVonService {
    @Transactional(rollbackOn = Exception.class)
    KhDnThCapVonResponse create(KhDnThCapVonRequest req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    KhDnThCapVonResponse update(KhDnThCapVonRequest req) throws Exception;

    KhDnThCapVonResponse detail(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean delete(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean updateStatusQd(StatusReq stReq) throws Exception;

    Page<KhDnThCapVonResponse> search(KhDnThCapVonSearchRequest req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean deleteMultiple(DeleteReq req) throws Exception;

    boolean exportToExcel(KhDnThCapVonSearchRequest objReq, HttpServletResponse response) throws Exception;

}
