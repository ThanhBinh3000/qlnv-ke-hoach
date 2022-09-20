package com.tcdt.qlnvkhoach.repository.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnThCapVon;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface KhDnThCapVonRepository extends BaseRepository<KhDnThCapVon, Long>, KhDnThCapVonRepositoryCustom {

    @Transactional
    @Modifying
    void deleteByIdIn(Collection<Long> ids);
}
