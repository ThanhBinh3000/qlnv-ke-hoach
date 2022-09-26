package com.tcdt.qlnvkhoach.repository.phithongtriduyetydutoan;


import com.tcdt.qlnvkhoach.entities.phithongtriduyetydutoan.PhiTtDuyetYDuToanCt;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PhiTtDuyetYDuToanCtRepository extends BaseRepository<PhiTtDuyetYDuToanCt, Long> {
    void deleteAllByIdIn(Collection<Long> ids);

    List<PhiTtDuyetYDuToanCt> findByIdIn(List<Long> ids);

    List<PhiTtDuyetYDuToanCt> findByIdTtdydtIn(Collection<Long> deNghiIds);
}
