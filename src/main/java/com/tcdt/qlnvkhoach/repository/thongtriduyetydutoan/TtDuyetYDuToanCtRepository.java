package com.tcdt.qlnvkhoach.repository.thongtriduyetydutoan;


import com.tcdt.qlnvkhoach.entities.thongtriduyetydutoan.TtDuyetYDuToanCt;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TtDuyetYDuToanCtRepository extends BaseRepository<TtDuyetYDuToanCt, Long> {
    void deleteAllByIdIn(Collection<Long> ids);

    List<TtDuyetYDuToanCt> findByIdIn(List<Long> ids);

    List<TtDuyetYDuToanCt> findByIdTtdydtIn(Collection<Long> deNghiIds);
}
