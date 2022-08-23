package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagPpXacDinhGia;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTtChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhPagPpXacDinhGiaRepository extends JpaRepository<KhPagPpXacDinhGia, Long> {
	List<KhPagPpXacDinhGia> findByPhuongAnGiaIdIn(List<Long> ids);
}
