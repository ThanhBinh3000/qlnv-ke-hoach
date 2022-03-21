package com.tcdt.qlnvkhoachphi.repository.catalog.capvonmuaban;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqg;

@Repository
public interface QlnvKhvonphiCapvonMuaBanTtoanThangDtqgRepository
		extends CrudRepository<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg, Long> {

	String value = "SELECT * FROM QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG t\r\n"
			+ "WHERE (:maDviLap is null or t.MA_DVI_LAP =  :maDviLap)\r\n"
			+ "AND (:maDviCap is null or t.MA_DVI_CAP = :maDviCap)\r\n"
			+ "AND (:maLoaiGnhan is null or t.MA_LOAI_GNHAN = :maLoaiGnhan)\r\n"
			+ "AND (:soQd is null or lower(t.SO_QD) like lower(concat(concat('%', :soQd),'%')))\r\n"
			+ "AND (:trangThai is null or t.TRANG_THAI = :trangThai)\r\n"
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))\r\n"
			+ "AND t.trang_thai != '0'";

	String countQuery = "SELECT * FROM QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG t\r\n"
			+ "WHERE (:maDviLap is null or t.MA_DVI_LAP =  :maDviLap)\r\n"
			+ "AND (:maDviCap is null or t.MA_DVI_CAP = :maDviCap)\r\n"
			+ "AND (:maLoaiGnhan is null or t.MA_LOAI_GNHAN = :maLoaiGnhan)\r\n"
			+ "AND (:soQd is null or lower(t.SO_QD) like lower(concat(concat('%', :soQd),'%')))\r\n"
			+ "AND (:trangThai is null or t.TRANG_THAI = :trangThai)\r\n"
			+ "AND (:ngayTaoTu is null or :ngayTaoDen is null or TO_DATE(TRUNC(t.NGAY_TAO)) >= TO_DATE(:ngayTaoTu,'DD/MM/YYYY') AND TO_DATE(TRUNC(t.NGAY_TAO)) <= TO_DATE(:ngayTaoDen,'DD/MM/YYYY'))\r\n"
			+ "AND t.trang_thai != '0'";

	@Query(value = value, countQuery = countQuery, nativeQuery = true)
	Page<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> selectParams(String maDviLap, String maDviCap, String maLoaiGnhan,
			String ngayTaoTu, String ngayTaoDen, String trangThai, String soQd, Pageable pageable);
//	@Query(value = value, countQuery = countQuery, nativeQuery = true)
//	Page<QlnvKhvonphiCapvonMuaBanTtoanThangDtqg> selectParams(String maDviLap, String maDviCap, String maLoaiGnhan,
//			String ngayQuyetDinh, String ngayTaoTu, String ngayTaoDen, String trangThai, String soQd,
//			Pageable pageable);

}
