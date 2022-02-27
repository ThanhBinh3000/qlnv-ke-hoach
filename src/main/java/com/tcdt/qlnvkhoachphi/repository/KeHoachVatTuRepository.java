package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.entities.KeHoachVatTu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeHoachVatTuRepository extends CrudRepository<KeHoachVatTu, Long> {
}
