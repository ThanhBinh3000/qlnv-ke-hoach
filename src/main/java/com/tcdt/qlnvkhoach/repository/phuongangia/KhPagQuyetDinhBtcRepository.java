package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagQuyetDinhBtc;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhPagQuyetDinhBtcSearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KhPagQuyetDinhBtcRepository extends JpaRepository<KhPagQuyetDinhBtc, Long> {
  @Query("SELECT c FROM KhPagQuyetDinhBtc c WHERE 1=1 " +
      "AND (:#{#param.namKeHoach} IS NULL OR function('TO_CHAR',c.namKeHoach) = :#{#param.namKeHoach}) " +
      "AND (:#{#param.soQd}  IS NULL OR LOWER(c.soQd) LIKE CONCAT('%',LOWER(:#{#param.soQd}),'%')) " +
      "AND (:#{#param.trichYeu}  IS NULL OR LOWER(c.trichYeu) LIKE CONCAT('%',LOWER(:#{#param.trichYeu}),'%')) " +
      "AND ((:#{#param.ngayKyTu}  IS NULL OR c.ngayKy >= :#{#param.ngayKyTu}) AND (:#{#param.ngayKyDen}  IS NULL OR c.ngayKy <= :#{#param.ngayKyDen}) ) " +
      "ORDER BY c.ngaySua desc , c.ngayTao desc")
  Page<KhPagQuyetDinhBtc> search(
      @Param("param") KhPagQuyetDinhBtcSearchReq param, Pageable pageable);
}
