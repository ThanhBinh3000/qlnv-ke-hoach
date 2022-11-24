package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.query.dto.DxDcQueryDto;
import com.tcdt.qlnvkhoach.request.search.catalog.dexuatdieuchinhkehoachnam.SearchDxDcKeHoachNamReq;

import java.util.Collection;
import java.util.List;


public interface DxDcKeHoachNamRepositoryCustom {
    List<DxDcQueryDto> search (SearchDxDcKeHoachNamReq req, Collection<String> trangThais);

    Long countDxDcKeHoachNam(SearchDxDcKeHoachNamReq req, Collection<String> trangThais);
}
