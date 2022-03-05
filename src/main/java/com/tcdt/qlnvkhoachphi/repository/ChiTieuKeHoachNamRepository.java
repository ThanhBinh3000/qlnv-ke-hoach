package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.entities.ChiTieuKeHoachNam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTieuKeHoachNamRepository extends CrudRepository<ChiTieuKeHoachNam, Long> {
}
