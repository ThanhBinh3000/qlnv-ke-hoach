package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHopCTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KhLtPagTongHopCTietRepository extends JpaRepository<KhPagTongHopCTiet, Long> {
	List<KhPagTongHopCTiet> findByIdIn(List<Long> ids);
	List<KhPagTongHopCTiet> findByPagThIdIn(List<Long> ids);

	List<KhPagTongHopCTiet> findAllByQdTcdtnnId(Long idQd);

	@Transactional
	void deleteAllByQdTcdtnnId(Long qdTcdtnnId);

	@Transactional
	void deleteAllByQdTcdtnnIdIn(List<Long> qdTcdtnnIdList);

}
