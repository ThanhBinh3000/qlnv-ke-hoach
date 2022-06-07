package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.KeHoachVatTu;
import com.tcdt.qlnvkhoach.query.dto.VatTuNhapQueryDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface KeHoachVatTuRepository extends CrudRepository<KeHoachVatTu, Long> {
	List<KeHoachVatTu> findByCtkhnId(Long ctkhnId);

	@Query("SELECT new com.tcdt.qlnvkhoach.query.dto.VatTuNhapQueryDTO(khn.namKeHoach, vt.soLuongNhap, vt.vatTuId) " +
			"FROM ChiTieuKeHoachNam khn " +
			"INNER JOIN KeHoachVatTu vt ON khn.id = vt.ctkhnId " +
			"WHERE vt.vatTuId IN ?1 AND khn.namKeHoach >= ?2 AND khn.namKeHoach <= ?3 AND khn.trangThai = ?4 AND khn.loaiQuyetDinh = ?5 AND khn.lastest = ?6")
	List<VatTuNhapQueryDTO> findKeHoachVatTuCacNamTruocByVatTuId(List<Long> vatTuIdList, int tuNam, int denNam, String trangThai, String loaiQd, boolean lastest);

	List<KeHoachVatTu> findByCtkhnIdAndVatTuIdInAndDonViId(Long ctkhnId, Collection<Long> vatTuIds, Long donViId);

	@Transactional
	@Modifying
	void deleteByCtkhnIdIn(Collection<Long> ctkhnIds);
}
