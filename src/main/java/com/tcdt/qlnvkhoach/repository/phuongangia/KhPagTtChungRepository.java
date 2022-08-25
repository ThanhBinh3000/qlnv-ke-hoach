package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagCcPhapLy;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTtChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhPagTtChungRepository extends JpaRepository<KhPagTtChung, Long> {
	List<KhPagTtChung> findByPhuongAnGiaIdIn(List<Long> ids);
}
