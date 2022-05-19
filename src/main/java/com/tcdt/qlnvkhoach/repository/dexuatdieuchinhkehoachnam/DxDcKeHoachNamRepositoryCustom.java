package com.tcdt.qlnvkhoach.repository.dexuatdieuchinhkehoachnam;

import com.tcdt.qlnvkhoach.query.dto.DxDcQueryDto;
import com.tcdt.qlnvkhoach.request.search.catalog.dexuatdieuchinhkehoachnam.SearchDxDcKeHoachNamReq;

import java.util.List;


public interface DxDcKeHoachNamRepositoryCustom {
    List<DxDcQueryDto> search (SearchDxDcKeHoachNamReq req);

    int countDxDcKeHoachNam(SearchDxDcKeHoachNamReq req);
}
