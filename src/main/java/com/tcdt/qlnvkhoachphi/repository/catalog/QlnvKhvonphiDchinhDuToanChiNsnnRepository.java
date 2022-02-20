package com.tcdt.qlnvkhoachphi.repository.catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphi;
import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiDchinhDuToanChiNsnn;
@Repository
public interface QlnvKhvonphiDchinhDuToanChiNsnnRepository extends CrudRepository<QlnvKhvonphiDchinhDuToanChiNsnn, Long>{


//	String value = "SELECT * FROM QLNV_KHVONPHI t "
//			+ "WHERE (:namBcao is null or lower(t.NAM_BCAO) like lower(concat(concat('%', :namBcao),'%'))) "
//			+ "AND (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
//			+ "AND (:maBcao is null or lower(t.MA_BCAO) like lower(concat(concat('%', :maBcao),'%'))) "
//			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))"
//			+ "AND (:maLoaiBcao is null or lower(t.MA_LOAI_BCAO) like lower(concat(concat('%', :maLoaiBcao),'%')))";
//
//	String countQuery = "SELECT count(1) FROM QLNV_KHVONPHI t "
//			+ "WHERE (:namBcao is null or lower(t.NAM_BCAO) like lower(concat(concat('%', :namBcao),'%'))) "
//			+ "AND (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
//			+ "AND (:maBcao is null or lower(t.MA_BCAO) like lower(concat(concat('%', :maBcao),'%'))) "
//			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or (TRUNC(t.NGAY_TAO) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TRUNC(t.NGAY_TAO) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY')))"
//			+ "AND (:maLoaiBcao is null or lower(t.MA_LOAI_BCAO) like lower(concat(concat('%', :maLoaiBcao),'%')))";
//
//	@Query(value = value, countQuery = countQuery, nativeQuery = true)
//	Page<QlnvKhvonphiDchinhDuToanChiNsnn> selectParams(Long namHienHanh, String ngayTaoTu, String ngayTaoDen, String maDvi, String trangThai,
//			Pageable pageable);

}
