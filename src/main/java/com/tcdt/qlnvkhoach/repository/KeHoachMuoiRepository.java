package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.KeHoachLuongThuc;
import com.tcdt.qlnvkhoach.entities.KeHoachMuoi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface KeHoachMuoiRepository extends CrudRepository<KeHoachMuoi, Long> {

    List<KeHoachMuoi> findByCtkhnId(Long ctkhnId);


    List<KeHoachMuoi> findByCtkhnIdIn(Collection<Long> ctkhnIds);


    @Query(value = "select dtl.* from KH_CHI_TIEU_MUOI dtl, KH_CHI_TIEU_KE_HOACH_NAM  ct where " +
            " ct.id = dtl.CTKHN_ID and ct.NAM_KE_HOACH = :namKh " +
            " and dtl.MA_DVI = :maDvi " +
            " and dtl.ma_vat_tu = :loaiVthh " +
            " and ct.latest = 1", nativeQuery = true)
    KeHoachMuoi getKhMuoi(Long namKh, String maDvi, String loaiVthh);
}
