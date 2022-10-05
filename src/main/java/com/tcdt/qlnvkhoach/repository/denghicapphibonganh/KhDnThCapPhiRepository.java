package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;

import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnThCapPhi;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnThCapPhiRepository extends BaseRepository<KhDnThCapPhi, Long>, KhDnThCapPhiRepositoryCustom {
    void deleteAllByIdIn(Collection<Long> ids);

    List<KhDnThCapPhi> findByIdIn(List<Long> ids);
}
