package com.tcdt.qlnvkhoach.repository.khcnbq;

import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnCtrinhNcuuRepository extends JpaRepository<CnCtrinhNcuu, Long> {

//    @Query(value = "SELECT * FROM KH_PAG_TONG_HOP PAG_TH WHERE (:namKh IS NULL OR PAG_TH.NAM_TONG_HOP = TO_NUMBER(:namKh))"
//            + "AND (:loaiHh IS NULL OR PAG_TH.LOAI_VTHH =  :loaiHh)"
//            + "AND (:trangThai IS NULL OR PAG_TH.TRANG_THAI_TH =  :trangThai)"
//            + "AND (:type IS NULL OR PAG_TH.TYPE =  :type)"
//            + "AND (:dvql IS NULL OR LOWER(PAG_TH.MA_DVI) LIKE LOWER(CONCAT(:dvql,'%' ) ) )"
//            + "AND (:ngayThTu IS NULL OR PAG_TH.NGAY_TONG_HOP >=  TO_DATE(:ngayThTu,'yyyy-MM-dd'))"
//            + "AND (:ngayThDen IS NULL OR PAG_TH.NGAY_TONG_HOP <= TO_DATE(:ngayThDen,'yyyy-MM-dd'))"
//            + "AND (:noiDung IS NULL  OR LOWER(PAG_TH.NOI_DUNG) LIKE LOWER(CONCAT(CONCAT('%',:noiDung),'%' ) ) )"
//            , nativeQuery = true)
//    Page<CnCtNc> selectPage(String maDt, String tenDt, String capDt, String trangThai, Integer namTu, Integer namDen, Pageable pageable);

    List<CnCtrinhNcuu> findByIdIn(List<Long> ids);

}
