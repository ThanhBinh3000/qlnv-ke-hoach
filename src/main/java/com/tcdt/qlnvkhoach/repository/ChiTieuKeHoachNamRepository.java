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
    List<ChiTieuKeHoachNam> findByNamKeHoachAndLatestAndLoaiQuyetDinhAndMaDviAndQdGocId(Integer namKh, boolean latest, String loaiQd, String maDvi, Long qdGocId);

    List<ChiTieuKeHoachNam> findByNamKeHoachAndLatestAndLoaiQuyetDinhAndCapDvi(Integer namKh, boolean latest, String loaiQd, String capDvi);

    ChiTieuKeHoachNam findByIdAndLoaiQuyetDinh(Long id, String loaiQuyetDinh);

    ChiTieuKeHoachNam findFirstBySoQuyetDinhAndLoaiQuyetDinhAndLatestIsTrue(String id, String loaiQuyetDinh);

    @Transactional
    @Modifying
    void deleteByIdIn(Collection<Long> ids);

    @Query(value = "SELECT * FROM KH_CHI_TIEU_KE_HOACH_NAM CT WHERE CT.NAM_KE_HOACH = TO_NUMBER(:namKh) AND CT.MA_DVI = :maDvi AND CT.LATEST = 1 ", nativeQuery = true)
    ChiTieuKeHoachNam getChiTieuDxKhLcnt(Long namKh,String maDvi);

    List<ChiTieuKeHoachNam> findByNamKeHoachAndLatestAndLoaiQuyetDinhAndMaDvi(Integer namKh, boolean latest, String loaiQd, String maDvi);

    ChiTieuKeHoachNam findFristByQdGocIdAndLoaiQuyetDinhAndLatestIsTrue(Long qdGocId, String loaiQd);


    @Query(value = "SELECT * FROM KH_CHI_TIEU_KE_HOACH_NAM CT WHERE CT.NAM_KE_HOACH = TO_NUMBER(:namKh) AND ( EXISTS (SELECT MUOI.ID FROM KH_CHI_TIEU_LT_MUOI MUOI WHERE MUOI.CTKHN_ID = CT.ID  AND MUOI.MA_DVI = :maDvi) \n" +
            "  OR  EXISTS (SELECT VT.ID FROM KH_CHI_TIEU_VAT_TU VT WHERE VT.CTKHN_ID = CT.ID  AND VT.MA_DVI = :maDvi)\n" +
            ") AND CT.LATEST = 1 ", nativeQuery = true)
    ChiTieuKeHoachNam getChiTieuDxKhLcntByMadvi(Long namKh,String maDvi);

    @Query(value = "SELECT * FROM KH_CHI_TIEU_KE_HOACH_NAM CT WHERE CT.NAM_KE_HOACH = TO_NUMBER(:namKh) AND ( EXISTS (SELECT MUOI.ID FROM KH_CHI_TIEU_LT_MUOI MUOI WHERE MUOI.CTKHN_ID = CT.ID  AND MUOI.MA_DVI = :maDvi) " +
            "  OR  EXISTS (SELECT VT.ID FROM KH_CHI_TIEU_VAT_TU VT WHERE VT.CTKHN_ID = CT.ID  AND VT.MA_DVI = :maDvi)" +
            ")", nativeQuery = true)
    List<ChiTieuKeHoachNam> getChiTieuDxKhLcntByPag(Long namKh,String maDvi);

}
