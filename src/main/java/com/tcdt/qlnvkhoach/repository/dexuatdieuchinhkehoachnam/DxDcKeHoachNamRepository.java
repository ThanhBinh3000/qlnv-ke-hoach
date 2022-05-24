package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DxDcKeHoachNamRepository extends JpaRepository<DxDcKeHoachNam, Long>, DxDcKeHoachNamRepositoryCustom {
    List<DxDcKeHoachNam> findByKeHoachNamIdAndMaDvi(Long keHoachNamId, String maDvi);

    DxDcKeHoachNam findFirstBySoVanBan(String soVanBan);

    List<DxDcKeHoachNam> findByMaDvi(String maDvi);

    List<DxDcKeHoachNam> findByMaDviInAndNamKeHoachAndTrangThaiTongCuc(Collection<String> maDvis, Integer namKeHoach, String trangThaiTongCuc);
}
