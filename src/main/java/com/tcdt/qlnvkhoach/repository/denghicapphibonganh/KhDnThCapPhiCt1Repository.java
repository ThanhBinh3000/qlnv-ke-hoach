package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;

import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnThCapPhiCt1;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnThCapPhiCt1Repository extends BaseRepository<KhDnThCapPhiCt1, Long> {

    void deleteAllByIdIn(Collection<Long> ids);

    List<KhDnThCapPhiCt1> findByIdIn(List<Long> ids);

    List<KhDnThCapPhiCt1> findByKhDnThCapPhiIdIn(Collection<Long> dnCapPhiId);

    List<KhDnThCapPhiCt1> findByKhDnThCapPhiIdInAndLoai(Collection<Long> dnCapPhiId, String loai);
}
