package com.tcdt.qlnvkhoach.mapper.denghicapphibonganh;

import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganh;
import com.tcdt.qlnvkhoach.mapper.AbstractMapper;
import com.tcdt.qlnvkhoach.response.denghicapphibonganh.KhDnCapPhiBoNganhResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface KhDnCapPhiBoNganhResponseMapper extends AbstractMapper<KhDnCapPhiBoNganh, KhDnCapPhiBoNganhResponse> {
}
