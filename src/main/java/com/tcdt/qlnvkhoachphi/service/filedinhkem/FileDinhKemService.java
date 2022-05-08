package com.tcdt.qlnvkhoachphi.service.filedinhkem;

import com.tcdt.qlnvkhoachphi.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;

import java.util.List;

public interface FileDinhKemService {
    List<FileDinhKemChung> saveListFileDinhKem(List<FileDinhKemReq> fileDinhKemReqs,
                                               Long dataId,
                                               String dataType);

    List<FileDinhKemChung> search(Long dataId, String dataType);

    void delete(Long dataId, String dataType);
}
