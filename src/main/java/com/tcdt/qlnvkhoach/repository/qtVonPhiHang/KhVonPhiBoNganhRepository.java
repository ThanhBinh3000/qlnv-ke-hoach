package com.tcdt.qlnvkhoach.repository.qtVonPhiHang;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPhuongAnGia;
import com.tcdt.qlnvkhoach.repository.KhLtPhuongAnGiaRepositoryCustom;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
import com.tcdt.qlnvkhoach.table.qtVonPhiHang.KhVonPhiBoNganh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface KhVonPhiBoNganhRepository extends JpaRepository<KhVonPhiBoNganh, Long> {

	@Query(value ="SELECT * FROM KH_VP_BO_NGANH VP_BN WHERE (:namQt IS NULL OR VP_BN.NAM_QT = TO_NUMBER(:namQt))"
			+" AND (:ngayNhapTu IS NULL OR VP_BN.NGAY_NHAP >=  TO_DATE(:ngayNhapTu,'yyyy-MM-dd'))"
			+" AND (:ngayNhapDen IS NULL OR VP_BN.NGAY_NHAP <= TO_DATE(:ngayNhapDen,'yyyy-MM-dd'))"
			+" AND (:ngayCapNhatTu IS NULL OR VP_BN.NGAY_SUA >=  TO_DATE(:ngayCapNhatTu,'yyyy-MM-dd'))"
			+" AND (:ngayCapNhatDen IS NULL OR VP_BN.NGAY_SUA <= TO_DATE(:ngayCapNhatDen,'yyyy-MM-dd'))"
			, nativeQuery = true)
	Page<KhVonPhiBoNganh> selectPage(Integer namQt,  String ngayNhapTu, String ngayNhapDen, String ngayCapNhatTu,String ngayCapNhatDen, Pageable pageable);

	List<KhVonPhiBoNganh> findByIdIn(List<Long> ids);
}
