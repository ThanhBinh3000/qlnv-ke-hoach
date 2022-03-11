package com.tcdt.qlnvkhoachphi.repository.catalog.quyetdinhdutoanchi;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.quyetdinhgiaodutoanchi.QlnvKhvonphiPhanboDtoanchiNsnnCtiet;

@Repository
public interface QlnvKhvonphiPhanboDtoanChiNsnnCtietRepository extends
	CrudRepository<QlnvKhvonphiPhanboDtoanchiNsnnCtiet, Long>{
	
	String tongHop = "SELECT SUM(q.SO_TIEN) as TONG_TIEN, q.MA_KHOAN_MUC, q.MA_DVI_THIEN, v.LOAI_DM, v.TEN_DM "
			+ "from QLNV_KHVONPHI_PHANBO_DTOAN_CHI_NSNN_CTIET q "
			+ "inner join QLNV_DM_KHOACHVON v on v.ID = q.MA_LOAI_CHI "
			+ "where q.MA_DVI_THIEN = :maDviThien GROUP BY q.MA_LOAI_CHI, q.MA_KHOAN_MUC, q.MA_DVI_THIEN, v.LOAI_DM, v.TEN_DM";
	
	ArrayList<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> findByQlnvKhvonphiPhanboDtoanChiId(Long qlnvKhvonphiPhanboDtoanChiId);
	
	@Query(value = tongHop, nativeQuery = true)
	ArrayList<QlnvKhvonphiPhanboDtoanchiNsnnCtiet> synthesis(String maDviThien);
	
}
