package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagGctQdDcTcdtnn;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagGctQdTcdtnn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhPagGctQdDcTcdtnnRepository extends JpaRepository<KhPagGctQdDcTcdtnn,Long> {

    @Query(value ="SELECT * FROM KH_PAG_GCT_QD_DC_TCDTNN QD_DC WHERE (:namKh IS NULL OR QD_DC.NAM_KE_HOACH = TO_NUMBER(:namKh))"
            +" AND (:soQd IS NULL OR LOWER(QD_DC.SO_QD) LIKE LOWER(CONCAT(CONCAT('%',:soQd),'%' ) ) )"
            +" AND (:ngayKyTu IS NULL OR QD_DC.NGAY_KY >=  TO_DATE(:ngayKyTu,'yyyy-MM-dd'))"
            +" AND (:ngayKyDen IS NULL OR QD_DC.NGAY_KY <= TO_DATE(:ngayKyDen,'yyyy-MM-dd'))"
            +" AND (( :pagType IS NULL AND (QD_DC.LOAI_VTHH LIKE '01%' OR QD_DC.LOAI_VTHH LIKE '04%')) OR (:pagType IS NOT NULL AND QD_DC.LOAI_VTHH LIKE CONCAT(:pagType,'%' )) )"
            +" AND (:trichYeu IS NULL  OR LOWER(QD_DC.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"  + "ORDER BY KLPAG.NGAY_TAO desc,  KLPAG.NGAY_SUA desc "
            , nativeQuery = true)
    Page<KhPagGctQdDcTcdtnn> selectPage(Integer namKh, String soQd, String ngayKyTu, String ngayKyDen, String trichYeu ,String pagType, Pageable pageable);

    Optional<KhPagGctQdDcTcdtnn> findBySoQd(String soQd);

    void deleteAllByIdIn(List<Long> ids);
}
