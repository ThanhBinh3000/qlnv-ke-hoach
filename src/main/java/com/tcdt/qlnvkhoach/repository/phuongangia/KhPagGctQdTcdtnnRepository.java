package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagGctQdTcdtnn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhPagGctQdTcdtnnRepository extends JpaRepository<KhPagGctQdTcdtnn,Long> {

    @Query(value ="SELECT * FROM KH_PAG_GCT_QD_TCDTNN KLPAG WHERE (:namKh IS NULL OR KLPAG.NAM_KE_HOACH = TO_NUMBER(:namKh))"
            +" AND (:soQd IS NULL OR LOWER(KLPAG.SO_QD) LIKE LOWER(CONCAT(CONCAT('%',:soQd),'%' ) ) )"
            +" AND (:ngayKyTu IS NULL OR KLPAG.NGAY_KY >=  TO_DATE(:ngayKyTu,'yyyy-MM-dd'))"
            +" AND (:ngayKyDen IS NULL OR KLPAG.NGAY_KY <= TO_DATE(:ngayKyDen,'yyyy-MM-dd'))"
            +" AND (:pagType IS NULL OR KLPAG.LOAI_VTHH LIKE CONCAT(:pagType,'%' ) )"
            +" AND (:trichYeu IS NULL  OR LOWER(KLPAG.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"
            , nativeQuery = true)
    Page<KhPagGctQdTcdtnn> selectPage(Integer namKh, String soQd, String ngayKyTu, String ngayKyDen, String trichYeu ,String pagType, Pageable pageable);

    Optional<KhPagGctQdTcdtnn> findBySoQd(String soQd);

    void deleteAllByIdIn(List<Long> ids);
}
