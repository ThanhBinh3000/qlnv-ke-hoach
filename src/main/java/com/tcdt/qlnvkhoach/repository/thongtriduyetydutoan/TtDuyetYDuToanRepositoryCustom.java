package com.tcdt.qlnvkhoach.repository.thongtriduyetydutoan;

import com.tcdt.qlnvkhoach.request.thongtriduyetydutoan.TtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.thongtriduyetydutoan.TtDuyetYDuToanResponse;

import java.util.List;

public interface TtDuyetYDuToanRepositoryCustom {
    List<TtDuyetYDuToanResponse> search(TtDuyetYDuToanSearchRequest req);
}
