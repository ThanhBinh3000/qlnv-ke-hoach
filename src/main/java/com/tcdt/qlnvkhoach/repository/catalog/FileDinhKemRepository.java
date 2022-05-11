package com.tcdt.qlnvkhoach.repository.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoach.table.catalog.FileDinhKem;

@Repository
public interface FileDinhKemRepository extends CrudRepository<FileDinhKem, Long> {
	String fileDinhKem = "SELECT * FROM FILE_DINH_KEM_KHVONPHI t "
			+ "WHERE (:qlnvId is null or t.QLNV_ID = :qlnvId) ";
	@Query(value = fileDinhKem, nativeQuery = true)
	ArrayList<FileDinhKem> findFileDinhKemByQlnvId(Long qlnvId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FILE_DINH_KEM_KHVONPHI u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);



}
