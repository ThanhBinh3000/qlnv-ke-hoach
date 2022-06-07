package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.KeHoachLuongThucMuoi;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface KeHoachLuongThucMuoiRepository extends CrudRepository<KeHoachLuongThucMuoi, Long> {
	List<KeHoachLuongThucMuoi> findByCtkhnId(Long ctkhnId);

	List<KeHoachLuongThucMuoi> findByCtkhnIdAndDonViIdAndVatTuIdIn(Long ctkhnId, Long donViId, Set<Long> vatTuIds);

	List<KeHoachLuongThucMuoi> findByCtkhnIdIn(Collection<Long> ctkhnIds);
}
