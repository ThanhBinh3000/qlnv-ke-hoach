package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagCcPhapLy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhPagCcPhapLyRepository extends JpaRepository<KhPagCcPhapLy, Long> {
	List<KhPagCcPhapLy> findByPhuongAnGiaIdIn(List<Long> ids);


	@Query("SELECT kq.id,kq.stt,kq.moTa,kq.phuongAnGiaId  FROM KhPagCcPhapLy kq " +
			"INNER JOIN KhPhuongAnGia pag ON kq.phuongAnGiaId = pag.id " +
			"WHERE kq.phuongAnGiaId IN ?1")
	List<Object[]> findByPhuongAnGiaIdsIn(Collection<Long> pagIds);
}
