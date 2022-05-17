package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcLtVt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DxDcLtVtRespository extends JpaRepository<DxDcLtVt, Long> {
    List<DxDcLtVt> findByDxdckhnId(Long dxDcId);

    @Transactional
    @Modifying
    void deleteByDxdckhnId(Long dxDcId);
}
