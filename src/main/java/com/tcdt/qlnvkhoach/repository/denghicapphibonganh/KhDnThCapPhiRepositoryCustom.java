package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;

import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnThCapPhi;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnThCapPhiSearchRequest;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonSearchRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhDnThCapPhiRepositoryCustom {
    List<KhDnThCapPhi> search(KhDnThCapPhiSearchRequest req, Pageable pageable);

    int count(KhDnThCapPhiSearchRequest req);
}
