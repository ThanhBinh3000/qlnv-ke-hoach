package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;


import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganhCt1;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnCapPhiBoNganhCt1Repository extends BaseRepository<KhDnCapPhiBoNganhCt1, Long> {
	void deleteAllByIdIn(Collection<Long> ids);

	List<KhDnCapPhiBoNganhCt1> findByIdIn(List<Long> ids);

	List<KhDnCapPhiBoNganhCt1> findByDnCapPhiIdIn(Collection<Long> dnCapPhiId);

}
