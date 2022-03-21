package com.tcdt.qlnvkhoachphi.repository.catalog.capvonmuaban;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.capvonmuaban.QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet;
@Repository
public interface QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietRepository extends CrudRepository<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet, Long>{

	String qlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet = "SELECT * FROM QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG_CTIET t "
			+ "WHERE t.QLNV_KHVONPHI_CAPVON_ID = :qlnvKhvonphiCapVonId ";

	@Query(value = qlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet, nativeQuery = true)
	ArrayList<QlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtiet> findQlnvKhvonphiCapvonMuaBanTtoanThangDtqgCtietByQlnvKhvonphiCapVonId(Long qlnvKhvonphiCapVonId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QLNV_KHVONPHI_CAPVON_MUA_BAN_TTOAN_THANG_DTQG_CTIET u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);
}
