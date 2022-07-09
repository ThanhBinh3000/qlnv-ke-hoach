package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagKetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhLtPagKetQuaRepository extends JpaRepository<KhLtPagKetQua, Long> {
}
