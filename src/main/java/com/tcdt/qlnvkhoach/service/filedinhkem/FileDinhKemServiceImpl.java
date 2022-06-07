package com.tcdt.qlnvkhoach.service.filedinhkem;

import com.tcdt.qlnvkhoach.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoach.repository.FileDinhKemChungRepository;
import com.tcdt.qlnvkhoach.request.object.catalog.FileDinhKemReq;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FileDinhKemServiceImpl implements FileDinhKemService {

    @Autowired
    private FileDinhKemChungRepository fileDinhKemChungRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<FileDinhKemChung> saveListFileDinhKem(List<FileDinhKemReq> fileDinhKemReqs,
                                                      Long dataId,
                                                      String dataType) {

        fileDinhKemChungRepository.deleteByDataIdAndDataTypeIn(dataId, Collections.singleton(dataType));

        if (CollectionUtils.isEmpty(fileDinhKemReqs))
            return Collections.emptyList();

        List<FileDinhKemChung> fileDinhKems = new ArrayList<>();
        for (FileDinhKemReq dinhKemReq : fileDinhKemReqs) {
            FileDinhKemChung dinhKem = new ModelMapper().map(dinhKemReq, FileDinhKemChung.class);
            dinhKem.setCreateDate(new Date());
            dinhKem.setDataType(dataType);
            dinhKem.setDataId(dataId);
            fileDinhKems.add(dinhKem);
        }
        fileDinhKemChungRepository.saveAll(fileDinhKems);

        return fileDinhKems;
    }

    @Override
    public List<FileDinhKemChung> search(Long dataId, Collection<String> dataTypes) {
        return fileDinhKemChungRepository.findByDataIdAndDataTypeIn(dataId, dataTypes);
    }

    @Override
    public void delete(Long dataId, Collection<String> dataTypes) {
        fileDinhKemChungRepository.deleteByDataIdAndDataTypeIn(dataId, dataTypes);
    }

    @Override
    public void saveFileDinhKems(Collection<FileDinhKemChung> fileDinhKemChungs) {
        fileDinhKemChungRepository.saveAll(fileDinhKemChungs);
    }

    @Override
    public void deleteMultiple(Collection<Long> dataIds, Collection<String> dataTypes) {
        fileDinhKemChungRepository.deleteByDataIdInAndDataTypeIn(dataIds, dataTypes);
    }
}
