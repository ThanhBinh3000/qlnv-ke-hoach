package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvKhvonphiKhoachBquanHnamMatHang;

public interface QlnvKhvonphiKhoachBquanHnamMatHangRepository extends CrudRepository <QlnvKhvonphiKhoachBquanHnamMatHang, Long>{
	String qlnvKhvonphiKhoachBquanHnamMatHang = "SELECT * FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (SELECT '' as QLNV_KHVONPHI_ID,'' as STT,t.MA_NOI_DUNG,t.MA_NHOM_CHI,t.MA_LOAI_CHI, Sum(t.NAM_HHANH_N) as NAM_HHANH_N, Sum(t.TRAN_CHI_DUOC_TB_N1) as TRAN_CHI_DUOC_TB_N1, Sum(t.NCAU_CHI_CUA_DVI_N1) as NCAU_CHI_CUA_DVI_N1, Sum(t.CLECH_TRAN_CHI_VS_NCAU_N1) as CLECH_TRAN_CHI_VS_NCAU_N1, Sum(t.TRAN_CHI_DUOC_TB_N2) as TRAN_CHI_DUOC_TB_N2, Sum(t.NCAU_CHI_CUA_DVI_N2) as NCAU_CHI_CUA_DVI_N2, Sum(t.CLECH_TRAN_CHI_VS_NCAU_N2) as CLECH_TRAN_CHI_VS_NCAU_N2, Sum(t.TRAN_CHI_DUOC_TB_N3) as TRAN_CHI_DUOC_TB_N3, Sum(t.NCAU_CHI_CUA_DVI_N3) as NCAU_CHI_CUA_DVI_N3, Sum(t.CLECH_TRAN_CHI_VS_NCAU_N3) as CLECH_TRAN_CHI_VS_NCAU_N3 "
			+ "FROM (" + "SELECT * FROM QLNV_KHVONPHI_CHI_TX_GD3N ct "
			+ "INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id "
			+ "INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi " + "WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t "
			+ "GROUP BY t.MA_NOI_DUNG,t.MA_NHOM_CHI,t.MA_LOAI_CHI) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachBquanHnamMatHang> synthesis(String maDviCha, String namHienHanh);
	
	@Query(value = qlnvKhvonphiKhoachBquanHnamMatHang, nativeQuery = true)
	ArrayList<QlnvKhvonphiKhoachBquanHnamMatHang> findQlnvKhvonphiKhoachBquanHnamMatHangByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_KHOACH_BQUAN_HNAM_MAT_HANG u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
