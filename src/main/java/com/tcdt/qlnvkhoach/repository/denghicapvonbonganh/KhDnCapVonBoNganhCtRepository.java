package com.tcdt.qlnvkhoach.repository.denghicapvonbonganh;


import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganhCt;
import com.tcdt.qlnvkhoach.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhDnCapVonBoNganhCtRepository extends BaseRepository<KhDnCapVonBoNganhCt, Long> {
	void deleteAllByIdIn(Collection<Long> ids);

	List<KhDnCapVonBoNganhCt> findByIdIn(List<Long> ids);

	List<KhDnCapVonBoNganhCt> findByDeNghiCapVonBoNganhIdIn(Collection<Long> deNghiIds);
}
