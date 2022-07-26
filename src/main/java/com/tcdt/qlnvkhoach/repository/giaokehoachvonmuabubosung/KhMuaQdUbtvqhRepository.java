package com.tcdt.qlnvkhoach.repository.giaokehoachvonmuabubosung;

import com.tcdt.qlnvkhoach.table.giaokehoachvonmuabubosung.KhMuaQdUbtvqh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface KhMuaQdUbtvqhRepository extends CrudRepository<KhMuaQdUbtvqh,Long> {
    @Query(value ="SELECT * FROM KH_MUA_QD_UBTVQH QD WHERE (:namQd IS NULL OR QD.NAM_QD = TO_NUMBER(:namQd))"
            +"AND (:soQd IS NULL OR LOWER(QD.SO_QD) LIKE LOWER(CONCAT(CONCAT('%',:soQd),'%' ) ) )"
            +"AND (:ngayQdTu IS NULL OR QD.NGAY_QD >=  TO_DATE(:ngayQdTu,'yyyy-MM-dd'))"
            +"AND (:ngayQdDen IS NULL OR QD.NGAY_QD <= TO_DATE(:ngayQdDen,'yyyy-MM-dd'))"
            +"AND (:trichYeu IS NULL  OR LOWER(QD.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"
            +"AND (:trangThai IS NULL OR QD.TRANG_THAI = :trangThai )"
            , nativeQuery = true)
    Page<KhMuaQdUbtvqh> selectPage(Integer namQd, String soQd, String ngayQdTu, String ngayQdDen, String trichYeu, String trangThai, Pageable pageable);

    Optional<KhMuaQdUbtvqh> findBySoQd(String soQd);

    Optional<KhMuaQdUbtvqh> findByNamQd(Integer namQd);

    @Transactional
    void deleteAllByIdIn(List<Long> ids);
}
