package com.tcdt.qlnvkhoach.service.filedinhkem;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;

import java.util.Collection;
import java.util.List;

public interface FileDinhKemService {
    List<FileDinhKemChung> saveListFileDinhKem(List<FileDinhKemReq> fileDinhKemReqs,
                                               Long dataId,
                                               String dataType);

    List<FileDinhKemChung> search(Long dataId, Collection<String> dataTypes);

    void delete(Long dataId, Collection<String> dataTypes);

    void saveFileDinhKems(Collection<FileDinhKemChung> fileDinhKemChungs);

    void deleteMultiple(Collection<Long> dataIds, Collection<String> dataTypes);
}
