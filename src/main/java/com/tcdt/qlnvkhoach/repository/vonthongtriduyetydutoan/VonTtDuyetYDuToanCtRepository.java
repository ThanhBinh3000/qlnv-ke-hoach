package com.tcdt.qlnvkhoach.repository.vonthongtriduyetydutoan;


import com.tcdt.qlnvkhoach.entities.vonthongtriduyetydutoan.VonTtDuyetYDuToanCt;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface VonTtDuyetYDuToanCtRepository extends BaseRepository<VonTtDuyetYDuToanCt, Long> {
    void deleteAllByIdIn(Collection<Long> ids);

    List<VonTtDuyetYDuToanCt> findByIdIn(List<Long> ids);

    List<VonTtDuyetYDuToanCt> findByIdTtdydtIn(Collection<Long> deNghiIds);
}
