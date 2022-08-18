package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagKetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhLtPagKetQuaRepository extends JpaRepository<KhPagKetQua, Long> {
	List<KhPagKetQua> findByTypeAndIdIn(String type, List<Long> ids);
	List<KhPagKetQua> findByTypeAndPhuongAnGiaIdIn(String type, List<Long> ids);

	@Query("SELECT kq.id,kq.stt,kq.tenDviBaoGia,kq.donGia,kq.donGiaVat,kq.cloaiVthh,kq.ghiChu,kq.thoiHanBaoGia,kq.phuongAnGiaId,kq.type FROM KhPagKetQua kq " +
			"INNER JOIN KhPhuongAnGia pag ON kq.phuongAnGiaId = pag.id " +
			"WHERE kq.type = ?1 and kq.phuongAnGiaId IN ?2")
	List<Object[]> findByTypeAndPhuongAnGiaIdsIn(String type,Collection<Long> pagIds);

}
