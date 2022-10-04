package com.tcdt.qlnvkhoach.mapper.denghicapphibonganh;

import com.tcdt.qlnvkhoach.entities.denghicapphibonganh.KhDnCapPhiBoNganh;
import com.tcdt.qlnvkhoach.mapper.AbstractMapper;
import com.tcdt.qlnvkhoach.request.denghicapphibonganh.KhDnCapPhiBoNganhRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface KhDnCapPhiBoNganhRequestMapper extends AbstractMapper<KhDnCapPhiBoNganh, KhDnCapPhiBoNganhRequest> {
}
