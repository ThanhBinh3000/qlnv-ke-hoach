package com.tcdt.qlnvkhoach.repository.khcnbq;

import com.tcdt.qlnvkhoach.entities.khcnbq.CnCtrinhNcuuCanCu;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagKetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CnCtrinhNcuuCanCuRepository extends JpaRepository<CnCtrinhNcuuCanCu, Long> {
	List<CnCtrinhNcuuCanCu> findAllByCnCtNcIdIn(List<Long> ids);

	List<CnCtrinhNcuuCanCu> findByCnCtNcId(Long id);

}
