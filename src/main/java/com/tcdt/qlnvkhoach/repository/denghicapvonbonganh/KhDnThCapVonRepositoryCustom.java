package com.tcdt.qlnvkhoach.repository.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnThCapVon;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnThCapVonSearchRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhDnThCapVonRepositoryCustom {
    List<KhDnThCapVon> search(KhDnThCapVonSearchRequest req, Pageable pageable);

    int count(KhDnThCapVonSearchRequest req);
}
