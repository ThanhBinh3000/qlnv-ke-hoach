package com.tcdt.qlnvkhoach.repository.vonthongtriduyetydutoan;

import com.tcdt.qlnvkhoach.request.vonthongtriduyetydutoan.VonTtDuyetYDuToanSearchRequest;
import com.tcdt.qlnvkhoach.response.vonthongtriduyetydutoan.VonTtDuyetYDuToanResponse;

import java.util.List;

public interface VonTtDuyetYDuToanRepositoryCustom {
    List<VonTtDuyetYDuToanResponse> search(VonTtDuyetYDuToanSearchRequest req);
}
