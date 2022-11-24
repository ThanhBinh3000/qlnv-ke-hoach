package com.tcdt.qlnvkhoach.repository.chitieudexuat;

import com.tcdt.qlnvkhoach.entities.ChiTieuDeXuat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface ChiTieuDeXuatRepository extends JpaRepository<ChiTieuDeXuat, Long> {
    @Transactional
    @Modifying
    void deleteByChiTieuId(Long ctId);

    List<ChiTieuDeXuat> findByChiTieuId(Long ctId);

    @Query("SELECT ctdx.chiTieuId, ctdx.dxDcKhnId, dx.soVanBan FROM ChiTieuDeXuat ctdx " +
            "INNER JOIN DxDcKeHoachNam dx ON ctdx.dxDcKhnId = dx.id " +
            "WHERE ctdx.chiTieuId IN ?1")
    List<Object[]> findDxByChiTieuIdIn(Collection<Long> ctIds);

    @Transactional
    @Modifying
    void deleteByChiTieuIdIn(Collection<Long> ctIds);
}
