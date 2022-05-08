package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.lapthamdinhdutoan.QlnvKhvonphiNcauChiNsnnGd3n;

public interface QlnvKhvonphiNcauChiNsnnGd3nRepository extends CrudRepository <QlnvKhvonphiNcauChiNsnnGd3n, Long>{
	String qlnvKhvonphiNcauChiNsnnGd3n = "SELECT * FROM QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N t "
			+ "WHERE (:qlnvKhvonphiId is null or t.QLNV_KHVONPHI_ID = :qlnvKhvonphiId) ";
	
	String tongHop = "SELECT ct.ID,ct.QLNV_KHVONPHI_ID,ct.STT,ct.MA_NDUNG,ct.MA_LOAI_CHI,ct.MA_KHOAN_CHI,ct.MA_MUC_CHI,ct.MA_LOAI_CHI_TX,ct.DTOAN_N,ct.UOC_THIEN_N,ct.TRAN_CHI_N1,ct.NCAU_CHI_N1,ct.CLECH_TRAN_CHI_VS_NCAU_CHI_N1,ct.SSANH_NCAU_N_VOI_N_1,ct.TRAN_CHI_N2,ct.NCAU_CHI_N2,ct.CLECH_TRAN_CHI_VS_NCAU_CHI_N2,ct.TRAN_CHI_N3,ct.NCAU_CHI_N3,ct.CLECH_TRAN_CHI_VS_NCAU_CHI_N3,ct.TEN_DAN FROM QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N ct " + 
			"INNER JOIN QLNV_KHVONPHI vp ON vp.id = ct.qlnv_khvonphi_id " + 
			"INNER JOIN dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiNcauChiNsnnGd3n> synthesis(String maDviCha, String namHienHanh);

	@Query(value = qlnvKhvonphiNcauChiNsnnGd3n, nativeQuery = true)
	ArrayList<QlnvKhvonphiNcauChiNsnnGd3n> findQlnvKhvonphiNcauChiNsnnGd3nByQlnvKhvonphiId(Long qlnvKhvonphiId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_NCAU_CHI_NSNN_GD3N u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}

