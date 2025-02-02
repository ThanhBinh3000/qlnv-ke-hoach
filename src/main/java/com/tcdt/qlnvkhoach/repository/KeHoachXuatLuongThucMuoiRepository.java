package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.KeHoachXuatLuongThucMuoi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KeHoachXuatLuongThucMuoiRepository extends CrudRepository<KeHoachXuatLuongThucMuoi, Long> {
	List<KeHoachXuatLuongThucMuoi> findByKeHoachId(Long id);
	List<KeHoachXuatLuongThucMuoi> findByKeHoachIdIn(Collection<Long> ids);
	List<KeHoachXuatLuongThucMuoi> findByKeHoachIdInAndNamKeHoachIn(Collection<Long> ids, Collection<Integer> namList);
}
