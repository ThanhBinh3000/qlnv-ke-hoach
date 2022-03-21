package com.tcdt.qlnvkhoachphi.repository.catalog.ketquathuchienvonphi;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkhoachphi.table.catalog.ketquathuchienvonphi.QlnvKhvonphiBcaoKquaThienXuatHang;



public interface QlnvKhvonphiBcaoKquaThienXuatHangRepository extends CrudRepository <QlnvKhvonphiBcaoKquaThienXuatHang, Long>{
	String qlnvKhvonphiBcaoKquaThienXuatHang = "SELECT * FROM QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG t "
			+ "WHERE t.QLNV_KHVONPHI_BCAO_ID = :qlnvKhvonphiBcaoId ";

	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" +
			"    SELECT '' as QLNV_KHVONPHI_BCAO_ID" +
			"    ,'' as STT" +
			"    ,'' as SO_QD" +
			"    ,t.MA_VTU_PARENT" +
			"    ,t.MA_VTU" +
			"    ,t.MA_DVI_TINH" +

			"    ,Sum(t.SO_LUONG_KHOACH) as SO_LUONG_KHOACH" +
			"    ,Sum(t.SO_LUONG_TTE) as SO_LUONG_TTE" +
			"    ,Sum(t.DG_GIA_KHOACH) as DG_GIA_KHOACH" +
			"    ,Sum(t.DG_GIA_BAN_TTHIEU) as DG_GIA_BAN_TTHIEU" +
			"    ,Sum(t.DG_GIA_BAN_TTE) as DG_GIA_BAN_TTE" +
			"    ,Sum(t.TT_GIA_HTOAN) as TT_GIA_HTOAN" +
			"    ,Sum(t.TT_GIA_BAN_TTE) as TT_GIA_BAN_TTE" +
			"    ,Sum(t.TT_CLECH_GIA_TTE_VA_GIA_HTOAN) as TT_CLECH_GIA_TTE_VA_GIA_HTOAN" +

			"    ,'' as GHI_CHU" +
			"			FROM (" +
			"                SELECT * FROM QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG ct " +
			"                INNER JOIN QLNV_KHVONPHI_BCAO vp ON vp.id = ct.QLNV_KHVONPHI_BCAO_ID " +
			"                INNER JOIN qlnv_dm_donvi dv ON dv.id = vp.ma_dvi WHERE dv.ma_dvi_cha=:maDviCha "+
			"                 AND:namBcao= vp.NAM_BCAO AND:maLoaiBcao= vp.MA_LOAI_BCAO AND:dotBcao= vp.DOT_BCAO  ) t " +
			"			GROUP BY t.MA_VTU,t.MA_DVI_TINH,t.MA_VTU_PARENT) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoKquaThienXuatHang> synthesis(String maDviCha,Long namBcao,Long dotBcao,String maLoaiBcao);

	@Query(value = qlnvKhvonphiBcaoKquaThienXuatHang, nativeQuery = true)
	ArrayList<QlnvKhvonphiBcaoKquaThienXuatHang> findQlnvKhvonphiBcaoKquaThienXuatHangByQlnvKhvonphiBcaoId(Long qlnvKhvonphiBcaoId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_BCAO_KQUA_THIEN_XUAT_HANG u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

}
