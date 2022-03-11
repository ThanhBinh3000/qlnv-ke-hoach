package com.tcdt.qlnvkhoachphi.repository.catalog.dieuchinhdutoan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.dieuchinhdutoan.QlnvKhvonphiDchinhDuToanChiNsnn;
@Repository
public interface QlnvKhvonphiDchinhDuToanChiNsnnRepository extends CrudRepository<QlnvKhvonphiDchinhDuToanChiNsnn, Long>{


	String value = "SELECT * FROM QLNV_KHVONPHI_DCHINH_DU_TOAN_CHI_NSNN t "
			+ "WHERE (:namHienHanh is null or lower(t.NAM_HIEN_HANH) like lower(concat(concat('%', :namHienHanh),'%'))) "
			+ "AND (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
			+ "AND (:soQd is null or lower(t.SO_QD) like lower(concat(concat('%', :soQd),'%'))) "
			+ "AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))";

	String countQuery = "SELECT * FROM QLNV_KHVONPHI_DCHINH_DU_TOAN_CHI_NSNN t "
			+ "WHERE (:namHienHanh is null or lower(t.NAM_HIEN_HANH) like lower(concat(concat('%', :namHienHanh),'%'))) "
			+ "AND (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
			+ "AND (:soQd is null or lower(t.SO_QD) like lower(concat(concat('%', :soQd),'%'))) "
			+ "AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))";

	@Query(value = value, countQuery = countQuery, nativeQuery = true)
	Page<QlnvKhvonphiDchinhDuToanChiNsnn> selectParams(Long namHienHanh, String ngayTaoTu, String ngayTaoDen, Long maDvi, String trangThai,
			String soQd,Pageable pageable);

}
