package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.entities.ChiTieuKeHoachNam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTieuKeHoachNamRepository extends ChiTieuKeHoachNamRepositoryCustom, CrudRepository<ChiTieuKeHoachNam, Long> {
}
