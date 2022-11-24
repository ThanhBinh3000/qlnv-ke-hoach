package com.tcdt.qlnvkhoach.mapper.denghicapvonbonganh;

import com.tcdt.qlnvkhoach.entities.denghicapvonbonganh.KhDnCapVonBoNganh;
import com.tcdt.qlnvkhoach.mapper.AbstractMapper;
import com.tcdt.qlnvkhoach.response.denghicapvonbonganh.KhDnCapVonBoNganhResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface KhDnCapVonBoNganhResponseMapper extends AbstractMapper<KhDnCapVonBoNganh, KhDnCapVonBoNganhResponse> {
}
