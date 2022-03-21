package com.tcdt.qlnvkhoachphi.repository.catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiPaGiaoSoKiemTraTcNsnn;

@Repository
public interface QlnvKhvonphiPaGiaoSoKiemTraTcNsnnRepository extends 
	CrudRepository<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn, Long>{
	
	String value = "SELECT * FROM QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN t "
			+ " WHERE (:namPa is null or lower(t.NAM_PA) like lower(concat(concat('%', :namPa),'%'))) "
			+ " AND (:maDviTao is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDviTao),'%'))) "
			+ " AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY')) "
			+ " AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) "
			+ " AND (:soQD is null or lower(t.SO_QD) like lower(concat(concat('%', :soQD),'%'))) "
			+ " AND (:soCv is null or lower(t.SO_CV) like lower(concat(concat('%', :soCv),'%'))) "
			+ " AND (:maPa is null or lower(t.MA_PA) like lower(concat(concat('%', :maPa),'%')))";

	String countQuery = "SELECT count(1) FROM QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN t "
			+ " WHERE (:namPa is null or lower(t.NAM_PA) like lower(concat(concat('%', :namPa),'%'))) "
			+ " AND (:maDviTao is null or lower(t.MA_DVI) like lower(concat(concat('%', :maDviTao),'%'))) "
			+ " AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY')) "
			+ " AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%'))) "
			+ " AND (:soQD is null or lower(t.SO_QD) like lower(concat(concat('%', :soQD),'%'))) "
			+ " AND (:soCv is null or lower(t.SO_CV) like lower(concat(concat('%', :soCv),'%'))) "
			+ " AND (:maPa is null or lower(t.MA_PA) like lower(concat(concat('%', :maPa),'%')))";;

	@Query(value = value, countQuery = countQuery, nativeQuery = true)
	Page<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> selectParams(String namPa, String ngayTaoTu, String ngayTaoDen,
			String maDviTao, String soQD, String soCv, String maPa, String trangThai, Pageable pageable);
	
	String genMaGiaoSo = "SELECT MA_GIAO_SO_SEQ.NEXTVAL FROM DUAL";
	
	String genMaPa = "SELECT MA_PA_SEQ.NEXTVAL FROM DUAL";

	@Query(value = genMaGiaoSo, nativeQuery = true)
	int getMaGiaoSo();
	
	@Query(value = genMaPa, nativeQuery = true)
	int getMaPa();
	
	
	QlnvKhvonphiPaGiaoSoKiemTraTcNsnn findByMaPa(String maPa);
	
	@Query(value = "select * from QLNV_KHVONPHI_PA_GIAO_SO_KIEM_TRA_TC_NSNN q WHERE q.TRANG_THAI = '7'", nativeQuery = true)
	Iterable<QlnvKhvonphiPaGiaoSoKiemTraTcNsnn> findQlnvKhvonphiPaGiaoSoKiemTraTcNsnnByTrangThai();
}
