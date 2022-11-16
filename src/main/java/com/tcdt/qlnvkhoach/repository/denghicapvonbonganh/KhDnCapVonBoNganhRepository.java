package com.tcdt.qlnvkhoach.repository.denghicapvonbonganh;


import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnCapVonBoNganhRepository extends BaseRepository<KhDnCapVonBoNganh, Long>, KhDnCapVonBoNganhRepositoryCustom {
    void deleteAllByIdIn(Collection<Long> ids);

    List<KhDnCapVonBoNganh> findByIdIn(List<Long> ids);

    List<KhDnCapVonBoNganh> findByKhDnThIdIn(List<Long> khDnThIds);
}
