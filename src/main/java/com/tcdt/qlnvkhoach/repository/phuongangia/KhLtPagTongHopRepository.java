package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHop;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhLtPagTongHopRepository extends JpaRepository<KhPagTongHop, Long> {

    @Query(value = "SELECT * FROM KH_PAG_TONG_HOP PAG_TH WHERE (:namKh IS NULL OR PAG_TH.NAM_TONG_HOP = TO_NUMBER(:namKh))"
            + "AND (:loaiHh IS NULL OR PAG_TH.LOAI_VTHH =  :loaiHh)"
            + "AND (:trangThai IS NULL OR PAG_TH.TRANG_THAI_TH =  :trangThai)"
            + "AND (:type IS NULL OR PAG_TH.TYPE =  :type)"
            + "AND (:dvql IS NULL OR LOWER(PAG_TH.MA_DVI) LIKE LOWER(CONCAT(:dvql,'%' ) ) )"
            + "AND (:ngayThTu IS NULL OR PAG_TH.NGAY_TONG_HOP >=  TO_DATE(:ngayThTu,'yyyy-MM-dd'))"
            + "AND (:ngayThDen IS NULL OR PAG_TH.NGAY_TONG_HOP <= TO_DATE(:ngayThDen,'yyyy-MM-dd'))"
            + "AND (:noiDung IS NULL  OR LOWER(PAG_TH.NOI_DUNG) LIKE LOWER(CONCAT(CONCAT('%',:noiDung),'%' ) ) )"
            , nativeQuery = true)
    Page<KhPagTongHop> selectPage(Integer namKh, String loaiHh, String ngayThTu, String ngayThDen, String noiDung, String dvql, String trangThai, String type, Pageable pageable);

    List<KhPagTongHop> findByIdIn(List<Long> ids);

    Optional<KhPagTongHop> findBySoToTrinh(String soToTrinh);

    @Query("SELECT c FROM KhPagTongHop c WHERE 1=1 " +
            "AND (c.trangThaiTt in :#{#param.dsTrangThai})" +
            "ORDER BY c.ngaySua desc , c.ngayTao desc")
    List<KhPagTongHop> DsToTrinhDeXuat(
            @Param("param") KhLtPagTongHopSearchReq param);

    @Query(value = "SELECT DISTINCT *" +
            "From KH_PAG_TONG_HOP TT" +
            " where TT.TYPE='GCT'" +
            "AND TT.TRANG_THAI_TH= '32'" +
            "AND TT.TRANG_THAI_TT='20' ",
            nativeQuery = true)
    List<KhPagTongHop> listToTrinh(KhLtPagTongHopSearchReq req);


    //    @Query(value = "SELECT DISTINCT *" +
//            "From KH_PAG_TONG_HOP TT" +
//            " where TT.TYPE= :type" +
//            " AND TT.TRANG_THAI_TT= :trangThaiTt " +
//            " AND NOT EXISTS(SELECT BTC.ID FROM KH_PAG_QD_BTC BTC WHERE BTC.SO_TO_TRINH = TT.SO_TO_TRINH) " +
//            " AND NOT EXISTS(SELECT QD_TCDT.ID FROM KH_PAG_GCT_QD_TCDTNN QD_TCDT WHERE QD_TCDT.SO_TO_TRINH = TT.SO_TO_TRINH) " +
//            " AND NOT EXISTS(SELECT QD_DC_TCDT.ID FROM KH_PAG_GCT_QD_DC_TCDTNN QD_DC_TCDT WHERE QD_DC_TCDT.SO_TO_TRINH_DX = TT.SO_TO_TRINH) ",
//            nativeQuery = true)
    @Query(value = "SELECT *" +
            "From KH_PAG_TONG_HOP TT" +
            " where TT.TYPE= :type" +
            " AND (( :pagType IS NULL AND (TT.LOAI_VTHH LIKE '01%' OR TT.LOAI_VTHH LIKE '04%')) OR (:pagType IS NOT NULL AND TT.LOAI_VTHH LIKE CONCAT(:pagType,'%' )) )" +
            " AND TT.TRANG_THAI_TT in (:dsTrangThai)",
            nativeQuery = true)
    List<KhPagTongHop> dsToTrinhTh(String type, List<String> dsTrangThai,String pagType);


}
