package com.tcdt.qlnvkhoach.repository.khotang;

import com.tcdt.qlnvkhoach.table.khotang.KtNganKho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface KtNganKhoRepositoryCustom {

    Page<KtNganKho> selectParams(@Param("ma") String ma, @Param("ten") String ten, @Param("id") Long id, Pageable pageable);
}
