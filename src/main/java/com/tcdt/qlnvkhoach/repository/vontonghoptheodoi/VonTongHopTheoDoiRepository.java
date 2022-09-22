package com.tcdt.qlnvkhoach.repository.vontonghoptheodoi;


import com.tcdt.qlnvkhoach.entities.vontonghoptheodoi.VonTongHopTheoDoi;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface VonTongHopTheoDoiRepository extends BaseRepository<VonTongHopTheoDoi, Long>, VonTongHopTheoDoiRepositoryCustom {
    @Transactional
    @Modifying
    void deleteAllByIdIn(Collection<Long> ids);

    List<VonTongHopTheoDoi> findByIdIn(List<Long> ids);

    @Query(value = "select max(id) from VonTongHopTheoDoi")
    Long getMaxId();
}
