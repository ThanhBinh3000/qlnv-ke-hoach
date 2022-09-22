package com.tcdt.qlnvkhoach.repository.denghicapphibonganh;


import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganh;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnCapPhiBoNganhRepository extends BaseRepository<KhDnCapPhiBoNganh, Long>, KhDnCapPhiBoNganhRepositoryCustom {
	void deleteAllByIdIn(Collection<Long> ids);

	List<KhDnCapPhiBoNganh> findByIdIn(List<Long> ids);

	List<KhDnCapPhiBoNganh> findByKhDnThIdIn(List<Long> khDnThIds);
}
