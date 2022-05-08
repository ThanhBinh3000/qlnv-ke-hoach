package com.tcdt.qlnvkhoachphi.repository.capnguonvonchi;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.capnguonvonchi.QlnvKhvonphiThopCapvonCtiet;

@Repository
public interface QlnvKhvonphiThopCapvonCtietRepository extends CrudRepository<QlnvKhvonphiThopCapvonCtiet, Long>{
	String tongHop = "SELECT ROWNUM as ID,a.* FROM (" +
			"    SELECT '' as QLNV_KHVONPHI_BCAO_ID" +
			"    ,'' as MA_DVI" +
			"    ,t.MA_KHOAN_MUC" +
			"    ,t.MA_NOI_DUNG" +
			"    ,t.MA_NHOM_CHI" +
			"    ,Sum(t.SO_TIEN_PHAN_BO) as SO_TIEN_PHAN_BO" +
			"    ,Sum(t.DIEU_CHINH) as DIEU_CHINH" +
			"    ,'' as NGAY_LAP" +
			"    ,'' as NAM" +
			"    ,'' as NGAY_GHI_NHAN" +
			"    ,'' as GHI_CHU"
			+ "FROM (" + "SELECT * FROM QLNV_KHVONPHI_THOP_CAPVON_CTIET ct "
			+ "INNER JOIN QLNV_KHVONPHI_THOP_CAPVON vp ON vp.id = ct.qlnv_khvonphi_dchinh_id "
			+ "INNER JOIN dm_donvi dv ON dv.id = vp.ma_dvi " + "WHERE dv.ma_dvi_cha=:maDviCha AND vp.nam_hien_hanh=:namHienHanh) t "
			+ "GROUP BY t.MA_KHOAN_MUC,t.MA_NOI_DUNG,t.MA_NHOM_CHI) a";

	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiThopCapvonCtiet> synthesis(String maDviCha,String namHienHanh);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_THOP_CAPVON_CTIET u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);
}
