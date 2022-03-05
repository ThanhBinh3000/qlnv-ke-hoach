package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.entities.KeHoachLuongThucMuoi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KeHoachLuongThucMuoiRepository extends CrudRepository<KeHoachLuongThucMuoi, Long> {
	List<KeHoachLuongThucMuoi> findByCtkhnId(Long ctkhnId);
}
