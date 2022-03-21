package com.tcdt.qlnvkhoachphi.repository.catalog.quyettoanvonphi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.quyettoanvonphi.QlnvKhvonphiQtoanDtqg;

@Repository
public interface QlnvKhvonphiQtoanDtqgRepository 
extends CrudRepository<QlnvKhvonphiQtoanDtqg, Long>{
	
	String value = "SELECT * FROM QLNV_KHVONPHI_QTOAN_DTQG t "
			+ "WHERE (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
			+ "AND (:soQd is null or lower(t.SO_QD) like lower(concat(concat('%', :soQd),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))"
			+ "AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) ";
	String countQuery = "SELECT count(1) FROM QLNV_KHVONPHI_QTOAN_DTQG t "
			+ "WHERE (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
			+ "AND (:soQd is null or lower(t.SO_QD) like lower(concat(concat('%', :soQd),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))"
			+ "AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) ";
	
	@Query(value = value, countQuery = countQuery, nativeQuery = true)
	Page<QlnvKhvonphiQtoanDtqg> selectParams(String maDvi, String soQd,
			String ngayTaoTu, String ngayTaoDen, String trangThai, Pageable pageable);

}
