package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.entities.KeHoachLuongThucMuoi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeHoachLuongThucMuoiRepository extends CrudRepository<KeHoachLuongThucMuoi, Long> {
}
