package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagLtQuyetDinhBtc;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagLtQuyetDinhBtcSearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KhPagLtQuyetDinhBtcRepository extends JpaRepository<KhPagLtQuyetDinhBtc, Long> {
  @Query("SELECT c FROM KhPagLtQuyetDinhBtc c WHERE 1=1 " +
      "AND (:#{#param.namKh} IS NULL OR c.namKeHoach = :#{#param.namKh}) " +
      "AND (:#{#param.soQd}  IS NULL OR LOWER(c.soQd) LIKE CONCAT('%',LOWER(:#{#param.soQd}),'%')) " +
      "AND (:#{#param.trichYeu}  IS NULL OR LOWER(c.trichYeu) LIKE CONCAT('%',LOWER(:#{#param.trichYeu}),'%')) " +
      "AND ((:#{#param.ngayKyTu}  IS NULL OR c.ngayKy >= :#{#param.ngayKyTu}) AND (:#{#param.ngayKyDen}  IS NULL OR c.ngayKy <= :#{#param.ngayKyDen}) ) " +
      "ORDER BY c.ngaySua desc , c.ngayTao desc")
  Page<KhPagLtQuyetDinhBtc> search(
      @Param("param") KhPagLtQuyetDinhBtcSearchReq param, Pageable pageable);
}
