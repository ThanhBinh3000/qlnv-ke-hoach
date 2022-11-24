package com.tcdt.qlnvkhoach.service.phithongtriduyetydutoan;

import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.phithongtriduyetydutoan.PhiTtDuyetYDuToanRequest;
import com.tcdt.qlnvkhoach.request.phithongtriduyetydutoan.PhiTtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.phithongtriduyetydutoan.PhiTtDuyetYDuToanResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

public interface PhiTtDuyetYDuToanService {
    @Transactional(rollbackOn = Exception.class)
    PhiTtDuyetYDuToanResponse create(PhiTtDuyetYDuToanRequest req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    PhiTtDuyetYDuToanResponse update(PhiTtDuyetYDuToanRequest req) throws Exception;

    boolean delete(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean deleteMultiple(List<Long> ids) throws Exception;

    Page<PhiTtDuyetYDuToanResponse> search(PhiTtDuyetYDuToanSearchRequest req) throws Exception;

    PhiTtDuyetYDuToanResponse detail(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean updateStatus(StatusReq stReq) throws Exception;

    boolean exportToExcel(PhiTtDuyetYDuToanSearchRequest req, HttpServletResponse response) throws Exception;
}
