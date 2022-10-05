package com.tcdt.qlnvkhoach.repository.phithongtriduyetydutoan;

import com.tcdt.qlnvkhoach.request.phithongtriduyetydutoan.PhiTtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.phithongtriduyetydutoan.PhiTtDuyetYDuToanResponse;

import java.util.List;

public interface PhiTtDuyetYDuToanRepositoryCustom {
    List<PhiTtDuyetYDuToanResponse> search(PhiTtDuyetYDuToanSearchRequest req);
}
