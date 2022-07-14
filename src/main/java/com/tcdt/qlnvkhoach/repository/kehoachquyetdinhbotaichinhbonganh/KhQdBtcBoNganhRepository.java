package com.tcdt.qlnvkhoach.repository.kehoachquyetdinhbotaichinhbonganh;

import com.tcdt.qlnvkhoach.table.btcgiaocacbonganh.KhQdBtcBoNganh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhQdBtcBoNganhRepository extends CrudRepository<KhQdBtcBoNganh, Long> {
    @Query(value ="SELECT * FROM KH_QD_BTC_BO_NGANH QD WHERE (:namKhoach IS NULL OR QD.NAM_KHOACH = TO_NUMBER(:namKhoach))"
            +"AND (:soQd IS NULL OR LOWER(QD.SO_QD) LIKE LOWER(CONCAT(CONCAT('%',:soQd),'%' ) ) )"
            +"AND (:ngayQdTu IS NULL OR QD.NGAY_QD >=  TO_DATE(:ngayQdTu,'yyyy-MM-dd'))"
            +"AND (:ngayQdDen IS NULL OR QD.NGAY_QD <= TO_DATE(:ngayQdDen,'yyyy-MM-dd'))"
            +"AND (:trichYeu IS NULL  OR LOWER(QD.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"
            , nativeQuery = true)
    Page<KhQdBtcBoNganh> selectPage(Integer namKhoach, String soQd, String ngayQdTu, String ngayQdDen, String trichYeu, Pageable pageable);
}