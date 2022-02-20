package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n;

public interface QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nRepository extends CrudRepository <QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n, Long>{
	String qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n = "SELECT * FROM QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N t "
			+ "WHERE t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId ";

	String tongHop = "SELECT ct.ID\r\n" + 
			",ct.QLNV_KHVONPHI_ID\r\n" + 
			",ct.STT\r\n" + 
			",ct.TEN_CTRINH\r\n" + 
			",ct.MA_NGUON_VON as NGUON_VON\r\n" + 
			",ct.MA_LOAI_CT as LOAI_CT\r\n" + 
			",ct.MA_DVI_TINH\r\n" +
			",ct.TGIAN_KC\r\n" + 
			",ct.TGIAN_HT\r\n" + 
			",ct.MA_CQUAN_QD as DTOAN_DUOC_DUYET_CQUAN_QD\r\n" + 
			",ct.TONG_GTRI_3006N as DTOAN_DUOC_DUYET_TONG_GTRI\r\n" + 
			",ct.DTOAN_KPHI as THIEN_KLUONG_N_DTOAN_KPHI_DEN_3006N\r\n" + 
			",ct.UOC_THIEN as THIEN_KLUONG_N_UOC_THIEN_CA_NAM_N\r\n" + 
			",ct.DA_TTOAN_3006N as TTOAN_KLUONG_N_DA_TTOAN_DEN_3006N\r\n" + 
			",ct.UOC_TTOAN as TTOAN_KLUONG_N_UOC_THIEN_CA_NAM_N\r\n" + 
			",ct.TSO_N1 as TONG_SO_N1\r\n" + 
			",ct.TTOAN_N1 as TTOAN_NAM_TRUOC_CHUYEN_SANG_N1\r\n" + 
			",ct.PHAT_SINH_N1 as PSINH_MOI_N1\r\n" + 
			",ct.TSO_N2 as TONG_SO_N2\r\n" + 
			",ct.TTOAN_N2 as TTOAN_NAM_TRUOC_CHUYEN_SANG_N2\r\n" + 
			",ct.PHAT_SINH_N2 as PSINH_MOI_N2\r\n" + 
			",ct.TSO_N3 as TONG_SO_N3\r\n" + 
			",ct.TTOAN_N3 as TTOAN_NAM_TRUOC_CHUYEN_SANG_N3\r\n" + 
			",ct.PHAT_SINH_N3 as PSINH_MOI_N3\r\n" + 
			"FROM QLNV_KHVONPHI_KHOACH_CTAO_SCHUA_GD3N ct \r\n" + 
			"			INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id \r\n" + 
			"			INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3n> findQlnvKhvonphiTcKhoachDtoanCtaoSchuaHthongKhoTangGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_TC_KHOACH_DTOAN_CTAO_SCHUA_HTHONG_KHO_TANG_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
