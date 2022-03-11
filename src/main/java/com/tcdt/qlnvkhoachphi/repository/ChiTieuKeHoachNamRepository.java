package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.entities.ChiTieuKeHoachNam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTieuKeHoachNamRepository extends JpaRepository<ChiTieuKeHoachNam, Long>, ChiTieuKeHoachNamRepositoryCustom {
    ChiTieuKeHoachNam findByNamKeHoachAndLastest(Integer namKh, boolean lastest);

    List<ChiTieuKeHoachNam> findAllByLoaiQuyetDinh(String loaiQuyetDinh);

    ChiTieuKeHoachNam findByIdAndLoaiQuyetDinh(Long id, String loaiQuyetDinh);
}
