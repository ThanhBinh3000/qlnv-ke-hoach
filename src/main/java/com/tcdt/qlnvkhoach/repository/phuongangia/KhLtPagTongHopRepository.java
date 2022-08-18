package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhLtPagTongHopRepository extends JpaRepository<KhPagTongHop, Long> {

	@Query(value ="SELECT * FROM KH_LT_PAG_TONG_HOP PAG_TH WHERE (:namKh IS NULL OR PAG_TH.NAM_TONG_HOP = TO_NUMBER(:namKh))"
			+"AND (:loaiHh IS NULL OR PAG_TH.LOAI_VTHH =  :loaiHh)"
			+"AND (:trangThai IS NULL OR PAG_TH.TRANG_THAI =  :trangThai)"
			+"AND (:dvql IS NULL OR LOWER(PAG_TH.MA_DVI) LIKE LOWER(CONCAT(:dvql,'%' ) ) )"
			+"AND (:ngayThTu IS NULL OR PAG_TH.NGAY_TONG_HOP >=  TO_DATE(:ngayThTu,'yyyy-MM-dd'))"
			+"AND (:ngayThDen IS NULL OR PAG_TH.NGAY_TONG_HOP <= TO_DATE(:ngayThDen,'yyyy-MM-dd'))"
			+"AND (:noiDung IS NULL  OR LOWER(PAG_TH.NOI_DUNG) LIKE LOWER(CONCAT(CONCAT('%',:noiDung),'%' ) ) )"
			, nativeQuery = true)
	Page<KhPagTongHop> selectPage(Integer namKh, String loaiHh, String ngayThTu, String ngayThDen, String noiDung , String dvql, String trangThai, Pageable pageable);

	List<KhPagTongHop> findByIdIn(List<Long> ids);

	Optional<KhPagTongHop> findByMaToTrinh(String maToTrinh);
}
