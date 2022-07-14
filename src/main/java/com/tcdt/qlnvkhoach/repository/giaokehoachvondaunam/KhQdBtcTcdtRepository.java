package com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhQdBtcTcdtRepository extends CrudRepository<KhQdBtcTcdt,Long> {
    @Query(value ="SELECT * FROM KH_QD_BTC_TCDT QD WHERE (:namQd IS NULL OR QD.NAM_KHOACH = TO_NUMBER(:namQd))"
            +"AND (:soQd IS NULL OR LOWER(QD.SO_QD) LIKE LOWER(CONCAT(CONCAT('%',:soQd),'%' ) ) )"
            +"AND (:ngayQdTu IS NULL OR QD.NGAY_QD >=  TO_DATE(:ngayQdTu,'yyyy-MM-dd'))"
            +"AND (:ngayQdDen IS NULL OR QD.NGAY_QD <= TO_DATE(:ngayQdDen,'yyyy-MM-dd'))"
            +"AND (:trichYeu IS NULL  OR LOWER(QD.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"
            , nativeQuery = true)
    Page<KhQdBtcTcdt> selectPage(Integer namQd, String soQd, String ngayQdTu, String ngayQdDen, String trichYeu, Pageable pageable);


}
