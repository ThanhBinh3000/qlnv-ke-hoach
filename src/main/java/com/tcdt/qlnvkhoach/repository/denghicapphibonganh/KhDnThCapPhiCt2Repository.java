package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;

import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnThCapPhiCt2;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnThCapPhiCt2Repository extends BaseRepository<KhDnThCapPhiCt2, Long> {

    @Transactional
    void deleteAllByIdIn(Collection<Long> ids);

    List<KhDnThCapPhiCt2> findByIdIn(List<Long> ids);

    void deleteAllByKhDnThCapPhiCt1IdIn(Collection<Long> ids);

    List<KhDnThCapPhiCt2> findByKhDnThCapPhiCt1IdIn(Collection<Long> ct1Ids);
}
