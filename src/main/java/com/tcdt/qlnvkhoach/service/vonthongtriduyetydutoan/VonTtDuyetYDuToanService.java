package com.tcdt.qlnvkhoach.service.vonthongtriduyetydutoan;

import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.vonthongtriduyetydutoan.VonTtDuyetYDuToanRequest;
import com.tcdt.qlnvkhoach.request.vonthongtriduyetydutoan.VonTtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.vonthongtriduyetydutoan.VonTtDuyetYDuToanResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

public interface VonTtDuyetYDuToanService {
    @Transactional(rollbackOn = Exception.class)
    VonTtDuyetYDuToanResponse create(VonTtDuyetYDuToanRequest req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    VonTtDuyetYDuToanResponse update(VonTtDuyetYDuToanRequest req) throws Exception;

    boolean delete(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean deleteMultiple(List<Long> ids) throws Exception;

    Page<VonTtDuyetYDuToanResponse> search(VonTtDuyetYDuToanSearchRequest req) throws Exception;

    VonTtDuyetYDuToanResponse detail(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean updateStatus(StatusReq stReq) throws Exception;

    boolean exportToExcel(VonTtDuyetYDuToanSearchRequest req, HttpServletResponse response) throws Exception;
}
