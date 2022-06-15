package com.tcdt.qlnvkhoach.repository.khotang;

import com.tcdt.qlnvkhoach.table.khotang.KtNganLo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface KtNganLoRepositoryCustom {

    Page<KtNganLo> selectParams(@Param("ma") String ma, @Param("ten") String ten, @Param("id") Long id, Pageable pageable);
}
