package com.tcdt.qlnvkhoach.service.thongtriduyetydutoan;

import com.tcdt.qlnvkhoach.request.StatusReq;
import com.tcdt.qlnvkhoach.request.thongtriduyetydutoan.TtDuyetYDuToanRequest;
import com.tcdt.qlnvkhoach.request.thongtriduyetydutoan.TtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.thongtriduyetydutoan.TtDuyetYDuToanResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

public interface TtDuyetYDuToanService {
    @Transactional(rollbackOn = Exception.class)
    TtDuyetYDuToanResponse create(TtDuyetYDuToanRequest req) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    TtDuyetYDuToanResponse update(TtDuyetYDuToanRequest req) throws Exception;

    boolean delete(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean deleteMultiple(List<Long> ids) throws Exception;

    Page<TtDuyetYDuToanResponse> search(TtDuyetYDuToanSearchRequest req) throws Exception;

    TtDuyetYDuToanResponse detail(Long id) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    boolean updateStatus(StatusReq stReq) throws Exception;

    boolean exportToExcel(TtDuyetYDuToanSearchRequest req, HttpServletResponse response) throws Exception;
}
