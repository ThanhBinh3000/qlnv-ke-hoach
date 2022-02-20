package com.tcdt.qlnvkhoachphi.repository.catalog;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmKHoachVon;

@Repository
public interface QlnvDmKHoachVonRepository extends CrudRepository<QlnvDmKHoachVon, Long> {
}
