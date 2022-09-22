package com.tcdt.qlnvkhoach.repository.vontonghoptheodoi;

import com.tcdt.qlnvkhoach.request.vontonghoptheodoi.VonTongHopTheoDoiSearchRequest;
import com.tcdt.qlnvkhoach.response.vontonghoptheodoi.VonTongHopTheoDoiResponse;

import java.util.List;

public interface VonTongHopTheoDoiRepositoryCustom {
    List<VonTongHopTheoDoiResponse> search(VonTongHopTheoDoiSearchRequest req);
}
