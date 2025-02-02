package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.QlnvDanhMuc;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface DanhMucRepository extends CrudRepository<QlnvDanhMuc, Long> {

	Iterable<QlnvDanhMuc> findByTrangThai(String trangThai);

	@Transactional()
	@Modifying
	@Query(value = "UPDATE DM_DUNG_CHUNG SET GIA_TRI=:shgtNext WHERE ma = :ma", nativeQuery = true)
	void updateVal(String ma, Long shgtNext);

	QlnvDanhMuc findByMa(String ma);

	List<QlnvDanhMuc> findByMaIn(Collection<String> mas);

}
