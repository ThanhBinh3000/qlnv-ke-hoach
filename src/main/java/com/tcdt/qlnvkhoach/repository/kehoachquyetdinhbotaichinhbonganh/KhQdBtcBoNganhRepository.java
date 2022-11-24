package com.tcdt.qlnvkhoach.repository.kehoachquyetdinhbotaichinhbonganh;

import com.tcdt.qlnvkhoach.table.btcgiaocacbonganh.KhQdBtcBoNganh;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface KhQdBtcBoNganhRepository extends CrudRepository<KhQdBtcBoNganh, Long> {
    @Query(value ="SELECT * FROM KH_QD_BTC_BO_NGANH QD WHERE (:namQd IS NULL OR QD.NAM_QD = TO_NUMBER(:namQd))"
            +"AND (:soQd IS NULL OR LOWER(QD.SO_QD) LIKE LOWER(CONCAT(CONCAT('%',:soQd),'%' ) ) )"
            +"AND (:ngayQdTu IS NULL OR QD.NGAY_QD >=  TO_DATE(:ngayQdTu,'yyyy-MM-dd'))"
            +"AND (:ngayQdDen IS NULL OR QD.NGAY_QD <= TO_DATE(:ngayQdDen,'yyyy-MM-dd'))"
            +"AND (:trichYeu IS NULL  OR LOWER(QD.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"
            +"AND (:trangThai IS NULL OR QD.TRANG_THAI = :trangThai )"+
            "AND (:maDvi IS NULL OR LOWER(QD.MA_DVI) LIKE LOWER(CONCAT(:maDvi,'%'))) "
            , nativeQuery = true)
    Page<KhQdBtcBoNganh> selectPage(Integer namQd, String soQd, String ngayQdTu, String ngayQdDen, String trichYeu, String trangThai,String maDvi,  Pageable pageable);

    Optional<KhQdBtcBoNganh> findByNamQd(Integer namQd);
    Optional<KhQdBtcBoNganh> findBySoQd (String soQd);

    @Transactional
    @Modifying
    void deleteAllByIdIn(List<Long> ids);
}