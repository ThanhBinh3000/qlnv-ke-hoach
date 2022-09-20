package com.tcdt.qlnvkhoach.mapper.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganhCt;
import com.tcdt.qlnvkhoach.mapper.AbstractMapper;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhCtResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface KhDnCapVonBoNganhCtResponseMapper extends AbstractMapper<KhDnCapVonBoNganhCt, KhDnCapVonBoNganhCtResponse> {
}
