package com.tcdt.qlnvkhoach.repository.thongtriduyetydutoan;


import com.tcdt.qlnvkhoach.entities.thongtriduyetydutoan.TtDuyetYDuToan;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface TtDuyetYDuToanRepository extends BaseRepository<TtDuyetYDuToan, Long>, TtDuyetYDuToanRepositoryCustom {
    @Transactional
    @Modifying
    void deleteAllByIdIn(Collection<Long> ids);

    List<TtDuyetYDuToan> findByIdIn(List<Long> ids);

    @Query(value = "select max(id) from TtDuyetYDuToan")
    Long getMaxId();
}
