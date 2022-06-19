package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcLtVtCt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface DxDcLtVtCtRepository extends JpaRepository<DxDcLtVtCt, Long> {

    List<DxDcLtVtCt> findByDxDcLtVtIdIn(Collection<Long> dxDcLtVtIds);

    @Transactional
    @Modifying
    void deleteByDxDcLtVtIdIn(Collection<Long> dxDcLtVtIds);
}
