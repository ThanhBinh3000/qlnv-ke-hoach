package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagKetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhLtPagKetQuaRepository extends JpaRepository<KhLtPagKetQua, Long> {
	List<KhLtPagKetQua> findByTypeAndIdIn(String type, List<Long> ids);
	List<KhLtPagKetQua> findByTypeAndPhuongAnGiaIdIn(String type, List<Long> ids);

}
