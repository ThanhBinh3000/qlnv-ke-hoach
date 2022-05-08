package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.entities.FileDinhKemChung;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FileDinhKemChungRepository extends CrudRepository<FileDinhKemChung, Long> {

	List<FileDinhKemChung> findByDataId(Long dataId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FILE_DINH_KEM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

	@Transactional
	@Modifying
	int deleteByDataIdAndDataType(Long dataId, String dataType);
}
