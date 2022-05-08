package com.tcdt.qlnvkhoachphi.service.filedinhkem;

import com.tcdt.qlnvkhoachphi.entities.FileDinhKemChung;
import com.tcdt.qlnvkhoachphi.repository.FileDinhKemChungRepository;
import com.tcdt.qlnvkhoachphi.request.object.catalog.FileDinhKemReq;
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

        fileDinhKemChungRepository.deleteByDataIdAndDataType(dataId, dataType);

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
    public List<FileDinhKemChung> search(Long dataId) {
        return fileDinhKemChungRepository.findByDataId(dataId);
    }
}
