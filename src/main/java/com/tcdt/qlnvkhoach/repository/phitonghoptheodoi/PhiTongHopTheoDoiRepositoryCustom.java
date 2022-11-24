package com.tcdt.qlnvkhoach.repository.phitonghoptheodoi;

import com.tcdt.qlnvkhoach.request.phitonghoptheodoi.PhiTongHopTheoDoiSearchRequest;
import com.tcdt.qlnvkhoach.response.phitonghoptheodoi.PhiTongHopTheoDoiResponse;

import java.util.List;

public interface PhiTongHopTheoDoiRepositoryCustom {
    List<PhiTongHopTheoDoiResponse> search(PhiTongHopTheoDoiSearchRequest req);
}
