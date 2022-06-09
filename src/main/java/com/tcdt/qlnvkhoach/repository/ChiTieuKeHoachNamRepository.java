package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.ChiTieuKeHoachNam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT COUNT(ct.id) FROM ChiTieuKeHoachNam ct " +
            "WHERE ct.lastest = ?1 AND ct.loaiQuyetDinh = ?2 " +
            "AND (ct.capDvi = ?3 OR (ct.capDvi = ?4 AND ct.trangThai = ?5))")
    Long countByParams(boolean lastest, String loaiQd, String capTongCuc, String capCuc, String trangThai);

    Long countByLastestAndLoaiQuyetDinhAndMaDvi(boolean lastest, String loaiQd, String maDvi);

    @Transactional
    @Modifying
    void deleteByIdIn(Collection<Long> ids);

    @Query(value = "SELECT * FROM CHI_TIEU_KE_HOACH_NAM CT WHERE CT.NAM_KE_HOACH = TO_NUMBER(:namKh) AND CT.MA_DVI = :maDvi AND CT.LASTEST = 1 ", nativeQuery = true)
    ChiTieuKeHoachNam getChiTieuDxKhLcnt(Long namKh,String maDvi);

}
