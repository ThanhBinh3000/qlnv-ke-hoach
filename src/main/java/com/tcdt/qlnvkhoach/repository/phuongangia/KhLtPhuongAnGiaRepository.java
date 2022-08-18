package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPhuongAnGia;
import com.tcdt.qlnvkhoach.repository.KhLtPhuongAnGiaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhLtPhuongAnGiaRepository extends JpaRepository<KhPhuongAnGia, Long>, KhLtPhuongAnGiaRepositoryCustom {

	@Query(value ="SELECT * FROM KH_LT_PHUONG_AN_GIA KLPAG WHERE (:namKh IS NULL OR KLPAG.NAM_KE_HOACH = TO_NUMBER(:namKh))"
			+"AND (:soDx IS NULL OR LOWER(KLPAG.SO_DE_XUAT) LIKE LOWER(CONCAT(CONCAT('%',:soDx),'%' ) ) )"
			+"AND (:dvql IS NULL OR LOWER(KLPAG.MA_DVI) LIKE LOWER(CONCAT(:dvql,'%' ) ) )"
			+"AND (:loaiHh IS NULL OR KLPAG.LOAI_VTHH =  :loaiHh)"
			+"AND (:ngayKyTu IS NULL OR KLPAG.NGAY_KY >=  TO_DATE(:ngayKyTu,'yyyy-MM-dd'))"
			+"AND (:ngayKyDen IS NULL OR KLPAG.NGAY_KY <= TO_DATE(:ngayKyDen,'yyyy-MM-dd'))"
			+"AND (:trichYeu IS NULL  OR LOWER(KLPAG.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"
			, nativeQuery = true)
	Page<KhPhuongAnGia> selectPage(Integer namKh, String soDx, String loaiHh, String ngayKyTu, String ngayKyDen, String trichYeu , String dvql, Pageable pageable);

	List<KhPhuongAnGia> findByIdIn(List<Long> ids);


	@Query(value = " SELECT PAG.* \n" +
			"FROM KH_LT_PHUONG_AN_GIA PAG \n" +
			" WHERE PAG.LOAI_VTHH = :loaiHh \n" +
			" AND PAG.CLOAI_VTHH = :cloaiHh \n" +
			" AND PAG.NAM_KE_HOACH = :namKh \n" +
			" AND PAG.LOAI_GIA = :loaiGia \n" +
			" AND PAG.NGAY_KY >=  TO_DATE(:ngayDxTu,'yyyy-MM-dd') \n" +
			" AND PAG.NGAY_KY  <=  TO_DATE(:ngayDxDen,'yyyy-MM-dd') \n" +
			" AND PAG.TRANG_THAI_TH = '24' \n" +
			" AND PAG.TRANG_THAI = '02' ", nativeQuery = true)
	List<KhPhuongAnGia> listTongHop(String loaiHh, String cloaiHh, String namKh, String loaiGia, String ngayDxTu, String ngayDxDen);

	@Query("SELECT pag.id, min(kq.donGia), max(kq.donGia),min(kq.donGiaVat), max(kq.donGiaVat) from KhPhuongAnGia pag,KhPagKetQua kq,KhPagCcPhapLy cc where pag.id= kq.phuongAnGiaId and pag.id = cc.phuongAnGiaId and kq.type = ?1 and pag.id in ?2  GROUP BY pag.id")
	List<Object[]> listPagWithDonGia(String type, Collection<Long> pagIds);
}
