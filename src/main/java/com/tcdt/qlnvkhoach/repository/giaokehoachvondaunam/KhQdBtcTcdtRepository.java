package com.tcdt.qlnvkhoach.repository.giaokehoachvondaunam;

import com.tcdt.qlnvkhoach.table.btcgiaotcdt.KhQdBtcTcdt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KhQdBtcTcdtRepository extends CrudRepository<KhQdBtcTcdt,Long> {
    @Query(value ="SELECT * FROM KH_QD_BTC_TCDT QD WHERE (:namQd IS NULL OR QD.NAM_KHOACH = TO_NUMBER(:namQd))"
            +"AND (:soQd IS NULL OR LOWER(QD.SO_QD) LIKE LOWER(CONCAT(CONCAT('%',:soQd),'%' ) ) )"
            +"AND (:ngayQdTu IS NULL OR QD.NGAY_QD >=  TO_DATE(:ngayQdTu,'yyyy-MM-dd'))"
            +"AND (:ngayQdDen IS NULL OR QD.NGAY_QD <= TO_DATE(:ngayQdDen,'yyyy-MM-dd'))"
            +"AND (:trichYeu IS NULL  OR LOWER(QD.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"
            +"AND (:trangThai IS NULL  OR QD.TRANG_THAI = :trangThai  )"
            , nativeQuery = true)
    Page<KhQdBtcTcdt> selectPage(Integer namQd, String soQd, String ngayQdTu, String ngayQdDen, String trichYeu, String trangThai,  Pageable pageable);


    @Transactional
    @Modifying
    void deleteAllByIdIn(List<Long> ids);
}
