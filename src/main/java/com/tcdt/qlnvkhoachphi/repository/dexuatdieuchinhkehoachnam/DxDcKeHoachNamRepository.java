package com.tcdt.qlnvkhoachphi.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoachphi.entities.dexuatdieuchinhkehoachnam.DxDcKeHoachNam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DxDcKeHoachNamRepository extends JpaRepository<DxDcKeHoachNam, Long>, DxDcKeHoachNamRepositoryCustom {

}
