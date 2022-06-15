package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcLtVtCt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DxDcLtVtCtRepository extends JpaRepository<DxDcLtVtCt, Long> {

    List<DxDcLtVtCt> findByDxDcLtVtIdIn(Collection<Long> dxDcLtVtIds);
}
