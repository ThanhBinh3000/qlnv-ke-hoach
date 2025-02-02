package com.tcdt.qlnvkhoach.repository.phuongangia;

import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagQdTcdtnnCtiet;
import com.tcdt.qlnvkhoach.entities.phuongangia.KhPagTongHopCTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

public interface KhPagQdTcdtnnCtietRepository extends JpaRepository<KhPagQdTcdtnnCtiet, Long> {

    @Transient
    void deleteAllByQdTcdtnnId(Long qdTcdtnnId);
    @Transient
    void deleteAllByQdTcdtnnIdIn(List<Long> listQdTcdtnnId);
    @Transient
    List<KhPagQdTcdtnnCtiet> findAllByQdTcdtnnId(Long qdTcdtnnId);

    List<KhPagQdTcdtnnCtiet> findAllByQdTcdtnnIdIn(List<Long> qdTcdtnnId);

}
