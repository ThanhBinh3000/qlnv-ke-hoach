package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.ChiTieuKeHoachNam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface ChiTieuKeHoachNamRepository extends JpaRepository<ChiTieuKeHoachNam, Long>, ChiTieuKeHoachNamRepositoryCustom {
    List<ChiTieuKeHoachNam> findByNamKeHoachAndLastestAndLoaiQuyetDinh(Integer namKh, boolean lastest, String loaiQd);

    List<ChiTieuKeHoachNam> findAllByLoaiQuyetDinh(String loaiQuyetDinh);

    ChiTieuKeHoachNam findByIdAndLoaiQuyetDinh(Long id, String loaiQuyetDinh);

    ChiTieuKeHoachNam findFirstBySoQuyetDinhAndLoaiQuyetDinhAndLastestIsTrue(String id, String loaiQuyetDinh);

    Long countByLastestAndLoaiQuyetDinhAndCapDvi(boolean lastest, String loaiQd, String capDvi);

    Long countByLastestAndLoaiQuyetDinhAndMaDvi(boolean lastest, String loaiQd, String maDvi);

    @Transactional
    @Modifying
    void deleteByIdIn(Collection<Long> ids);
}
