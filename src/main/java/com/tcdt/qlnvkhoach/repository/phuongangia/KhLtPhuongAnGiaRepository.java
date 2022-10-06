package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHop;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPhuongAnGia;
import com.tcdt.qlnvkhoach.repository.KhLtPhuongAnGiaRepositoryCustom;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPagTongHopSearchReq;
import com.tcdt.qlnvkhoach.request.search.catalog.phuongangia.KhLtPhuongAnGiaSearchReq;
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
public interface KhLtPhuongAnGiaRepository extends JpaRepository<KhPhuongAnGia, Long>, KhLtPhuongAnGiaRepositoryCustom {

	@Query(value ="SELECT * FROM KH_PHUONG_AN_GIA KLPAG WHERE (:namKh IS NULL OR KLPAG.NAM_KE_HOACH = TO_NUMBER(:namKh))"
			+" AND (:soDx IS NULL OR LOWER(KLPAG.SO_DE_XUAT) LIKE LOWER(CONCAT(CONCAT('%',:soDx),'%' ) ) )"
			+" AND (:dvql IS NULL OR LOWER(KLPAG.MA_DVI) LIKE LOWER(CONCAT(:dvql,'%' ) ) )"
			+" AND (:loaiHh IS NULL OR KLPAG.LOAI_VTHH = :loaiHh)"
			+" AND (( :pagType IS NULL AND (KLPAG.LOAI_VTHH LIKE '01%' OR KLPAG.LOAI_VTHH LIKE '04%')) OR (:pagType IS NOT NULL AND KLPAG.LOAI_VTHH LIKE CONCAT(:pagType,'%' )) )"
			+" AND (:type IS NULL OR KLPAG.type =  :type)"
			+" AND (:ngayKyTu IS NULL OR KLPAG.NGAY_KY >=  TO_DATE(:ngayKyTu,'yyyy-MM-dd'))"
			+" AND (:ngayKyDen IS NULL OR KLPAG.NGAY_KY <= TO_DATE(:ngayKyDen,'yyyy-MM-dd'))"
			+" AND (:trichYeu IS NULL  OR LOWER(KLPAG.TRICH_YEU) LIKE LOWER(CONCAT(CONCAT('%',:trichYeu),'%' ) ) )"
			, nativeQuery = true)
	Page<KhPhuongAnGia> selectPage(Integer namKh, String soDx, String loaiHh, String ngayKyTu, String ngayKyDen, String trichYeu , String dvql,String type,String pagType, Pageable pageable);

	List<KhPhuongAnGia> findByIdIn(List<Long> ids);

	@Query(value = " SELECT PAG.* \n" +
			"FROM KH_PHUONG_AN_GIA PAG \n" +
			" WHERE PAG.LOAI_VTHH = :loaiVthh \n" +
			" AND PAG.CLOAI_VTHH = :cloaiVthh \n" +
			" AND PAG.NAM_KE_HOACH = :namTongHop \n" +
			" AND PAG.LOAI_GIA = :loaiGia \n" +
			" AND PAG.MA_DVI in (:maDvis) \n" +
			" AND PAG.TYPE = :type \n" +
			" AND PAG.NGAY_KY >=  TO_DATE(:ngayDxTu,'yyyy-MM-dd') \n" +
			" AND PAG.NGAY_KY  <=  TO_DATE(:ngayDxDen,'yyyy-MM-dd') \n" +
			" AND PAG.TRANG_THAI_TH = :trangThaiTh \n" +
			" AND PAG.TRANG_THAI = :trangThai ", nativeQuery = true)
	List<KhPhuongAnGia> listTongHop(String loaiVthh, String cloaiVthh, String namTongHop, String loaiGia, String ngayDxTu, String ngayDxDen,String type,List<String> maDvis,String trangThaiTh,String trangThai);

	@Query("SELECT min(kq.donGia), max(kq.donGia),min(kq.donGiaVat), max(kq.donGiaVat) from KhPhuongAnGia pag,KhPagKetQua kq,KhPagCcPhapLy cc where pag.id= kq.phuongAnGiaId and pag.id = cc.phuongAnGiaId and kq.type = ?1 and pag.id in ?2")
	List<Object[]> listPagWithDonGia(String type, Collection<Long> pagIds);

  Optional<KhPhuongAnGia> findBySoDeXuat(String soDeXuat);

  @Query("SELECT c FROM KhPhuongAnGia c WHERE 1=1 " +
			"AND (c.trangThai in :#{#param.dsTrangThai})"+
			"AND (c.type in :#{#param.type})"+
      "AND (c.loaiVthh like '02%')"+
      "ORDER BY c.ngaySua desc , c.ngayTao desc")
  List<KhPhuongAnGia> DsToTrinhDeXuat(
      @Param("param") KhLtPhuongAnGiaSearchReq param);


	@Query(value = "SELECT *" +
			"From KH_PHUONG_AN_GIA TT" +
			" where TT.TYPE= :type" +
			" AND (( :pagType IS NULL AND (TT.LOAI_VTHH LIKE '01%' OR TT.LOAI_VTHH LIKE '04%')) OR (:pagType IS NOT NULL AND TT.LOAI_VTHH LIKE CONCAT(:pagType,'%' )) )" +
			" AND (TT.TRANG_THAI in (:dsTrangThai) or :dsTrangThai is NULL)",
			nativeQuery = true)
	List<KhPhuongAnGia> dsSoDeXuatPag(String type, List<String> dsTrangThai, String pagType);
}
