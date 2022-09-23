package com.tcdt.qlnvkhoach.repository.phitonghoptheodoi;


import com.tcdt.qlnvkhoach.entities.phitonghoptheodoi.PhiTongHopTheoDoi;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface PhiTongHopTheoDoiRepository extends BaseRepository<PhiTongHopTheoDoi, Long>, PhiTongHopTheoDoiRepositoryCustom {
    @Transactional
    @Modifying
    void deleteAllByIdIn(Collection<Long> ids);

    List<PhiTongHopTheoDoi> findByIdIn(List<Long> ids);

    @Query(value = "select max(id) from PhiTongHopTheoDoi")
    Long getMaxId();
}
