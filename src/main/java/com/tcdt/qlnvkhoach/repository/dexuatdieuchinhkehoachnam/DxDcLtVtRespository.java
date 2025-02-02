package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcLtVt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface DxDcLtVtRespository extends JpaRepository<DxDcLtVt, Long> {
    List<DxDcLtVt> findByDxdckhnId(Long dxDcId);

    @Transactional
    @Modifying
    void deleteByDxdckhnId(Long dxDcId);

    @Transactional
    @Modifying
    void deleteByDxdckhnIdIn(Collection<Long> dxDcIds);

    @Query("SELECT ltVt.id FROM DxDcLtVt ltVt WHERE ltVt.dxdckhnId IN ?1")
    List<Long> findIdByDxdckhnIdIn(Collection<Long> dxDcIds);
}
