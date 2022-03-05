package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.entities.KeHoachVatTu;
import com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeHoachVatTuRepository extends CrudRepository<KeHoachVatTu, Long> {
	List<KeHoachVatTu> findByCtkhnId(Long id);

	@Query("SELECT new com.tcdt.qlnvkhoachphi.response.chitieukehoachnam.VatTuNhapRes(khn.namKeHoach, vt.soLuongNhap, vt.vatTuId) FROM ChiTieuKeHoachNam khn LEFT JOIN KeHoachVatTu vt ON khn.id = vt.ctkhnId WHERE vt.vatTuId IN ?1 AND khn.namKeHoach >= ?2 AND khn.namKeHoach <= ?3")
	List<VatTuNhapRes> findKeHoachVatTuCacNamTruocByVatTuId(List<Long> vatTuIdList, int tuNam, int denNam);
}
