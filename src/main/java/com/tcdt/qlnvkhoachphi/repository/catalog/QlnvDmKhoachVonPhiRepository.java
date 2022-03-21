package com.tcdt.qlnvkhoachphi.repository.catalog;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkhoachphi.table.catalog.QlnvDmKhoachVonPhi;

@Repository
public interface QlnvDmKhoachVonPhiRepository extends CrudRepository<QlnvDmKhoachVonPhi, Long> {
	@Query(value = "SELECT * FROM QLNV_DM_KHOACHVON WHERE ID_CHA IS NULL AND LEVEL_DM=0", nativeQuery = true)
	Page<QlnvDmKhoachVonPhi> listCategoryType(Pageable pageable);

	@Query(value = "SELECT * FROM QLNV_DM_KHOACHVON WHERE ID_CHA=:idCha", nativeQuery = true)
	Page<QlnvDmKhoachVonPhi> findByIdCha(@Param("idCha") String idCha, Pageable pageable);
//	QlnvDmKhoachVon findByMa(String ma);

	@Query(value = "SELECT * FROM QLNV_DM_KHOACHVON WHERE LOAI_DM=:loaiDm AND LEVEL_DM=:levelDm", nativeQuery = true)
	Page<QlnvDmKhoachVonPhi> findByLoaiDmAndLevelDm(@Param("loaiDm") String loaiDm, @Param("levelDm") Integer levelDm,
			Pageable pageable);
	
	@Query(value = "SELECT * FROM QLNV_DM_KHOACHVON WHERE LOAI_DM=:loaiDm AND LEVEL_DM=1", nativeQuery = true)
	ArrayList<QlnvDmKhoachVonPhi> findByLoaiDm(@Param("loaiDm") String loaiDm);
}
