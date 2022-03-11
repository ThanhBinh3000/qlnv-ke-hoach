package com.tcdt.qlnvkhoachphi.repository.catalog.quyetdinhdutoanchi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnn;

@Repository
public interface QlnvKhvonphiPhanboDtoanChiNsnnRepository extends CrudRepository<QlnvKhvonphiPhanboDtoanchiNsnn, Long>{

	String value = "SELECT * FROM QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN t "
			+ "WHERE (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
			+ "AND (:noiQd is null or lower(t.NOI_QD) like lower(concat(concat('%', :noiQd),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))"
			+ "AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) ";
	String countQuery = "SELECT count(1) FROM QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN t "
			+ "WHERE (:maDvi is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) "
			+ "AND (:noiQd is null or lower(t.NOI_QD) like lower(concat(concat('%', :noiQd),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))"
			+ "AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) ";
	
	@Query(value = value, countQuery = countQuery, nativeQuery = true)
	Page<QlnvKhvonphiPhanboDtoanchiNsnn> selectParams(String maDvi, String noiQd,
			String ngayTaoTu, String ngayTaoDen, String trangThai, Pageable pageable);
}
