package com.tcdt.qlnvkhoach.repository;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface FileDinhKemChungRepository extends CrudRepository<FileDinhKemChung, Long> {

	List<FileDinhKemChung> findByDataIdAndDataTypeIn(Long dataId, Collection<String> dataTypes);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FILE_DINH_KEM u WHERE u.ID in ?1", nativeQuery = true)
	int deleteWithIds(List<Long> ids);

	@Transactional
	@Modifying
	int deleteByDataIdAndDataTypeIn(Long dataId, Collection<String> dataTypes);

	@Transactional
	@Modifying
	int deleteByDataIdInAndDataTypeIn(Collection<Long> dataIds, Collection<String> dataTypes);

	List<FileDinhKemChung> findByDataIdAndDataType(Long dataId, String dataType);

	List<FileDinhKemChung> findByDataIdInAndDataType(List<Long> dataIds, String dataType);
}
