package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagDiaDiemDeHang;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagKetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhLtPagDiaDiemDeHangRepository extends JpaRepository<KhLtPagDiaDiemDeHang, Long> {
	List<KhLtPagDiaDiemDeHang> findByIdIn(List<Long> ids);
	List<KhLtPagDiaDiemDeHang> findByPagIdIn(List<Long> ids);

}
