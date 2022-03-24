package com.tcdt.qlnvkhoachphi.repository.catalog;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmVattu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface QlnvDmVattuRepository extends CrudRepository<QlnvDmVattu, Long> {
    Set<QlnvDmVattu> findByMaIn(Collection<String> maVatTus);

    Set<QlnvDmVattu> findByIdIn(Collection<Long> vatTuIds);
}
