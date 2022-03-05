package com.tcdt.qlnvkhoachphi.repository;

import com.tcdt.qlnvkhoachphi.entities.KtTrangthaiHienthoi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KtTrangthaiHienthoiRepository extends CrudRepository<KtTrangthaiHienthoi, Long> {
	List<KtTrangthaiHienthoi> findAllByMaDonViInAndMaVthhInAndNamIn(List<String> maDonViList, List<String> maVatTuList, List<String> namList);
}
