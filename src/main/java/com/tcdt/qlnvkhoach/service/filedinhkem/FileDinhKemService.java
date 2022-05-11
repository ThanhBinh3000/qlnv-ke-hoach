package com.tcdt.qlnvkhoach.service.filedinhkem;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;

import java.util.List;

public interface FileDinhKemService {
    List<FileDinhKemChung> saveListFileDinhKem(List<FileDinhKemReq> fileDinhKemReqs,
                                               Long dataId,
                                               String dataType);

    List<FileDinhKemChung> search(Long dataId, String dataType);

    void delete(Long dataId, String dataType);
}
