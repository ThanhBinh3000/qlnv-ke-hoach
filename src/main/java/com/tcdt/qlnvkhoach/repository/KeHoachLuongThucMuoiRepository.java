package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.KeHoachLuongThucMuoi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface KeHoachLuongThucMuoiRepository extends CrudRepository<KeHoachLuongThucMuoi, Long> {
	List<KeHoachLuongThucMuoi> findByCtkhnId(Long ctkhnId);

	List<KeHoachLuongThucMuoi> findByCtkhnIdAndDonViIdAndVatTuIdIn(Long ctkhnId, Long donViId, Set<Long> vatTuIds);

	List<KeHoachLuongThucMuoi> findByCtkhnIdIn(Collection<Long> ctkhnIds);


	@Query(value = "select dtl.* from KH_CHI_TIEU_LT_MUOI dtl, KH_CHI_TIEU_KE_HOACH_NAM  ct where " +
			" ct.id = dtl.CTKHN_ID and ct.NAM_KE_HOACH = :namKh " +
			" and dtl.MA_DVI = :maDvi " +
			" and dtl.ma_vat_tu = :loaiVthh " +
			" and ct.latest = 1", nativeQuery = true)
	KeHoachLuongThucMuoi getKhLTMuoi(Long namKh,String maDvi,String loaiVthh);
}
