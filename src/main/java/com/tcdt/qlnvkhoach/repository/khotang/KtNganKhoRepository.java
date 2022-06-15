package com.tcdt.qlnvkhoach.repository.khotang;

import com.tcdt.qlnvkhoach.table.khotang.KtNganKho;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KtNganKhoRepository extends CrudRepository<KtNganKho, Long>, KtNganKhoRepositoryCustom {

}