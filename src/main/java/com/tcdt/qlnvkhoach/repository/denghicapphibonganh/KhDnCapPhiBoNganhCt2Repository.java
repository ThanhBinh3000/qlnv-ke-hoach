package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;


import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganhCt2;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnCapPhiBoNganhCt2Repository extends BaseRepository<KhDnCapPhiBoNganhCt2, Long> {
	void deleteAllByIdIn(Collection<Long> ids);

	List<KhDnCapPhiBoNganhCt2> findByIdIn(List<Long> ids);

	List<KhDnCapPhiBoNganhCt2> findByCapPhiBoNghanhCt1IdIn(Collection<Long> ct1Ids);

}
