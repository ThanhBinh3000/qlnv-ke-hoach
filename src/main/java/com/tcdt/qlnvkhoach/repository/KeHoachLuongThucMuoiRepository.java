package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.KeHoachLuongThucMuoi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface KeHoachLuongThucMuoiRepository extends CrudRepository<KeHoachLuongThucMuoi, Long> {
	List<KeHoachLuongThucMuoi> findByCtkhnId(Long ctkhnId);

	List<KeHoachLuongThucMuoi> findByCtkhnIdAndDonViIdAndVatTuIdIn(Long ctkhnId, Long donViId, Set<Long> vatTuIds);
}
