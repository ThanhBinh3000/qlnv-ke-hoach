package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPhuongAnGia;
import com.tcdt.qlnvkhoach.repository.KhLtPhuongAnGiaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhLtPhuongAnGiaRepository extends JpaRepository<KhLtPhuongAnGia, Long>, KhLtPhuongAnGiaRepositoryCustom {
}
