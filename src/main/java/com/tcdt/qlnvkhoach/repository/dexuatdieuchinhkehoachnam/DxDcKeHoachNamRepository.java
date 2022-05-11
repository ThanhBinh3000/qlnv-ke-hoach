package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DxDcKeHoachNamRepository extends JpaRepository<DxDcKeHoachNam, Long>, DxDcKeHoachNamRepositoryCustom {

}
