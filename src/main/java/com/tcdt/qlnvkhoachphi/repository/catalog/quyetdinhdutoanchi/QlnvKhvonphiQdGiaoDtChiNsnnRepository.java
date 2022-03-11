package com.tcdt.qlnvkhoachphi.repository.catalog.quyetdinhdutoanchi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiQdGiaoDtChiNsnn;

@Repository
public interface QlnvKhvonphiQdGiaoDtChiNsnnRepository extends CrudRepository<QlnvKhvonphiQdGiaoDtChiNsnn, Long>{
	
	String value = "SELECT * FROM QLNV_KHVONPHI_GIAO_DTOAN_CHI_NSNN_DS_QD t "
			+ "WHERE (:noiQd is null or lower(t.NOI_QD) like lower(concat(concat('%', :noiQd),'%'))) "
			+ "AND (:soQd is null or lower(t.SO_QD) like lower(concat(concat('%', :soQd),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))";

	String countQuery = "SELECT count(1) FROM QLNV_KHVONPHI_GIAO_DTOAN_CHI_NSNN_DS_QD t "
			+ "WHERE (:noiQd is null or lower(t.NOI_QD) like lower(concat(concat('%', :noiQd),'%'))) "
			+ "AND (:soQd is null or lower(t.SO_QD) like lower(concat(concat('%', :soQd),'%'))) "
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))";
	
	@Query(value = value, countQuery = countQuery, nativeQuery = true)
	Page<QlnvKhvonphiQdGiaoDtChiNsnn> selectParams(String noiQd, String soQd,
			String ngayTaoTu, String ngayTaoDen, Pageable pageable);
}
