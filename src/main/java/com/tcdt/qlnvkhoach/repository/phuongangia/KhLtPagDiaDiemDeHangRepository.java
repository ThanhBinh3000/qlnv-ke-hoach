package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagDiaDiemDeHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhLtPagDiaDiemDeHangRepository extends JpaRepository<KhPagDiaDiemDeHang, Long> {
	List<KhPagDiaDiemDeHang> findByIdIn(List<Long> ids);
	List<KhPagDiaDiemDeHang> findByPagIdIn(List<Long> ids);

}
