package com.tcdt.qlnvkhoach.mapper.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh;
import com.tcdt.qlnvkhoach.mapper.AbstractMapper;
import com.tcdt.qlnvkhoach.request.denghicapvonbonganh.KhDnCapVonBoNganhRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface KhDnCapVonBoNganhRequestMapper extends AbstractMapper<KhDnCapVonBoNganh, KhDnCapVonBoNganhRequest> {
}
