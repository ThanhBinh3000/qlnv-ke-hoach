package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.repository.KhLtPhuongAnGiaRepositoryCustom;
import com.tcdt.qlnvkhoach.table.ttcp.KhQdTtcp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhLtPhuongAnGiaRepository extends JpaRepository<KhLtPhuongAnGia, Long>, KhLtPhuongAnGiaRepositoryCustom {

	@Query(value ="SELECT * FROM KH_LT_PHUONG_AN_GIA KLPAG WHERE (:namKh IS NULL OR KLPAG.NAM_KE_HOACH = TO_NUMBER(:namKh))"
			+"AND (:soDx IS NULL OR LOWER(KLPAG.SO_DE_XUAT) LIKE LOWER(CONCAT(CONCAT('%',:soDx),'%' ) ) )"
			+"AND (:loaiHh IS NULL OR KLPAG.LOAI_HANG_HOA =  :loaiHh)"
			+"AND (:ngayKyTu IS NULL OR KLPAG.NGAY_KY >=  TO_DATE(:ngayKyTu,'yyyy-MM-dd'))"
			+"AND (:ngayKyDen IS NULL OR KLPAG.NGAY_KY <= TO_DATE(:ngayKyDen,'yyyy-MM-dd'))"
			+"AND (:trichYeu IS NULL  OR LOWER(KLPAG.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"
			, nativeQuery = true)
	Page<KhLtPhuongAnGia> selectPage(Integer namKh,String soDx,  String loaiHh, String ngayKyTu, String ngayKyDen, String trichYeu ,Pageable pageable);

	List<KhLtPhuongAnGia> findByIdIn(List<Long> ids);
}
