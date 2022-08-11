package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhLtPagKetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KhLtPagKetQuaRepository extends JpaRepository<KhLtPagKetQua, Long> {
	List<KhLtPagKetQua> findByTypeAndIdIn(String type, List<Long> ids);
	List<KhLtPagKetQua> findByTypeAndPhuongAnGiaIdIn(String type, List<Long> ids);

	@Query("SELECT kq.id,kq.stt,kq.tenDonVi,kq.donGia,kq.phuongAnGiaId,kq.type FROM KhLtPagKetQua kq " +
			"INNER JOIN KhLtPhuongAnGia pag ON kq.phuongAnGiaId = pag.id " +
			"WHERE kq.type = ?1 and kq.phuongAnGiaId IN ?2")
	List<Object[]> findByTypeAndPhuongAnGiaIdsIn(String type,Collection<Long> pagIds);

}
