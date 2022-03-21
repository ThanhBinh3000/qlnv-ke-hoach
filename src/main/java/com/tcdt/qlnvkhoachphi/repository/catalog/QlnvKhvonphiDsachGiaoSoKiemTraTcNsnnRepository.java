package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn;

public interface QlnvKhvonphiDsachGiaoSoKiemTraTcNsnnRepository
		extends CrudRepository<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn, Long> {

	String value = "SELECT * FROM QLNV_KHVONPHI_DSACH_GIAO_SO_KIEM_TRA_TC_NSNN t "
			+ " WHERE (:namGiao is null or lower(t.NAM_GIAO) like lower(concat(concat('%', :namGiao),'%'))) "
			+ " AND (:maDviTao is null or lower(t.MA_DVI_TAO) like lower(concat(concat('%', :maDviTao),'%'))) "
			+ " AND (:maDviNhan is null or lower(t.MA_DVI_NHAN) like lower(concat(concat('%', :maDviNhan),'%'))) "
			+ " AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))"
			+ " AND (:maGiao is null or lower(t.MA_GIAO) like lower(concat(concat('%', :maGiao),'%')))"
			+ " AND (:maPa is null or lower(t.MA_PA) like lower(concat(concat('%', :maPa),'%')))"
			+ " AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%')))";

	String countQuery = "SELECT count(1) FROM QLNV_KHVONPHI_DSACH_GIAO_SO_KIEM_TRA_TC_NSNN t "
			+ " WHERE (:namGiao is null or lower(t.NAM_GIAO) like lower(concat(concat('%', :namGiao),'%'))) "
			+ " AND (:maDviTao is null or lower(t.MA_DVI_TAO) like lower(concat(concat('%', :maDviTao),'%'))) "
			+ " AND (:maDviNhan is null or lower(t.MA_DVI_NHAN) like lower(concat(concat('%', :maDviNhan),'%'))) "
			+ " AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))"
			+ " AND (:maGiao is null or lower(t.MA_GIAO) like lower(concat(concat('%', :maGiao),'%')))"
			+ " AND (:maPa is null or lower(t.MA_PA) like lower(concat(concat('%', :maPa),'%')))"
			+ " AND (:trangThai is null or lower(t.TRANG_THAI) like lower(concat(concat('%', :trangThai),'%')))";

	@Query(value = value, countQuery = countQuery, nativeQuery = true)
	Page<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> selectParams(String namGiao, String ngayTaoTu, String ngayTaoDen,
			String maDviTao, String maDviNhan, String maGiao, String maPa, String trangThai,Pageable pageable);

	@Query(value = value, nativeQuery = true)
	ArrayList<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> checkBanTinDaGiao(String maPa, String maDviNhan, Long namGiao, String ngayTaoDen, String trangThai,
			String maDviTao, String ngayTaoTu, String maGiao);
	
	List<QlnvKhvonphiDsachGiaoSoKiemTraTcNsnn> findByMaGiao(String maGiao);

}
