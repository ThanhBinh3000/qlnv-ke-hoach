package com.tcdt.qlnvkhoach.repository.khcnbq;

import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuuCanCu;
import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuuTienDo;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagKetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnCtrinhNcuuTienDoRepository extends JpaRepository<CnCtrinhNcuuTienDo, Long> {
	List<CnCtrinhNcuuTienDo> findAllByCnCtNcIdIn(List<Long> ids);

	List<CnCtrinhNcuuTienDo> findByCnCtNcId(Long id);
}
