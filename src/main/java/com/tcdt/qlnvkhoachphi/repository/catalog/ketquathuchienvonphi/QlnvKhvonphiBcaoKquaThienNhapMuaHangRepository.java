package com.tcdt.qlnvkhoachphi.repository.catalog.ketquathuchienvonphi;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienNhapMuaHang;


public interface QlnvKhvonphiBcaoKquaThienNhapMuaHangRepository extends CrudRepository <QlnvKhvonphiBcaoKquaThienNhapMuaHang, Long>{
	String qlnvKhvonphiBcaoKquaThienNhapMuaHang = "SELECT * FROM QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG t "
			+ "WHERE t.QLNV_KHVONPHI_BCAO_ID = :qlnvKhvonphiBcaoId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" +
			"    SELECT '' as QLNV_KHVONPHI_BCAO_ID" +
			"    ,'' as STT" +
			"    ,'' as SO_QD" +
			"    ,t.MA_VTU_PARENT" +
			"    ,t.MA_VTU" +
			"    ,t.MA_DVI_TINH" +

			"    ,Sum(t.KH_SO_LUONG) as KH_SO_LUONG" +
			"    ,Sum(t.KH_GIA_MUA_TD) as KH_GIA_MUA_TD" +
			"    ,Sum(t.KH_TTIEN) as KH_TTIEN" +
			"    ,Sum(t.TH_SO_LUONG) as TH_SO_LUONG" +
			"    ,Sum(t.TH_GIA_MUA_TD) as TH_GIA_MUA_TD" +
			"    ,Sum(t.TH_TTIEN) as TH_TTIEN" +

			"    ,'' as GHI_CHU" +
			"			FROM (" +
			"                SELECT * FROM QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG ct " +
			"                INNER JOIN QLNV_KHVONPHI_BCAO vp ON vp.id = ct.QLNV_KHVONPHI_BCAO_ID " +
			"                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha "+
			"                AND:namBcao= vp.NAM_BCAO AND:maLoaiBcao= vp.MA_LOAI_BCAO AND:dotBcao= vp.DOT_BCAO ) t " +
			"			GROUP BY t.MA_VTU,t.MA_DVI_TINH,t.MA_VTU_PARENT) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoKquaThienNhapMuaHang> synthesis(String maDviCha,Long namBcao,Long dotBcao,String maLoaiBcao);

	@Query(value = qlnvKhvonphiBcaoKquaThienNhapMuaHang, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoKquaThienNhapMuaHang> findQlnvKhvonphiBcaoKquaThienNhapMuaHangByQlnvKhvonphiBcaoId(Long qlnvKhvonphiBcaoId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_BCAO_KQUA_THIEN_NHAP_MUA_HANG u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
