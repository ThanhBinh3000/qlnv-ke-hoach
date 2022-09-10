package com.tcdt.qlnvkhoach.repository.khcnbq;

import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CnCtrinhNcuuRepository extends JpaRepository<CnCtrinhNcuu, Long> {

    @Query(value = "SELECT * FROM CN_CONGTRINH_NGHIENCUU CN_CT_NC WHERE (:capDt IS NULL OR CN_CT_NC.CAP_DT = :capDt)"
            + "AND (:maDt IS NULL OR CN_CT_NC.MA_DT =  :maDt)"
            + "AND (:trangThai IS NULL OR CN_CT_NC.TRANG_THAI =  :trangThai)"
            + "AND (:namTu IS NULL OR CN_CT_NC.NAM_TU >=   :namTu )"
            + "AND (:namDen IS NULL OR CN_CT_NC.NAM_DEN <=  :namDen )"
            + "AND (:tenDt IS NULL  OR LOWER(CN_CT_NC.TEN_DT) LIKE LOWER(CONCAT(CONCAT('%',:tenDt),'%' ) ) )"
            , nativeQuery = true)
    Page<CnCtrinhNcuu> selectPage(String maDt, String tenDt, String capDt, String trangThai, Integer namTu, Integer namDen, Pageable pageable);

    List<CnCtrinhNcuu> findByIdIn(List<Long> ids);

}
