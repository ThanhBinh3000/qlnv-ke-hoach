package com.tcdt.qlnvkhoach.repository.vonthongtriduyetydutoan;


import com.tcdt.qlnvkhoach.entities.vonthongtriduyetydutoan.VonTtDuyetYDuToan;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface VonTtDuyetYDuToanRepository extends BaseRepository<VonTtDuyetYDuToan, Long>, VonTtDuyetYDuToanRepositoryCustom {
    @Transactional
    @Modifying
    void deleteAllByIdIn(Collection<Long> ids);

    List<VonTtDuyetYDuToan> findByIdIn(List<Long> ids);

    @Query(value = "select max(id) from VonTtDuyetYDuToan")
    Long getMaxId();
}
