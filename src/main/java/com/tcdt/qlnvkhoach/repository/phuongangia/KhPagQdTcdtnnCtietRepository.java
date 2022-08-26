package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagQdTcdtnnCtiet;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHopCTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface KhPagQdTcdtnnCtietRepository extends JpaRepository<KhPagQdTcdtnnCtiet, Long> {

    List<KhPagQdTcdtnnCtiet> findByIdIn(List<Long> ids);
    List<KhPagQdTcdtnnCtiet> findByQdTcdtnnIdIn(List<Long> ids);

    @Transactional
    void deleteAllByQdTcdtnnId(Long qdTcdtnnId);

    @Transactional
    void deleteAllByQdTcdtnnIdIn(List<Long> qdTcdtnnIdList);

}
