package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DxDcKeHoachNamRepository extends JpaRepository<DxDcKeHoachNam, Long>, DxDcKeHoachNamRepositoryCustom {
    List<DxDcKeHoachNam> findByKeHoachNamId(Long keHoachNamId);

    DxDcKeHoachNam findFirstBySoVanBan(String soVanBan);
}
