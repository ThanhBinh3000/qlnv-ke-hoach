package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagCcPhapLy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhLtPagCcPhapLyRepository extends JpaRepository<KhLtPagCcPhapLy, Long> {
	List<KhLtPagCcPhapLy> findByPhuongAnGiaIdIn(List<Long> ids);

}
