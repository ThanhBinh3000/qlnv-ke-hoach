package com.tcdt.qlnvkhoach.repository.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnThCapVonCt1;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnThCapVonCt1Repository extends BaseRepository<KhDnThCapVonCt1, Long> {
    List<KhDnThCapVonCt1> findByKhDnThIdIn(Collection<Long> khDnThIds);

    @Transactional
    @Modifying
    void deleteByKhDnThIdIn(Collection<Long> khDnThIds);
}
