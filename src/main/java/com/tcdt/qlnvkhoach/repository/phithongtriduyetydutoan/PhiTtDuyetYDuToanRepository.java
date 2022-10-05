package com.tcdt.qlnvkhoach.repository.phithongtriduyetydutoan;


import com.tcdt.qlnvkhoach.entities.phithongtriduyetydutoan.PhiTtDuyetYDuToan;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface PhiTtDuyetYDuToanRepository extends BaseRepository<PhiTtDuyetYDuToan, Long>, PhiTtDuyetYDuToanRepositoryCustom {
    @Transactional
    @Modifying
    void deleteAllByIdIn(Collection<Long> ids);

    List<PhiTtDuyetYDuToan> findByIdIn(List<Long> ids);

    @Query(value = "select max(id) from PhiTtDuyetYDuToan")
    Long getMaxId();
}
